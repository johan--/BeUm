package net.cosmiclion.opms.login.usecase;

import android.support.annotation.NonNull;

import net.cosmiclion.opms.UseCase;
import net.cosmiclion.opms.login.TasksDataSource;
import net.cosmiclion.opms.login.TasksRepository;
import net.cosmiclion.opms.login.model.ResponseData;
import net.cosmiclion.opms.login.model.UserInfoDomain;
import net.cosmiclion.opms.login.model.UserInfoResponseMapper;
import net.cosmiclion.opms.utils.Debug;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by longpham on 9/14/2016.
 */
public class DoGetUserInfo extends UseCase<DoGetUserInfo.RequestValues, DoGetUserInfo.ResponseValue> {
    private final TasksRepository mTasksRepository;

    public DoGetUserInfo(@NonNull TasksRepository tasksRepository) {
        mTasksRepository = checkNotNull(tasksRepository, "tasksRepository cannot be null!");
    }

    @Override
    protected void executeUseCase(final RequestValues requestValues) {
        String token = requestValues.getToken();
        mTasksRepository.getUserInfoResponse(token, new TasksDataSource.LoadUserInfoCallback() {
            @Override
            public void onUserInfoLoaded(ResponseData userInfoResponse) {
                getUseCaseCallback().onSuccess(new ResponseValue(userInfoResponse));
            }

            @Override
            public void onDataNotAvailable(String errorMessage) {
                Debug.i("DoGetUserInfo", "onDataNotAvailable=" + errorMessage);
                getUseCaseCallback().onError();
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final String mToken;

        public RequestValues(@NonNull String token) {
            mToken = checkNotNull(token, "token cannot null");
        }

        public String getToken() {
            return mToken;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private final UserInfoDomain mUserInfo;

        /*
            Map object tu data sang domain, tranh viec sua du lieu tren server anh huong
            den cac tang domain va present.
         */
        public ResponseValue(ResponseData userInfoResponse) {
            UserInfoDomain userInfo = UserInfoResponseMapper.transform((String)userInfoResponse.getResponse());
            mUserInfo = checkNotNull(userInfo, "mUserInfo cannot null");
        }

        public UserInfoDomain getUseInfoResponse() {
            return mUserInfo;
        }
    }
}
