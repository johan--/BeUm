package net.cosmiclion.opms.main.quickmenu;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import net.cosmiclion.beum.R;
import net.cosmiclion.opms.main.quickmenu.model.QuickMenuItem;
import net.cosmiclion.opms.volley.VolleyManager;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by longpham on 10/31/2016.
 */
public class QuickMenuAdapter extends RecyclerView.Adapter<QuickMenuAdapter.ViewHolder> {

    private String TAG = getClass().getSimpleName();
    private List<QuickMenuItem> mBooks;
    private ImageLoader imageLoader;
    private ViewHolder.ClickListener clickListener;

    public QuickMenuAdapter(ViewHolder.ClickListener clickListener, List<QuickMenuItem> books) {
        super();
        this.mBooks = books;
        this.clickListener = clickListener;
    }

    public void replaceData(List<QuickMenuItem> books) {
        setList(books);
        notifyDataSetChanged();
    }

    private void setList(List<QuickMenuItem> books) {
        mBooks = checkNotNull(books);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_book_item_even, parent, false);
        return new ViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final QuickMenuItem item = mBooks.get(position);

        holder.title.setText(item.getTitle());
        holder.bookCover.setImageUrl(item.getCover(), imageLoader);
        holder.bookCover.setDefaultImageResId(R.drawable.book_cover);
        // Span the item if active
        final ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp instanceof StaggeredGridLayoutManager.LayoutParams && position == 0) {
            StaggeredGridLayoutManager.LayoutParams sglp = (StaggeredGridLayoutManager.LayoutParams) lp;
            sglp.setFullSpan(true);
            holder.itemView.setLayoutParams(sglp);
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

        View selectedOverlay;
        NetworkImageView bookCover;
        ImageLoader imageLoader;
        private ClickListener listener;

        public ViewHolder(View itemView, ClickListener listener) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.tvBookName);
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