package net.cosmiclion.opms.main.library.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import net.cosmiclion.opms.login.source.local.DbHelper;
import net.cosmiclion.opms.main.library.source.LibraryDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

public class LibraryLocalDataSource implements LibraryDataSource {
    private String TAG = getClass().getSimpleName();
    private static LibraryLocalDataSource INSTANCE;

    private DbHelper mDbHelper;
    private SQLiteDatabase mDb;

    private LibraryLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        mDbHelper = new DbHelper(context);
        mDb = mDbHelper.getWritableDatabase();
    }

    public static LibraryLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LibraryLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getBooksLibraryResponse(@NonNull String token, @NonNull LoadLibraryCallback callback) {

    }

    @Override
    public void getBookShelfResponse(@NonNull String token, @NonNull String bookId, @NonNull LoadBookShelfCallback callback) {

    }
}
