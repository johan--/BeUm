package net.cosmiclion.opms.login;

import android.content.Context;
import android.support.annotation.NonNull;

import net.cosmiclion.opms.BasePresenter;
import net.cosmiclion.opms.BaseView;
import net.cosmiclion.opms.login.model.LoginRequest;
import net.cosmiclion.opms.login.model.ResponseData;
import net.cosmiclion.opms.login.model.UserInfoDomain;


/**
 * Created by longpham on 9/14/2016.
 */
public interface LoginContract {

    interface View extends BaseView<Presenter> {

        void showLoginSuccess(@NonNull ResponseData loginResponse);

        void doLoginAfterGetBaseValue(String baseValue);

        void showLoginFailure(@NonNull ResponseData loginResponse);

        void showLoginError(String message);

        void doOpenBooks();
    }

    interface Presenter extends BasePresenter {

        void doLogin(@NonNull LoginRequest loginRequest);

        void doSaveToken(@NonNull ResponseData loginResponse, @NonNull Context context);

        void doGetUserInfo(@NonNull String token);

        void doSaveUserInfo(@NonNull UserInfoDomain userInfo);

        void doOpenRegister();

        void doOpenForgotPassword();

        void result(int requestCode, int resultCode);

    }

    /*
        Luong:
        LoginFragment               LoginPresenter
        onLoginClicked      ->      doLogin
        showLoginSuccess    ->      doSaveToken
                                    doGetUserInfo

     */
}
