package net.cosmiclion.opms.main.library.source.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import net.cosmiclion.opms.login.model.ResponseData;
import net.cosmiclion.opms.main.library.service.LibraryService;
import net.cosmiclion.opms.main.library.source.LibraryDataSource;
import net.cosmiclion.opms.network.retrofit2.ApiClient;
import net.cosmiclion.opms.utils.Debug;

import retrofit2.Call;
import retrofit2.Callback;

import static com.google.common.base.Preconditions.checkNotNull;

public class LibraryRemoteDataSource implements LibraryDataSource {
    private String TAG = getClass().getSimpleName();
    private static LibraryRemoteDataSource INSTANCE = null;
    private Context mContext;

    public static LibraryRemoteDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LibraryRemoteDataSource(context);
        }
        return INSTANCE;
    }

    private LibraryRemoteDataSource(@NonNull Context context) {
        this.mContext = checkNotNull(context);
    }

    @Override
    public void getBooksLibraryResponse(@NonNull String token, final @NonNull LoadLibraryCallback callback) {
        Debug.i(TAG, "===getBooksLibraryResponse===");

        LibraryService service = ApiClient.getClient(mContext).create(LibraryService.class);

        final Call<ResponseData> call = service.getBooksLibrary(token);

        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call,
                                   retrofit2.Response<ResponseData> response) {
                callback.onBooksLibraryLoaded(response.body());
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Debug.i(TAG, "Something went wrong: " + t.getMessage());
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }

    @Override
    public void getBookShelfResponse(@NonNull String token,
                                     @NonNull String bookId,
                                     @NonNull final LoadBookShelfCallback callback) {
        Debug.i(TAG, "===getBookShelfResponse===");
        LibraryService service = ApiClient.getClient(mContext).create(LibraryService.class);

        final Call<ResponseData> call = service.getBookShelf(token, bookId);

        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call,
                                   retrofit2.Response<ResponseData> response) {
                Debug.i(TAG, "getBookShelfResponse: " +response.body());
                callback.onBookShelfLoaded(response.body());
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Debug.i(TAG, "Something went wrong: " + t.getMessage());
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }
}
