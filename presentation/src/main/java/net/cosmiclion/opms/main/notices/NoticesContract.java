package net.cosmiclion.opms.main.notices;

import android.support.annotation.NonNull;

import net.cosmiclion.opms.BasePresenter;
import net.cosmiclion.opms.BaseView;
import net.cosmiclion.opms.main.notices.model.NoticeDomain;

import java.util.List;

/**
 * Created by longpham on 9/18/2016.
 */
public interface NoticesContract {

    interface Presenter extends BasePresenter {

        void loadNotices(boolean forceUpdate);
    }

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        boolean isActive();

        void showNotices(@NonNull List<NoticeDomain> notices);

    }
}
