package net.cosmiclion.beum.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.cosmiclion.data.login.model.LoginRequest;
import net.cosmiclion.data.login.model.LoginResponse;
import net.cosmiclion.data.utils.AES256Cipher;
import net.cosmiclion.data.utils.Constants;
import net.cosmiclion.data.utils.Debug;
import net.cosmiclion.domain.domain.UseCase;
import net.cosmiclion.domain.domain.UseCaseHandler;
import net.cosmiclion.domain.domain.login.usecase.DoGetBaseValue;
import net.cosmiclion.domain.domain.login.usecase.DoLogin;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by longpham on 9/14/2016.
 */
public class LoginPresenter implements LoginContract.Presenter {

    private final String TAG = getClass().getSimpleName();

    private final LoginContract.View mLoginView;

    private final DoLogin mDoLogin;

    private final DoGetBaseValue mDoGetBaseValue;

    private final UseCaseHandler mUseCaseHandler;

    @Nullable
    private String mTaskId;

    public LoginPresenter(@NonNull UseCaseHandler useCaseHandler, @Nullable String taskId,
                          @NonNull LoginContract.View loginView, @NonNull DoLogin doLogin,
                          @NonNull DoGetBaseValue dogetBaseValue) {
        this.mUseCaseHandler = checkNotNull(useCaseHandler, "useCaseHandler is not null");
        this.mTaskId = taskId;
        this.mLoginView = checkNotNull(loginView, "loginView is not null");
        this.mDoLogin = checkNotNull(doLogin, "doLogin is not null");
        this.mDoGetBaseValue = checkNotNull(dogetBaseValue, "dogetBaseValue is not null");

        mLoginView.setPresenter(this);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void doGetBaseValue() {
        Debug.i(TAG, "doGetBaseValue");
        mUseCaseHandler.execute(mDoGetBaseValue, new DoGetBaseValue.RequestValues(), new UseCase.UseCaseCallback<DoGetBaseValue.ResponseValue>() {
            @Override
            public void onSuccess(DoGetBaseValue.ResponseValue response) {
                Debug.i(TAG, "doGetBaseValue onSuccess" + response.getBaseValueResponse().getBaseValue());
                mLoginView.doLoginAfterGetBaseValue(response.getBaseValueResponse().getBaseValue());
            }

            @Override
            public void onError() {
                Debug.i(TAG, "doGetBaseValue onError");
                mLoginView.showLoginError("");
            }
        });
    }

    @Override
    public void doSaveUID(LoginResponse loginResponse, @NonNull Context context) {
        try {
            Debug.i(TAG, "Login success");
            String uidEncoded = AES256Cipher.AES_Encode(loginResponse.getUid(), AES256Cipher.key);
            Debug.i(TAG, "uid encode=" + uidEncoded);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Constants.PARAMS_UID, uidEncoded);
            editor.commit();
            mLoginView.doOpenBooks();
        } catch (Exception e) {
            mLoginView.showLoginError(e.getMessage());
        }
    }

    @Override
    public void doLogin(@NonNull LoginRequest loginRequest) {
        checkNotNull(loginRequest, "LoginRequest is not null");
        mUseCaseHandler.execute(mDoLogin, new DoLogin.RequestValues(loginRequest), new UseCase.UseCaseCallback<DoLogin.ResponseValue>() {
            @Override
            public void onSuccess(DoLogin.ResponseValue response) {
                LoginResponse loginResponse = response.getLoginResponse();
                if (loginResponse.getSuccess().equals("true")) {
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
    public void doLoginGooglePlus(LoginRequest loginRequest) {

    }

    @Override
    public void doLoginFacebook(LoginRequest loginRequest) {

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

}
