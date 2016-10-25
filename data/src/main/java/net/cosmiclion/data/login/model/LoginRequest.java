package net.cosmiclion.data.login.model;

/**
 * Created by longpham on 9/14/2016.
 */
public class LoginRequest {

    private final String mEmailAddress;

    private final String mPassword;

    private final String mBaseValue;

    public LoginRequest(String emailAddress, String password, String baseValue) {
        this.mEmailAddress = emailAddress;
        this.mPassword = password;
        this.mBaseValue = baseValue;
    }

    public String getEmailAddress() {
        return mEmailAddress;
    }

    public String getPassword() {
        return mPassword;
    }

    public String getBaseValue() {
        return mBaseValue;
    }
}
