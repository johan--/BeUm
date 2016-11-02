package net.cosmiclion.opms.main.mylibrary.source;

import android.support.annotation.NonNull;

import net.cosmiclion.opms.main.mylibrary.model.BooksRequest;
import net.cosmiclion.opms.main.mylibrary.model.BooksResponse;

import static com.google.common.base.Preconditions.checkNotNull;

public class LibraryRepository implements LibraryDataSource {

    private static LibraryRepository INSTANCE = null;

    private final LibraryDataSource mRemoteDataSource;

    private final LibraryDataSource mLocalDataSource;

    boolean mCacheIsDirty = false;

    private LibraryRepository(@NonNull LibraryDataSource remoteDataSource,
                              @NonNull LibraryDataSource localDataSource) {
        mRemoteDataSource = checkNotNull(remoteDataSource);
        mLocalDataSource = checkNotNull(localDataSource);
    }

    public static LibraryRepository getInstance(LibraryDataSource remoteDataSource,
                                                LibraryDataSource localDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new LibraryRepository(remoteDataSource, localDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getBooksResponse(@NonNull BooksRequest request, @NonNull final LoadLibraryCallback callback) {
        checkNotNull(callback);

        if (!mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            mRemoteDataSource.getBooksResponse(request, new LoadLibraryCallback() {

                @Override
                public void onBooksLoaded(BooksResponse response) {
                    callback.onBooksLoaded(response);
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
