package net.cosmiclion.opms.main.purchase.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import net.cosmiclion.opms.main.purchase.source.PurchaseDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

public class PurchaseLocalDataSource implements PurchaseDataSource {

    private static PurchaseLocalDataSource INSTANCE;

//    private QuickMenuDbHelper mDbHelper;

    private PurchaseLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
//        mDbHelper = new QuickMenuDbHelper(context);
    }

    public static PurchaseLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new PurchaseLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getBooksPurchaseResponse(@NonNull String token, @NonNull LoadPurchaseCallback callback) {

    }
}
