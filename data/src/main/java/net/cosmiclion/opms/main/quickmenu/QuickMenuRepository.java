package net.cosmiclion.opms.main.quickmenu;

import android.support.annotation.NonNull;

import net.cosmiclion.opms.main.quickmenu.model.QuickMenuItemDetailRequest;
import net.cosmiclion.opms.main.quickmenu.model.QuickMenuItemDetailResponse;
import net.cosmiclion.opms.main.quickmenu.model.QuickMenuItemsRequest;
import net.cosmiclion.opms.main.quickmenu.model.QuickMenuItemsResponse;

import static com.google.common.base.Preconditions.checkNotNull;

public class QuickMenuRepository implements QuickMenuDataSource {

    private static QuickMenuRepository INSTANCE = null;

    private final QuickMenuDataSource mRemoteDataSource;

    private final QuickMenuDataSource mLocalDataSource;

    boolean mCacheIsDirty = false;

    // Prevent direct instantiation.
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
    public void getQuickMenuItemsResponse(@NonNull QuickMenuItemsRequest request, @NonNull final LoadQuickMenuItemsCallback callback) {
        checkNotNull(callback);

        if (!mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            mRemoteDataSource.getQuickMenuItemsResponse(request, new LoadQuickMenuItemsCallback() {

                @Override
                public void onQuickMenuItemsLoaded(QuickMenuItemsResponse response) {
                    callback.onQuickMenuItemsLoaded(response);
                }

                @Override
                public void onDataNotAvailable(String errorMessage) {
                    callback.onDataNotAvailable(errorMessage);
                }
            });
        } else {
            // Query the local storage if available. If not, query the network.
//            mTasksLocalDataSource.getTasks(new LoadTasksCallback() {
//                @Override
//                public void onTasksLoaded(List<Task> tasks) {
//                    refreshCache(tasks);
//                    callback.onTasksLoaded(new ArrayList<>(mCachedTasks.values()));
//                }
//
//                @Override
//                public void onDataNotAvailable() {
//                    getTasksFromRemoteDataSource(callback);
//                }
//            });
        }
    }

    @Override
    public void getQuickMenuItemDetailResponse(@NonNull QuickMenuItemDetailRequest request, @NonNull final LoadQuickMenuItemDetailCallback callback) {
        checkNotNull(callback);

        if (!mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            mRemoteDataSource.getQuickMenuItemDetailResponse(request, new LoadQuickMenuItemDetailCallback() {

                @Override
                public void onQuickMenuItemDetailLoaded(QuickMenuItemDetailResponse response) {
                    callback.onQuickMenuItemDetailLoaded(response);
                }

                @Override
                public void onDataNotAvailable(String errorMessage) {
                    callback.onDataNotAvailable(errorMessage);
                }
            });
        }
    }
}
