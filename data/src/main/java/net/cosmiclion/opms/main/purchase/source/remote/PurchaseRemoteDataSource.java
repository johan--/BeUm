package net.cosmiclion.opms.main.purchase.source.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import net.cosmiclion.opms.login.model.ResponseData;
import net.cosmiclion.opms.main.purchase.model.BookPurchaseData;
import net.cosmiclion.opms.main.purchase.service.PurchaseService;
import net.cosmiclion.opms.main.purchase.source.PurchaseDataSource;
import net.cosmiclion.opms.network.retrofit2.ApiClient;
import net.cosmiclion.opms.utils.Debug;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static com.google.common.base.Preconditions.checkNotNull;

public class PurchaseRemoteDataSource implements PurchaseDataSource {
    private String TAG = getClass().getSimpleName();
    private static PurchaseRemoteDataSource INSTANCE = null;
    private Context mContext;

    public static PurchaseRemoteDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new PurchaseRemoteDataSource(context);
        }
        return INSTANCE;
    }

    private PurchaseRemoteDataSource(@NonNull Context context) {
        this.mContext = checkNotNull(context);
    }

    @Override
    public void getBooksPurchaseResponse(@NonNull String token,
                                         @NonNull final LoadPurchaseCallback callback) {
        Debug.i(TAG, "===getBooksPurchaseResponse===");

        PurchaseService purchaseService = ApiClient.getClient(mContext).create(PurchaseService.class);

        final Call<ResponseData> call = purchaseService.getBookPurchaseInfo(token);

        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call,
                                   retrofit2.Response<ResponseData> response) {
                callback.onBooksPurchaseLoaded(response.body());
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Debug.i(TAG, "Something went wrong: " + t.getMessage());
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }

    @Override
    public void doSaveBooks(List<BookPurchaseData> books) {

    }

    @Override
    public BookPurchaseData getBook(@NonNull String bookId) {
        return null;
    }

    @Override
    public void doUpdateBookDownloaded(@NonNull String bookId) {

    }

    @Override
    public void doUpdateBookReaded(@NonNull String bookId) {

    }

}
