package net.cosmiclion.opms.main.purchase.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.cosmiclion.beum.R;
import net.cosmiclion.opms.login.LoginPresenter;
import net.cosmiclion.opms.main.purchase.model.BookPurchaseDomain;
import net.cosmiclion.opms.utils.Debug;
import net.cosmiclion.opms.utils.Downloader;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by longpham on 10/31/2016.
 */
public class BooksPurchaseAdapter extends RecyclerView.Adapter<BooksPurchaseAdapter.ViewHolder> {

    private final int ITEM_EVEN = 0, ITEM_ODD = 1;
    private String TAG = getClass().getSimpleName();
    private List<BookPurchaseDomain> mBooks;
    private ViewHolder.ClickListener clickListener;
    private Context mContext;
    private String mToken;
    private Downloader mDownloader;


    public BooksPurchaseAdapter(
            ViewHolder.ClickListener clickListener,
            List<BookPurchaseDomain> books,
            Context context) {
        super();
        this.mBooks = books;
        this.clickListener = clickListener;
        this.mContext = context;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mToken = sharedPreferences.getString(LoginPresenter.PARAMS_MOBILE_TOKEN, "");

        mDownloader = new Downloader(mContext, mToken);
    }

    private void setList(List<BookPurchaseDomain> books) {
        mBooks = checkNotNull(books);
    }

    public void replaceData(List<BookPurchaseDomain> books) {
        setList(books);
        notifyDataSetChanged();
    }

    public List<BookPurchaseDomain> getBooks() {
        return mBooks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        BooksPurchaseAdapter.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        int layout = 0;
        switch (viewType) {
            case ITEM_EVEN:
                layout = R.layout.purchase_list_book_item_even;
                View v3 = inflater.inflate(layout, viewGroup, false);
                viewHolder = new BooksPurchaseAdapter.ViewHolder(v3, clickListener);
                break;
            case ITEM_ODD:
                layout = R.layout.purchase_list_book_item_odd;
                View v4 = inflater.inflate(layout, viewGroup, false);
                viewHolder = new BooksPurchaseAdapter.ViewHolder(v4, clickListener);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final BookPurchaseDomain item = mBooks.get(position);

        holder.title.setText(item.product_title);
        holder.author.setText(item.product_author);
        holder.publisher.setText(item.product_translator);

//        String urlCoverBook = item.base_cover_image + "/" + item.product_id + "/" + item.cover_image2;
//        Debug.i(TAG, "urlCoverBook1=" + urlCoverBook);
        Debug.i(TAG, "urlCoverBook2=" + item.cover_image2);
        Picasso.with(mContext)
                .load(item.cover_image2)
                .placeholder(R.drawable.book_cover)
                .error(R.drawable.book_cover)
                .into(holder.bookCover);

//        if (position % 2 == 0) {
//            holder.download.setVisibility(View.INVISIBLE);
//        }

        if (item.isFileExists(mContext)) {
            holder.download.setVisibility(View.INVISIBLE);
        } else {
            holder.download.setVisibility(View.VISIBLE);
            holder.download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDownloader.start(item.product_id, item.filename, new Downloader.OnDownloadListener() {
                        @Override
                        public void onStateChanged(String state) {
                            Log.d(TAG, state);
                            mDownloadListener.onDownloadClicked(state, item);

                        }
                    });

                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return ITEM_EVEN;
        } else {
            return ITEM_ODD;
        }
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    /* VIEW_HOLDER */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @SuppressWarnings("unused")
        private static final String TAG = ViewHolder.class.getSimpleName();

        private TextView title;
        private TextView author;
        private TextView publisher;
        private TextView download;

        View selectedOverlay;
        ImageView bookCover;
        private ClickListener listener;

        public ViewHolder(View itemView, ClickListener listener) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.tvBookName);
            author = (TextView) itemView.findViewById(R.id.tvBookAuthor);
            publisher = (TextView) itemView.findViewById(R.id.tvBookPublisher);
            download = (TextView) itemView.findViewById(R.id.btnPurchaseDownload);

            bookCover = (ImageView) itemView.findViewById(R.id.ivBookCover);
            selectedOverlay = itemView.findViewById(R.id.selected_overlay);
            this.listener = listener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClicked(getLayoutPosition());
            }
        }

        public interface ClickListener {
            void onItemClicked(int position);
        }
    }

    private DownloadClickListener mDownloadListener;

    public interface DownloadClickListener {
        void onDownloadClicked(String state, BookPurchaseDomain book);
    }

    public void setOnDownloadClickListener(DownloadClickListener downloadClickListener) {
        this.mDownloadListener = downloadClickListener;
    }


}