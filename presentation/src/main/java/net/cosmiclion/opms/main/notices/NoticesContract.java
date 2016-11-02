package net.cosmiclion.opms.main.notices;

import net.cosmiclion.opms.BasePresenter;
import net.cosmiclion.opms.BaseView;

/**
 * Created by longpham on 9/18/2016.
 */
public interface NoticesContract {

    interface Presenter extends BasePresenter {

        void loadContent(boolean forceUpdate);
    }

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        boolean isActive();

        void showContent();

    }
}
