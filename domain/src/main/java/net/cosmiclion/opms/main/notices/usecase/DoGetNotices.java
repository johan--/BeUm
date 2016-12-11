package net.cosmiclion.opms.main.notices.usecase;

import android.support.annotation.NonNull;

import net.cosmiclion.opms.UseCase;
import net.cosmiclion.opms.login.model.ResponseData;
import net.cosmiclion.opms.main.notices.model.NoticeDomain;
import net.cosmiclion.opms.main.notices.model.NoticeMapper;
import net.cosmiclion.opms.main.notices.source.NoticesDataSource;
import net.cosmiclion.opms.main.notices.source.NoticesRepository;
import net.cosmiclion.opms.utils.Debug;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by longpham on 9/14/2016.
 */
public class DoGetNotices extends
        UseCase<DoGetNotices.RequestValues, DoGetNotices.ResponseValue> {
    private final String TAG = getClass().getSimpleName();
    private final NoticesRepository mRepository;

    public DoGetNotices(@NonNull NoticesRepository repository) {
        mRepository = checkNotNull(repository, "repository cannot be null!");
    }

    @Override
    protected void executeUseCase(final RequestValues requestValues) {

        mRepository.getNoticesResponse(new NoticesDataSource.LoadNoticesCallback() {

            @Override
            public void onNoticesLoaded(ResponseData response) {
                getUseCaseCallback().onSuccess(new ResponseValue(response));
            }

            @Override
            public void onDataNotAvailable(String errorMessage) {
                Debug.i(TAG, "onDataNotAvailable=" + errorMessage);
                getUseCaseCallback().onError();
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final boolean mForceUpdate;

        public RequestValues(boolean forceUpdate) {
            this.mForceUpdate = forceUpdate;
        }

        public boolean isForceUpdate() {
            return mForceUpdate;
        }    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private List<NoticeDomain> mNotices = null;
        private NoticeDomain mNotice = null;

        /*
            Map object tu data sang domain, tranh viec sua du lieu tren server anh huong
            den cac tang domain va present.
         */
        public ResponseValue(ResponseData books) {
            Debug.i("ResponseValue", "ResponseData books=" + books.getResponse());
            List<NoticeDomain> booksPurchaseDomain = NoticeMapper
                    .transformList((String) books.getResponse());
            mNotices = checkNotNull(booksPurchaseDomain, "mBookInfo cannot null");

        }

        public List<NoticeDomain> getNoticesResponse() {
            return mNotices;
        }

        public NoticeDomain getNoticeResponse() {
            return mNotice;
        }
    }
}
