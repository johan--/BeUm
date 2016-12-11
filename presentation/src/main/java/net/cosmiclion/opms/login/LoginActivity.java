package net.cosmiclion.opms.login;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import net.cosmiclion.beum.R;
import net.cosmiclion.opms.UseCaseHandler;
import net.cosmiclion.opms.login.source.local.TasksLocalDataSource;
import net.cosmiclion.opms.login.source.remote.TasksRemoteDataSource;
import net.cosmiclion.opms.login.usecase.DoGetImageUrl;
import net.cosmiclion.opms.login.usecase.DoGetUserInfo;
import net.cosmiclion.opms.login.usecase.DoLogin;
import net.cosmiclion.opms.utils.ActivityUtils;


public class LoginActivity extends AppCompatActivity {
    private final String TAG = LoginActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_act);

        FragmentManager fragmentManager = getSupportFragmentManager();
        String taskId = null;
        LoginFragment loginFragment = (LoginFragment) fragmentManager.findFragmentById(R.id.contentFrame);
        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();
            if (getIntent().hasExtra(LoginFragment.ARGUMENT_LOGIN_ID)) {
                taskId = getIntent().getStringExtra(
                        LoginFragment.ARGUMENT_LOGIN_ID);
                Bundle bundle = new Bundle();
                bundle.putString(LoginFragment.ARGUMENT_LOGIN_ID, taskId);
                loginFragment.setArguments(bundle);
            }
            ActivityUtils.addFragmentToActivity(fragmentManager, loginFragment, R.id.contentFrame);
        }
        Context context = getApplicationContext();
        // Create the presenter
        new LoginPresenter(UseCaseHandler.getInstance(), taskId, loginFragment,
                new DoLogin(TasksRepository.getInstance(
                        TasksRemoteDataSource.getInstance(context),
                        TasksLocalDataSource.getInstance(context))),
                new DoGetUserInfo(TasksRepository.getInstance(
                        TasksRemoteDataSource.getInstance(context),
                        TasksLocalDataSource.getInstance(context))),
                new DoGetImageUrl(TasksRepository.getInstance(
                        TasksRemoteDataSource.getInstance(context),
                        TasksLocalDataSource.getInstance(context)))

        );
    }

}
