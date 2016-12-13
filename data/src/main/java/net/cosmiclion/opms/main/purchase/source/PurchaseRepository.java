package net.cosmiclion.opms.main.purchase.source;

import android.support.annotation.NonNull;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.cosmiclion.opms.login.model.ResponseData;
import net.cosmiclion.opms.main.purchase.model.BookPurchaseData;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class PurchaseRepository implements PurchaseDataSource {

    private static PurchaseRepository INSTANCE = null;

    private final PurchaseDataSource mRemoteDataSource;

    private final PurchaseDataSource mLocalDataSource;

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
        mRemoteDataSource.getBooksPurchaseResponse(token, new LoadPurchaseCallback() {

            @Override
            public void onBooksPurchaseLoaded(ResponseData response) {
                // success return = true
                if (response.getSuccess()) {
                    List<BookPurchaseData> booksData = new GsonBuilder().setPrettyPrinting().create()
                            .fromJson((String) response.getResponse(), new TypeToken<List<BookPurchaseData>>() {
                            }.getType());
                    mLocalDataSource.doSaveBooks(booksData);
                    callback.onBooksPurchaseLoaded(response);
                }
            }

            @Override
            public void onDataNotAvailable(String errorMessage) {
                callback.onDataNotAvailable(errorMessage);
            }
        });

    }

    @Override
    public void doSaveBooks(List<BookPurchaseData> books) {

    }

    @Override
    public BookPurchaseData getBook(@NonNull String bookId) {
        return mLocalDataSource.getBook(bookId);
    }

    @Override
    public void doUpdateBookDownloaded(@NonNull String bookId) {
        mLocalDataSource.doUpdateBookDownloaded(bookId);
    }

    @Override
    public void doUpdateBookReaded(@NonNull String bookId) {

    }


}
