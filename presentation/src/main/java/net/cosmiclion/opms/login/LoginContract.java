package net.cosmiclion.opms.login;

import android.content.Context;
import android.support.annotation.NonNull;

import net.cosmiclion.opms.BasePresenter;
import net.cosmiclion.opms.BaseView;
import net.cosmiclion.opms.login.model.LoginRequest;
import net.cosmiclion.opms.login.model.LoginResponse;


/**
 * Created by longpham on 9/14/2016.
 */
public interface LoginContract {

    interface View extends BaseView<Presenter> {

        void doLoginAfterGetBaseValue(String baseValue);

        void showLoginSuccess(@NonNull LoginResponse loginResponse);

        void showLoginFailure(@NonNull LoginResponse loginResponse);

        void showLoginError(String message);

        void doOpenBooks();
    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void doLogin(@NonNull LoginRequest loginRequest);

        void doGetBaseValue();

        void doSaveUID(LoginResponse loginResponse, @NonNull Context context);

        void doLoginGooglePlus(LoginRequest loginRequest);

        void doLoginFacebook(LoginRequest loginRequest);

        void doOpenRegister();

        void doOpenForgotPassword();
    }
}
