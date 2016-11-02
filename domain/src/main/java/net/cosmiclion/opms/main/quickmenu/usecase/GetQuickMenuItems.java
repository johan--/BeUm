package net.cosmiclion.opms.main.quickmenu.usecase;

import android.support.annotation.NonNull;

import net.cosmiclion.opms.main.quickmenu.QuickMenuDataSource;
import net.cosmiclion.opms.main.quickmenu.QuickMenuRepository;
import net.cosmiclion.opms.main.quickmenu.model.QuickMenuItemsRequest;
import net.cosmiclion.opms.main.quickmenu.model.QuickMenuItemsResponse;
import net.cosmiclion.opms.UseCase;
import net.cosmiclion.opms.main.quickmenu.model.QuickMenuItem;
import net.cosmiclion.opms.main.quickmenu.model.QuickMenuItemsResponseMapper;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class GetQuickMenuItems extends UseCase<GetQuickMenuItems.RequestValues, GetQuickMenuItems.ResponseValues> {
    private String TAG = this.getClass().getSimpleName();
    private final QuickMenuRepository mRepository;

    public GetQuickMenuItems(@NonNull QuickMenuRepository repository) {
        this.mRepository = repository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        QuickMenuItemsRequest request = requestValues.getRequest();
        mRepository.getQuickMenuItemsResponse(request, new QuickMenuDataSource.LoadQuickMenuItemsCallback() {

            @Override
            public void onQuickMenuItemsLoaded(QuickMenuItemsResponse response) {
                getUseCaseCallback().onSuccess(new ResponseValues(response));
            }

            @Override
            public void onDataNotAvailable(String errorMessage) {
                getUseCaseCallback().onError();
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final QuickMenuItemsRequest mRequest;
        private final boolean mForceUpdate;

        public RequestValues(boolean forceUpdate, @NonNull QuickMenuItemsRequest request) {
            this.mForceUpdate = forceUpdate;
            this.mRequest = checkNotNull(request, "QuickMenuItemsRequest cannot null");
        }

        public boolean isForceUpdate() {
            return mForceUpdate;
        }

        public QuickMenuItemsRequest getRequest() {
            return mRequest;
        }
    }

    public static final class ResponseValues implements UseCase.ResponseValue {
        private final List<QuickMenuItem> mResponse;

        public ResponseValues(@NonNull QuickMenuItemsResponse response) {
            List<QuickMenuItem> quickMenuItem = QuickMenuItemsResponseMapper.transform(response);
            this.mResponse = checkNotNull(quickMenuItem, "mBaseValueReponse cannot null");
        }

        public List<QuickMenuItem> getResponse() {
            return mResponse;
        }
    }
}
