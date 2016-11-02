package net.cosmiclion.opms.main.purchase.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import net.cosmiclion.beum.R;
import net.cosmiclion.opms.main.library.model.BookDomain;
import net.cosmiclion.opms.volley.VolleyManager;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by longpham on 10/31/2016.
 */
public class BooksPurchaseAdapter extends RecyclerView.Adapter<BooksPurchaseAdapter.ViewHolder> {

    private String TAG = getClass().getSimpleName();
    private List<BookDomain> mBooks;
    private ImageLoader imageLoader;
    private ViewHolder.ClickListener clickListener;

    public BooksPurchaseAdapter(ViewHolder.ClickListener clickListener, List<BookDomain> books) {
        super();
        this.mBooks = books;
        this.clickListener = clickListener;
    }

    public void replaceData(List<BookDomain> books) {
        setList(books);
        notifyDataSetChanged();
    }

    private void setList(List<BookDomain> books) {
        mBooks = checkNotNull(books);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchase_list_book_item, parent, false);
        return new ViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final BookDomain item = mBooks.get(position);

        holder.title.setText(item.getTitle());
        holder.author.setText("Author");
        holder.publisher.setText("Publisher");
        holder.bookCover.setImageUrl(item.getCover(), imageLoader);
        holder.bookCover.setDefaultImageResId(R.drawable.book_cover);

        if (position % 2 == 0) {
            holder.download.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }


    /* VIEW_HOLDER */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        @SuppressWarnings("unused")
        private static final String TAG = ViewHolder.class.getSimpleName();

        private TextView title;
        private TextView author;
        private TextView publisher;
        private Button download;

        View selectedOverlay;
        NetworkImageView bookCover;
        ImageLoader imageLoader;
        private ClickListener listener;

        public ViewHolder(View itemView, ClickListener listener) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.tvBookName);
            author = (TextView) itemView.findViewById(R.id.tvBookAuthor);
            publisher = (TextView) itemView.findViewById(R.id.tvBookPublisher);
            download = (Button) itemView.findViewById(R.id.btnPurchaseDownload);

            bookCover = (NetworkImageView) itemView.findViewById(R.id.ivBookCover);
            selectedOverlay = itemView.findViewById(R.id.selected_overlay);
            this.listener = listener;
            if (imageLoader == null) {
                imageLoader = VolleyManager.getInstance(itemView.getContext()).getImageLoader();
            }

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


}