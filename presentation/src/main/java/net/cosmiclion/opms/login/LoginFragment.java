package net.cosmiclion.opms.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import net.cosmiclion.beum.R;
import net.cosmiclion.opms.login.model.LoginRequest;
import net.cosmiclion.opms.login.model.ResponseData;
import net.cosmiclion.opms.main.MainActivity;
import net.cosmiclion.opms.utils.AES256Cipher;
import net.cosmiclion.opms.utils.Debug;
import net.cosmiclion.opms.utils.PromptDialogFragment;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginFragment extends Fragment implements LoginContract.View,
        PromptDialogFragment.DialogListener,
        View.OnClickListener {

    private final String TAG = getClass().getSimpleName();

    public static final String ARGUMENT_LOGIN_ID = "LOGIN_ID";

    private LoginContract.Presenter mPresenter;

    private EditText mEmail;
    private EditText mPassword;
    private android.support.v7.widget.AppCompatButton btnForgotId, btnForgotPass, btnRegister;

    private ProgressDialog pDialog;

    private PromptDialogFragment dialog = null;

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
        btnForgotId = (android.support.v7.widget.AppCompatButton) root.findViewById(R.id.btnForgotId);
        btnForgotId.setOnClickListener(this);
        btnForgotPass = (android.support.v7.widget.AppCompatButton) root.findViewById(R.id.btnForgotPass);
        btnForgotPass.setOnClickListener(this);
        btnRegister = (android.support.v7.widget.AppCompatButton) root.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        dialog = new PromptDialogFragment();
        dialog.setDialogClickListener(this);

        Button mBtnSignIn = (Button) root.findViewById(R.id.btn_email_sign_in);
        mBtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Debug.i(TAG, "onClick mPresenter.doGetBaseValue()");
                showProgressDialog("", "Loading");
//                mPresenter.doGetBaseValue();

//                doOpenBooks();

                String email = (mEmail.getText().toString());
                String pwd = AES256Cipher.AES_Encode(mPassword.getText().toString().trim());
                mPresenter.doLogin(new LoginRequest(email, pwd, ""));
            }
        });
        setHasOptionsMenu(true);
        setRetainInstance(true);
        return root;
    }

    @Override
    public void showLoginSuccess(@NonNull ResponseData loginResponse) {
        mPresenter.doSaveToken(loginResponse, this.getActivity());
    }

    @Override
    public void doLoginAfterGetBaseValue(String baseValue) {
        Debug.i(TAG, "doGetBaseValue=" + baseValue);
        try {
            String email = AES256Cipher.AES_Encode(mEmail.getText().toString());
            String pwd = AES256Cipher.AES_Encode(mPassword.getText().toString());

            mPresenter.doLogin(new LoginRequest(email, pwd, baseValue));
        } catch (Exception e) {
            showLoginError("");
        }
    }

    @Override
    public void showLoginFailure(@NonNull ResponseData loginResponse) {
        hideProgressDialog();
        dialog.setDialogTitle((String) loginResponse.getResponse());
        dialog.show(getActivity().getFragmentManager(), "DIALOG_PROMPT");
    }

    @Override
    public void showLoginError(String message) {
        hideProgressDialog();

        if (message.equals("")) {
            dialog.setDialogTitle(getString(R.string.error_try_again));
        } else {
            dialog.setDialogTitle(message);
        }
        dialog.show(getActivity().getFragmentManager(), "DIALOG_PROMPT");
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


    @Override
    public void onClickPositive() {

    }

    @Override
    public void onClickNegative() {

    }

    @Override
    public void onClick(View view) {
        String url = "http://malltest.wjopms.com/forgot";

        switch (view.getId()) {
            case R.id.btnForgotId:
                goWebsite(url);
                break;
            case R.id.btnForgotPass:
                goWebsite(url);
                break;
            case R.id.btnRegister:
                url = "http://malltest.wjopms.com/register";
                goWebsite(url);
                break;
        }
    }

    public void goWebsite(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(Intent.createChooser(intent, "Choose browser"));// Choose browser is arbitrary :)

    }
}
