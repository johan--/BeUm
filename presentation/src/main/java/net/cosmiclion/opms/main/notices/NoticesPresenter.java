package net.cosmiclion.opms.main.notices;

import android.support.annotation.NonNull;

import net.cosmiclion.opms.UseCaseHandler;

/**
 * Created by longpham on 9/18/2016.
 */
public class NoticesPresenter implements NoticesContract.Presenter {

    private String TAG = getClass().getSimpleName();

    private final UseCaseHandler mUseCaseHandler;

    private final NoticesContract.View mItemView;

    private boolean mFirstLoad = true;

    public NoticesPresenter(@NonNull UseCaseHandler useCaseHandler,
                            @NonNull NoticesContract.View itemView
    ) {
        this.mUseCaseHandler = useCaseHandler;
        this.mItemView = itemView;

        mItemView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    private void loadQuickMenuItems(boolean forceUpdate, final boolean showLoadingUI) {

    }

    @Override
    public void loadContent(boolean forceUpdate) {

    }
}
