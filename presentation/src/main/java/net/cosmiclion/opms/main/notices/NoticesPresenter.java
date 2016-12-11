package net.cosmiclion.opms.main.notices;

import android.support.annotation.NonNull;

import net.cosmiclion.opms.UseCase;
import net.cosmiclion.opms.UseCaseHandler;
import net.cosmiclion.opms.main.notices.model.NoticeDomain;
import net.cosmiclion.opms.main.notices.usecase.DoGetNotices;
import net.cosmiclion.opms.utils.Debug;

import java.util.List;

/**
 * Created by longpham on 9/18/2016.
 */
public class NoticesPresenter implements NoticesContract.Presenter {

    private String TAG = getClass().getSimpleName();

    private final UseCaseHandler mUseCaseHandler;

    private final NoticesContract.View mItemView;

    private DoGetNotices mDoGetNotices;

    private boolean mFirstLoad = true;

    public NoticesPresenter(@NonNull UseCaseHandler useCaseHandler,
                            @NonNull NoticesContract.View itemView,
                            @NonNull DoGetNotices getNotices
    ) {
        this.mUseCaseHandler = useCaseHandler;
        this.mItemView = itemView;
        this.mDoGetNotices = getNotices;

        mItemView.setPresenter(this);
    }

    @Override
    public void start() {
        loadNotices(false);
    }

    @Override
    public void loadNotices(boolean forceUpdate) {
        Debug.i(TAG, "===loadNotices=== " + forceUpdate);
        getNotices(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    private void getNotices(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            mItemView.setLoadingIndicator(true);
        }
        mUseCaseHandler.execute(mDoGetNotices, new DoGetNotices.RequestValues(forceUpdate),
                new UseCase.UseCaseCallback<DoGetNotices.ResponseValue>() {
                    @Override
                    public void onSuccess(DoGetNotices.ResponseValue response) {
                        List<NoticeDomain> notices = response.getNoticesResponse();
                        Debug.i(TAG, "books size = " + notices.size());
                        if (notices.isEmpty()) {
                            // Show a message indicating there are no tasks for that filter type.
//                             processEmptyTasks();
                        } else {
                            mItemView.showNotices(notices);
                        }
                    }

                    @Override
                    public void onError() {
                        Debug.i(TAG, "Error");
//                mItemView.showLoginError("");
                    }
                });

    }
}
