package net.cosmiclion.opms.main.notices.source;

import android.support.annotation.NonNull;

import net.cosmiclion.opms.login.model.ResponseData;

import static com.google.common.base.Preconditions.checkNotNull;

public class NoticesRepository implements NoticesDataSource {

    private static NoticesRepository INSTANCE = null;

    private final NoticesDataSource mRemoteDataSource;

    private final NoticesDataSource mLocalDataSource;

    boolean mCacheIsDirty = false;

    private NoticesRepository(@NonNull NoticesDataSource remoteDataSource,
                              @NonNull NoticesDataSource localDataSource) {
        mRemoteDataSource = checkNotNull(remoteDataSource);
        mLocalDataSource = checkNotNull(localDataSource);
    }

    public static NoticesRepository getInstance(NoticesDataSource remoteDataSource,
                                                NoticesDataSource localDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new NoticesRepository(remoteDataSource, localDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getNoticesResponse(@NonNull final LoadNoticesCallback callback) {
        checkNotNull(callback);

        if (!mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            mRemoteDataSource.getNoticesResponse(new LoadNoticesCallback() {

                @Override
                public void onNoticesLoaded(ResponseData response) {
                    callback.onNoticesLoaded(response);
                }

                @Override
                public void onDataNotAvailable(String errorMessage) {
                    callback.onDataNotAvailable(errorMessage);
                }
            });
        } else {

        }
    }
}
