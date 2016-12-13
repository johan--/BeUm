package net.cosmiclion.opms.main.library.source;

import android.support.annotation.NonNull;

import net.cosmiclion.opms.login.model.ResponseData;

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
    public void getBooksLibraryResponse(@NonNull String token, @NonNull final LoadLibraryCallback callback) {
        checkNotNull(callback);
        if (!mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            mRemoteDataSource.getBooksLibraryResponse(token, new LoadLibraryCallback() {

                @Override
                public void onBooksLibraryLoaded(ResponseData response) {
                    callback.onBooksLibraryLoaded(response);
                }

                @Override
                public void onDataNotAvailable(String errorMessage) {
                    callback.onDataNotAvailable(errorMessage);
                }
            });
        } else {

        }
    }

    @Override
    public void getBookShelfResponse(@NonNull String token,
                                     @NonNull String bookId,
                                     @NonNull final LoadBookShelfCallback callback) {
        checkNotNull(callback);
        if (!mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            mRemoteDataSource.getBookShelfResponse(token, bookId, new LoadBookShelfCallback() {

                @Override
                public void onBookShelfLoaded(ResponseData response) {
                    callback.onBookShelfLoaded(response);
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
