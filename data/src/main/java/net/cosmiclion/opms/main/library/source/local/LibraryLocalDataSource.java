package net.cosmiclion.opms.main.library.source.local;
import android.content.Context;
import android.support.annotation.NonNull;

import net.cosmiclion.opms.main.library.source.LibraryDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

public class LibraryLocalDataSource implements LibraryDataSource {

    private static LibraryLocalDataSource INSTANCE;

//    private QuickMenuDbHelper mDbHelper;

    private LibraryLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
//        mDbHelper = new QuickMenuDbHelper(context);
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
}
