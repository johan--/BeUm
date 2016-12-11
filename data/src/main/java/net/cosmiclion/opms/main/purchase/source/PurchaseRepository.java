package net.cosmiclion.opms.main.purchase.source;

import android.support.annotation.NonNull;

import net.cosmiclion.opms.login.model.ResponseData;

import static com.google.common.base.Preconditions.checkNotNull;

public class PurchaseRepository implements PurchaseDataSource {

    private static PurchaseRepository INSTANCE = null;

    private final PurchaseDataSource mRemoteDataSource;

    private final PurchaseDataSource mLocalDataSource;

    boolean mCacheIsDirty = false;

    private PurchaseRepository(@NonNull PurchaseDataSource remoteDataSource,
                               @NonNull PurchaseDataSource localDataSource) {
        mRemoteDataSource = checkNotNull(remoteDataSource);
        mLocalDataSource = checkNotNull(localDataSource);
    }

    public static PurchaseRepository getInstance(PurchaseDataSource remoteDataSource,
                                                 PurchaseDataSource localDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new PurchaseRepository(remoteDataSource, localDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getBooksPurchaseResponse(@NonNull String token,
                                         @NonNull final LoadPurchaseCallback callback) {
        checkNotNull(callback);

        if (!mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            mRemoteDataSource.getBooksPurchaseResponse(token, new LoadPurchaseCallback() {

                @Override
                public void onBooksPurchaseLoaded(ResponseData response) {
                    callback.onBooksPurchaseLoaded(response);
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
