package net.cosmiclion.beum.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import net.cosmiclion.beum.R;
import net.cosmiclion.beum.main.MainActivity;
import net.cosmiclion.beum.utils.SimpleAlert;
import net.cosmiclion.data.login.model.LoginRequest;
import net.cosmiclion.data.login.model.LoginResponse;
import net.cosmiclion.data.utils.AES256Cipher;
import net.cosmiclion.data.utils.Debug;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginFragment extends Fragment implements LoginContract.View {

    private final String TAG = getClass().getSimpleName();

    public static final String ARGUMENT_LOGIN_ID = "LOGIN_ID";

    private LoginContract.Presenter mPresenter;

    private EditText mEmail;
    private EditText mPassword;

    private ProgressDialog pDialog;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
        pDialog = new ProgressDialog(getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.login_frag, container, false);
        mEmail = (EditText) root.findViewById(R.id.login_email);
        mPassword = (EditText) root.findViewById(R.id.login_password);

        Button mBtnSignIn = (Button) root.findViewById(R.id.btn_email_sign_in);
        mBtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Debug.i(TAG, "onClick mPresenter.doGetBaseValue()");
                showProgressDialog("", "Loading");
//                mPresenter.doGetBaseValue();
                doOpenBooks();
            }
        });
        setHasOptionsMenu(true);
        setRetainInstance(true);
        return root;
    }

    @Override
    public void doLoginAfterGetBaseValue(String baseValue) {
        Debug.i(TAG, "doGetBaseValue=" + baseValue);
        try {
            String email = AES256Cipher.AES_Encode(mEmail.getText().toString(), AES256Cipher.key);
            String pwd = AES256Cipher.AES_Encode(mPassword.getText().toString(), AES256Cipher.key);

            mPresenter.doLogin(new LoginRequest(email, pwd, baseValue));
        } catch (Exception e) {
            showLoginError("");
        }
    }

    @Override
    public void showLoginSuccess(@NonNull LoginResponse loginResponse) {
        Debug.i(TAG, loginResponse.getMessage() + " - " + loginResponse.getUid());
        mPresenter.doSaveUID(loginResponse, this.getActivity());
    }

    @Override
    public void showLoginFailure(@NonNull LoginResponse loginResponse) {
        hideProgressDialog();
        SimpleAlert.show(this.getActivity(), loginResponse.getMessage());
    }

    @Override
    public void showLoginError(String message) {
        hideProgressDialog();
        if (message.equals("")) {
            SimpleAlert.show(this.getActivity(), getString(R.string.error_try_again));
        } else {
            SimpleAlert.show(this.getActivity(), message);
        }
    }

    @Override
    public void doOpenBooks() {
        hideProgressDialog();
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void setPresenter(@NonNull LoginContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    private void showProgressDialog(String title, String message) {
        if (!pDialog.isShowing()) {
            pDialog.setTitle(title);
            pDialog.setMessage(message);
            pDialog.setCancelable(false);
            pDialog.show();
        }
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

}
