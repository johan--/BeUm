package net.cosmiclion.opms.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;

import com.google.gson.Gson;

import net.cosmiclion.opms.UseCase;
import net.cosmiclion.opms.UseCaseHandler;
import net.cosmiclion.opms.login.model.LoginRequest;
import net.cosmiclion.opms.login.model.MobileToken;
import net.cosmiclion.opms.login.model.ResponseData;
import net.cosmiclion.opms.login.model.UserInfoDomain;
import net.cosmiclion.opms.login.usecase.DoGetImageUrl;
import net.cosmiclion.opms.login.usecase.DoGetUserInfo;
import net.cosmiclion.opms.login.usecase.DoLogin;
import net.cosmiclion.opms.main.purchase.model.ImageUrl;
import net.cosmiclion.opms.utils.Debug;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by longpham on 9/14/2016.
 */
public class LoginPresenter implements LoginContract.Presenter {

    private final String TAG = getClass().getSimpleName();

    private final LoginContract.View mLoginView;

    private final DoLogin mDoLogin;

    private final DoGetUserInfo mDoGetUserInfo;

    private final UseCaseHandler mUseCaseHandler;

    private final DoGetImageUrl mGetImageUrl;

    public static final String PREF_KEY_USER_INFO = "USER_INFO";
    public static final String PARAMS_MOBILE_TOKEN = "member_mobile_token";
    public static final String PREF_KEY_COVER_IMAGE_URL = "cover_image_base_url";

    @Nullable
    private String mTaskId;

    public LoginPresenter(@NonNull UseCaseHandler useCaseHandler, @Nullable String taskId,
                          @NonNull LoginContract.View loginView, @NonNull DoLogin doLogin,
                          @NonNull DoGetUserInfo doGetUserInfo,
                          @NonNull DoGetImageUrl getImageUrl
    ) {
        this.mUseCaseHandler = checkNotNull(useCaseHandler, "useCaseHandler is not null");
        this.mTaskId = taskId;
        this.mLoginView = checkNotNull(loginView, "loginView is not null");
        this.mDoLogin = checkNotNull(doLogin, "doLogin is not null");
        this.mDoGetUserInfo = checkNotNull(doGetUserInfo, "dogetBaseValue is not null");
        this.mGetImageUrl = getImageUrl;

        mLoginView.setPresenter(this);
    }

    @Override
    public void doLogin(@NonNull LoginRequest loginRequest) {
        Debug.i(TAG, "===doLogin===");
        checkNotNull(loginRequest, "LoginRequest is not null");
        mUseCaseHandler.execute(mDoLogin, new DoLogin.RequestValues(loginRequest), new UseCase.UseCaseCallback<DoLogin.ResponseValue>() {
            @Override
            public void onSuccess(DoLogin.ResponseValue response) {
                ResponseData loginResponse = response.getLoginResponse();
                if (loginResponse.getSuccess()) {
                    mLoginView.showLoginSuccess(loginResponse);
                } else {
                    mLoginView.showLoginFailure(loginResponse);
                }
            }

            @Override
            public void onError() {
                Debug.i(TAG, "Error");
                mLoginView.showLoginError("");
            }
        });
    }

    @Override
    public void doGetUserInfo(String token) {
        Debug.i(TAG, "===doGetUserInfo===");
        doSaveImageUrl();
        mUseCaseHandler.execute(mDoGetUserInfo, new DoGetUserInfo.RequestValues(token), new UseCase.UseCaseCallback<DoGetUserInfo.ResponseValue>() {
            @Override
            public void onSuccess(DoGetUserInfo.ResponseValue response) {
                Debug.i(TAG, "doGetUserInfo onSuccess=" + response.getUseInfoResponse().member_account);
                doSaveUserInfo(response.getUseInfoResponse());
            }

            @Override
            public void onError() {
                Debug.i(TAG, "doGetUserInfo onError");
                mLoginView.showLoginError("");
            }
        });
    }

    @Override
    public void doSaveUserInfo(@NonNull UserInfoDomain userInfo) {
        mEditor.putString(PREF_KEY_USER_INFO, new Gson().toJson(userInfo));
        mEditor.commit();
        mLoginView.doOpenBooks();
    }

    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;

    @Override
    public void doSaveToken(@NonNull ResponseData loginResponse, @NonNull Context context) {
        try {
            Debug.i(TAG, "===doSaveToken===" + loginResponse.getResponse());

            String memberMobileToken = new Gson().fromJson(
                    (String) loginResponse.getResponse(), MobileToken.class).getMobileToken();
            Debug.i(TAG, "memberMobileToken=" + memberMobileToken);
            String tokenEncoded = memberMobileToken + ":";
            tokenEncoded = Base64.encodeToString(tokenEncoded.getBytes("UTF-8"), Base64.NO_WRAP);
            tokenEncoded = "Basic " + tokenEncoded;
            if (memberMobileToken != null) {
                mPref = PreferenceManager.getDefaultSharedPreferences(context);
                mEditor = mPref.edit();
                mEditor.putString(PARAMS_MOBILE_TOKEN, tokenEncoded);
                mEditor.commit();
//                mLoginView.doOpenBooks();
                doGetUserInfo(tokenEncoded);
            } else {
                mLoginView.showLoginError("");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mLoginView.showLoginError(e.getMessage());
        }
    }

    @Override
    public void doOpenRegister() {

    }

    @Override
    public void doOpenForgotPassword() {

    }

    @Override
    public void start() {

    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    private void doSaveImageUrl(){

        mUseCaseHandler.execute(mGetImageUrl, new DoGetImageUrl.RequestValues(),
                new UseCase.UseCaseCallback<DoGetImageUrl.ResponseValue>() {
                    @Override
                    public void onSuccess(DoGetImageUrl.ResponseValue response) {
                        ImageUrl imageUrl = new Gson().fromJson(
                                (String) response.getImageUrlResponse().getResponse(), ImageUrl.class);
                        Debug.i(TAG, "cover_image_base_url = " + imageUrl.cover_image_base_url);
                        mEditor.putString(PREF_KEY_COVER_IMAGE_URL, imageUrl.cover_image_base_url);
                        mEditor.commit();
                    }

                    @Override
                    public void onError() {
                        Debug.i(TAG, "doSaveImageUrl Error");
//                mItemView.showLoginError("");
                    }
                });
    }
}
