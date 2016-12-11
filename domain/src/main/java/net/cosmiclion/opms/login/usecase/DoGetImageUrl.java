package net.cosmiclion.opms.login.usecase;

import android.support.annotation.NonNull;

import net.cosmiclion.opms.UseCase;
import net.cosmiclion.opms.login.TasksDataSource;
import net.cosmiclion.opms.login.TasksRepository;
import net.cosmiclion.opms.login.model.ResponseData;
import net.cosmiclion.opms.utils.Debug;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by longpham on 9/14/2016.
 */
public class DoGetImageUrl extends
        UseCase<DoGetImageUrl.RequestValues, DoGetImageUrl.ResponseValue> {
    private final String TAG = getClass().getSimpleName();
    private final TasksRepository mRepository;

    public DoGetImageUrl(@NonNull TasksRepository repository) {
        mRepository = checkNotNull(repository, "repository cannot be null!");
    }

    @Override
    protected void executeUseCase(final RequestValues requestValues) {
        mRepository.getBaseImageUrlResponse( new TasksDataSource.LoadBaseImageUrlCallback() {

            @Override
            public void onBaseImageUrlLoaded(ResponseData response) {
                getUseCaseCallback().onSuccess(new ResponseValue(response));
            }

            @Override
            public void onDataNotAvailable(String errorMessage) {
                Debug.i(TAG, "onDataNotAvailable=" + errorMessage);
                getUseCaseCallback().onError();
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        public RequestValues() {
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private final ResponseData mResponse;

        public ResponseValue(ResponseData response) {
            mResponse = checkNotNull(response, "response cannot null");
        }

        public ResponseData getImageUrlResponse() {
            return mResponse;
        }
    }
}
