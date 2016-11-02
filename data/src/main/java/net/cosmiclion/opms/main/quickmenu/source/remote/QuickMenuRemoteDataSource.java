package net.cosmiclion.opms.main.quickmenu.source.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import net.cosmiclion.opms.main.quickmenu.QuickMenuDataSource;
import net.cosmiclion.opms.main.quickmenu.model.QMenuItem;
import net.cosmiclion.opms.main.quickmenu.model.QuickMenuItemDetailRequest;
import net.cosmiclion.opms.main.quickmenu.model.QuickMenuItemDetailResponse;
import net.cosmiclion.opms.main.quickmenu.model.QuickMenuItemsRequest;
import net.cosmiclion.opms.main.quickmenu.model.QuickMenuItemsResponse;
import net.cosmiclion.opms.utils.Debug;
import net.cosmiclion.opms.volley.VolleyManager;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class QuickMenuRemoteDataSource implements QuickMenuDataSource {
    private String TAG = getClass().getSimpleName();
    private static QuickMenuRemoteDataSource INSTANCE = null;
    private VolleyManager mVolleyManager = null;

    public static QuickMenuRemoteDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new QuickMenuRemoteDataSource(context);
        }
        return INSTANCE;
    }

    private QuickMenuRemoteDataSource(@NonNull Context context) {
        checkNotNull(context);
        if (mVolleyManager == null) {
            mVolleyManager = VolleyManager.getInstance(context);
        }
    }

    @Override
    public void getQuickMenuItemsResponse(@NonNull QuickMenuItemsRequest request, @NonNull LoadQuickMenuItemsCallback callback) {
        Debug.i(TAG, "========getQuickMenuItemsResponse=======");
        List<QMenuItem> items = new ArrayList<QMenuItem>();
        items.add(0, new QMenuItem("01","Book1","Cover1"));
        items.add(1, new QMenuItem("02","Book2","Cover2"));
        items.add(2, new QMenuItem("03","Book3","Cover3"));
        items.add(3, new QMenuItem("04","Book4","Cover4"));

        items.add(0, new QMenuItem("01","Book1","Cover1"));
        items.add(1, new QMenuItem("02","Book2","Cover2"));
        items.add(2, new QMenuItem("03","Book3","Cover3"));
        items.add(3, new QMenuItem("04","Book4","Cover4"));

        items.add(0, new QMenuItem("01","Book1","Cover1"));
        items.add(1, new QMenuItem("02","Book2","Cover2"));
        items.add(2, new QMenuItem("03","Book3","Cover3"));
        items.add(3, new QMenuItem("04","Book4","Cover4"));

        items.add(0, new QMenuItem("01","Book1","Cover1"));
        items.add(1, new QMenuItem("02","Book2","Cover2"));
        items.add(2, new QMenuItem("03","Book3","Cover3"));
        items.add(3, new QMenuItem("04","Book4","Cover4"));

        callback.onQuickMenuItemsLoaded(new QuickMenuItemsResponse(items));
    }

    @Override
    public void getQuickMenuItemDetailResponse(@NonNull QuickMenuItemDetailRequest request, @NonNull LoadQuickMenuItemDetailCallback callback) {
        Debug.i(TAG, "========getQuickMenuItemsResponse=======");
        callback.onQuickMenuItemDetailLoaded(new QuickMenuItemDetailResponse());
    }
}
