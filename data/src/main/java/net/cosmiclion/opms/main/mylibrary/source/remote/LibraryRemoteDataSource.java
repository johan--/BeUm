package net.cosmiclion.opms.main.mylibrary.source.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import net.cosmiclion.opms.main.mylibrary.model.BookData;
import net.cosmiclion.opms.main.mylibrary.model.BooksRequest;
import net.cosmiclion.opms.main.mylibrary.model.BooksResponse;
import net.cosmiclion.opms.main.mylibrary.source.LibraryDataSource;
import net.cosmiclion.opms.utils.Debug;
import net.cosmiclion.opms.volley.VolleyManager;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class LibraryRemoteDataSource implements LibraryDataSource {
    private String TAG = getClass().getSimpleName();
    private static LibraryRemoteDataSource INSTANCE = null;
    private VolleyManager mVolleyManager = null;

    public static LibraryRemoteDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LibraryRemoteDataSource(context);
        }
        return INSTANCE;
    }

    private LibraryRemoteDataSource(@NonNull Context context) {
        checkNotNull(context);
        if (mVolleyManager == null) {
            mVolleyManager = VolleyManager.getInstance(context);
        }
    }

    @Override
    public void getBooksResponse(@NonNull BooksRequest request, @NonNull LoadLibraryCallback callback) {
        Debug.i(TAG, "========getBooksResponse=======");
        List<BookData> items = new ArrayList<BookData>();
        for (int i = 0; i < 120; i++) {
            items.add(i, new BookData("0" + i, "Book " + i, "Cover " + i));
        }
        callback.onBooksLoaded(new BooksResponse(items));
    }
}
