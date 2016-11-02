package net.cosmiclion.opms.login.usecase;

import android.support.annotation.NonNull;

import net.cosmiclion.opms.login.TasksDataSource;
import net.cosmiclion.opms.login.TasksRepository;
import net.cosmiclion.opms.login.model.LoginRequest;
import net.cosmiclion.opms.login.model.LoginResponse;
import net.cosmiclion.opms.UseCase;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by longpham on 9/14/2016.
 */
public class DoLogin extends UseCase<DoLogin.RequestValues, DoLogin.ResponseValue> {
    private final TasksRepository mTasksRepository;

    public DoLogin(@NonNull TasksRepository tasksRepository) {
        mTasksRepository = checkNotNull(tasksRepository, "tasksRepository cannot be null!");
    }

    @Override
    protected void executeUseCase(final RequestValues requestValues) {
        LoginRequest loginRequest = requestValues.getLoginAccount();

        mTasksRepository.getLoginResponse(loginRequest, new TasksDataSource.LoadLoginCallback() {
            @Override
            public void onLoginLoaded(LoginResponse response) {
                getUseCaseCallback().onSuccess(new ResponseValue(response));
            }

            @Override
            public void onDataNotAvailable(String errorMessage) {
                getUseCaseCallback().onError();
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final LoginRequest mLoginRequest;

        public RequestValues(@NonNull LoginRequest loginRequest) {
            mLoginRequest = checkNotNull(loginRequest, "loginRequest cannot null");
        }

        public LoginRequest getLoginAccount() {
            return mLoginRequest;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private final LoginResponse mLoginResponse;

        public ResponseValue(LoginResponse loginResponse) {
            mLoginResponse = checkNotNull(loginResponse, "LoginResponse cannot null");
        }

        public LoginResponse getLoginResponse() {
            return mLoginResponse;
        }
    }
}
