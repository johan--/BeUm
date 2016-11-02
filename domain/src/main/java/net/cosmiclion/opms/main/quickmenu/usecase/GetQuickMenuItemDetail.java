package net.cosmiclion.opms.main.quickmenu.usecase;

import android.support.annotation.NonNull;

import net.cosmiclion.opms.main.quickmenu.QuickMenuDataSource;
import net.cosmiclion.opms.main.quickmenu.QuickMenuRepository;
import net.cosmiclion.opms.main.quickmenu.model.QuickMenuItemDetailRequest;
import net.cosmiclion.opms.main.quickmenu.model.QuickMenuItemDetailResponse;
import net.cosmiclion.opms.UseCase;

import static com.google.common.base.Preconditions.checkNotNull;

public class GetQuickMenuItemDetail extends UseCase<GetQuickMenuItemDetail.RequestValues, GetQuickMenuItemDetail.ResponseValues> {
    private String TAG = this.getClass().getSimpleName();
    private final QuickMenuRepository mRepository;

    public GetQuickMenuItemDetail(@NonNull QuickMenuRepository repository) {
        this.mRepository = repository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        QuickMenuItemDetailRequest request = requestValues.getRequest();
        mRepository.getQuickMenuItemDetailResponse(request, new QuickMenuDataSource.LoadQuickMenuItemDetailCallback() {

            @Override
            public void onQuickMenuItemDetailLoaded(QuickMenuItemDetailResponse response) {
                getUseCaseCallback().onSuccess(new ResponseValues(response));
            }

            @Override
            public void onDataNotAvailable(String errorMessage) {
                getUseCaseCallback().onError();
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final QuickMenuItemDetailRequest mRequest;
        private final boolean mForceUpdate;

        public RequestValues(boolean forceUpdate, @NonNull QuickMenuItemDetailRequest request) {
            this.mForceUpdate = forceUpdate;
            this.mRequest = checkNotNull(request, "QuickMenuItemsRequest cannot null");
        }

        public boolean isForceUpdate() {
            return mForceUpdate;
        }

        public QuickMenuItemDetailRequest getRequest() {
            return mRequest;
        }
    }

    public static final class ResponseValues implements UseCase.ResponseValue {
        private final QuickMenuItemDetailResponse mResponse;

        public ResponseValues(@NonNull QuickMenuItemDetailResponse response) {
            this.mResponse = response;
        }

        public QuickMenuItemDetailResponse getResponse() {
            return mResponse;
        }
    }
}
