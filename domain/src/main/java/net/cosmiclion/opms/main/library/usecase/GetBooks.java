package net.cosmiclion.opms.main.library.usecase;

import android.support.annotation.NonNull;

import net.cosmiclion.opms.UseCase;
import net.cosmiclion.opms.main.library.model.BookDomain;
import net.cosmiclion.opms.main.library.model.BooksResponseMapper;
import net.cosmiclion.opms.main.mylibrary.model.BooksRequest;
import net.cosmiclion.opms.main.mylibrary.model.BooksResponse;
import net.cosmiclion.opms.main.mylibrary.source.LibraryDataSource;
import net.cosmiclion.opms.main.mylibrary.source.LibraryRepository;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class GetBooks extends UseCase<GetBooks.RequestValues, GetBooks.ResponseValues> {
    private String TAG = this.getClass().getSimpleName();
    private final LibraryRepository mRepository;

    public GetBooks(@NonNull LibraryRepository repository) {
        this.mRepository = repository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        BooksRequest request = requestValues.getRequest();
        mRepository.getBooksResponse(request, new LibraryDataSource.LoadLibraryCallback() {

            @Override
            public void onBooksLoaded(BooksResponse response) {
                getUseCaseCallback().onSuccess(new ResponseValues(response));
            }

            @Override
            public void onDataNotAvailable(String errorMessage) {
                getUseCaseCallback().onError();
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final BooksRequest mRequest;
        private final boolean mForceUpdate;

        public RequestValues(boolean forceUpdate, @NonNull BooksRequest request) {
            this.mForceUpdate = forceUpdate;
            this.mRequest = checkNotNull(request, "BooksRequest cannot null");
        }

        public boolean isForceUpdate() {
            return mForceUpdate;
        }

        public BooksRequest getRequest() {
            return mRequest;
        }
    }

    public static final class ResponseValues implements UseCase.ResponseValue {
        private final List<BookDomain> mResponse;

        public ResponseValues(@NonNull BooksResponse response) {
            List<BookDomain> itemsDomain = BooksResponseMapper.transform(response);
            this.mResponse = checkNotNull(itemsDomain, "mBaseValueReponse cannot null");
        }

        public List<BookDomain> getResponse() {
            return mResponse;
        }
    }
}
