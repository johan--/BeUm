package net.cosmiclion.opms.main.quickmenu;

import android.support.annotation.NonNull;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.cosmiclion.opms.login.model.ResponseData;
import net.cosmiclion.opms.main.purchase.model.BookPurchaseData;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class QuickMenuRepository implements QuickMenuDataSource {

    private static QuickMenuRepository INSTANCE = null;

    private final QuickMenuDataSource mRemoteDataSource;

    private final QuickMenuDataSource mLocalDataSource;

    private QuickMenuRepository(@NonNull QuickMenuDataSource remoteDataSource,
                                @NonNull QuickMenuDataSource localDataSource) {
        mRemoteDataSource = checkNotNull(remoteDataSource);
        mLocalDataSource = checkNotNull(localDataSource);
    }

    public static QuickMenuRepository getInstance(QuickMenuDataSource remoteDataSource,
                                                  QuickMenuDataSource localDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new QuickMenuRepository(remoteDataSource, localDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getBooksPurchaseResponse(@NonNull String token, @NonNull final LoadPurchaseCallback callback) {
        checkNotNull(callback);
        mRemoteDataSource.getBooksPurchaseResponse(token, new LoadPurchaseCallback() {

            @Override
            public void onBooksPurchaseLoaded(ResponseData response) {
                // success return = true
                if (response.getSuccess()) {
                    List<BookPurchaseData> booksData = new GsonBuilder().setPrettyPrinting().create()
                            .fromJson((String) response.getResponse(), new TypeToken<List<BookPurchaseData>>() {
                            }.getType());
                    callback.onBooksPurchaseLoaded(response);
                }
            }

            @Override
            public void onDataNotAvailable(String errorMessage) {
                callback.onDataNotAvailable(errorMessage);
            }
        });
    }
}
