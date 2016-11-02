package net.cosmiclion.opms.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.cosmiclion.beum.R;
import net.cosmiclion.opms.utils.ActivityUtils;
import net.cosmiclion.opms.login.source.local.TasksLocalDataSource;
import net.cosmiclion.opms.login.source.remote.TasksRemoteDataSource;
import net.cosmiclion.opms.UseCaseHandler;
import net.cosmiclion.opms.login.usecase.DoGetBaseValue;
import net.cosmiclion.opms.login.usecase.DoLogin;


public class LoginActivity extends AppCompatActivity {
    private final String TAG = LoginActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_act);

        String taskId = null;
        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();
            if (getIntent().hasExtra(LoginFragment.ARGUMENT_LOGIN_ID)) {
                taskId = getIntent().getStringExtra(
                        LoginFragment.ARGUMENT_LOGIN_ID);
                Bundle bundle = new Bundle();
                bundle.putString(LoginFragment.ARGUMENT_LOGIN_ID, taskId);
                loginFragment.setArguments(bundle);
            }
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), loginFragment, R.id.contentFrame);
        }
        // Create the presenter
        new LoginPresenter(UseCaseHandler.getInstance(), taskId, loginFragment,
                new DoLogin(TasksRepository.getInstance(TasksRemoteDataSource.getInstance(getApplicationContext()),
                        TasksLocalDataSource.getInstance(getApplicationContext()))),
                new DoGetBaseValue(TasksRepository.getInstance(TasksRemoteDataSource.getInstance(getApplicationContext()),
                        TasksLocalDataSource.getInstance(getApplicationContext())))
        );
    }

}
