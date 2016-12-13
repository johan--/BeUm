package net.cosmiclion.opms.main.quickmenu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.squareup.picasso.Picasso;

import net.cosmiclion.beum.R;
import net.cosmiclion.opms.main.purchase.model.BookPurchaseDomain;
import net.cosmiclion.opms.utils.Debug;

import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by longpham on 10/31/2016.
 */
public class QuickMenuAdapter extends RecyclerView.Adapter<QuickMenuAdapter.ViewHolder> {

    private String TAG = getClass().getSimpleName();
    private static List<BookPurchaseDomain> mBooks;
    private ViewHolder.ClickListener clickListener;
    private Context mContext;

    public QuickMenuAdapter(ViewHolder.ClickListener clickListener,
                            List<BookPurchaseDomain> books,
                            Context context) {
        super();
        this.mBooks = books;
        this.clickListener = clickListener;
        this.mContext=context;
    }

    public void replaceData(List<BookPurchaseDomain> books) {
        Collections.reverse(books);
        setList(books);
        notifyDataSetChanged();
    }

    private void setList(List<BookPurchaseDomain> books) {
        mBooks = checkNotNull(books);
    }

    public List<BookPurchaseDomain> getBooks() {
        return mBooks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.grid_book_item_even, parent, false);
        return new ViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final BookPurchaseDomain item = mBooks.get(position);

        String urlCoverBook = item.base_cover_image + "/" + item.product_id + "/" + item.cover_image2;
        Debug.i(TAG, "urlCoverBook=" + item.cover_image2);
        Picasso.with(mContext)
                .load(item.cover_image2)
                .placeholder(R.drawable.book_cover)
                .error(R.drawable.book_cover)
                .into(holder.bookCover);

        // Span the item if active
//        final ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
//        if (lp instanceof StaggeredGridLayoutManager.LayoutParams && position == 0) {
//            StaggeredGridLayoutManager.LayoutParams sglp = (StaggeredGridLayoutManager.LayoutParams) lp;
//            sglp.setFullSpan(true);
//            holder.itemView.setLayoutParams(sglp);
//        }
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

        View selectedOverlay;
        ImageView bookCover;
        ImageLoader imageLoader;
        private ClickListener listener;

        public ViewHolder(View itemView, ClickListener listener) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.tvBookName);
            bookCover = (ImageView) itemView.findViewById(R.id.ivBookCover);
            selectedOverlay = itemView.findViewById(R.id.selected_overlay);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                int pos = getLayoutPosition();
                listener.onItemClicked(pos, mBooks.get(pos));
            }
        }

        public interface ClickListener {
            void onItemClicked(int position, BookPurchaseDomain book);
        }
    }


}