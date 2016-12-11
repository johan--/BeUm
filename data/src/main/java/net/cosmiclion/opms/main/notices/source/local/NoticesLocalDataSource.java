package net.cosmiclion.opms.main.notices.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import net.cosmiclion.opms.main.notices.source.NoticesDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

public class NoticesLocalDataSource implements NoticesDataSource {

    private static NoticesLocalDataSource INSTANCE;

//    private QuickMenuDbHelper mDbHelper;

    private NoticesLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
//        mDbHelper = new QuickMenuDbHelper(context);
    }

    public static NoticesLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new NoticesLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getNoticesResponse(@NonNull LoadNoticesCallback callback) {

    }
}
