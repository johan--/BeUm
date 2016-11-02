package net.cosmiclion.opms.main.library.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import net.cosmiclion.beum.R;
import net.cosmiclion.opms.main.library.model.BookDomain;
import net.cosmiclion.opms.utils.Debug;
import net.cosmiclion.opms.volley.VolleyManager;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by longpham on 10/29/2016.
 */
public class BookLibraryAdapter extends SelectableAdapter<BookLibraryAdapter.ViewHolder> {
    @SuppressWarnings("unused")
    private static final String TAG = BookLibraryAdapter.class.getSimpleName();

    private static final int TYPE_INACTIVE = 0;
    private static final int TYPE_ACTIVE = 1;

    private ImageLoader imageLoader;
    private List<BookDomain> items;
    private int itemLayoutType = 0;
    private ViewHolder.ClickListener clickListener;

    public BookLibraryAdapter(ViewHolder.ClickListener clickListener, List<BookDomain> items, int viewType) {
        super();
        this.clickListener = clickListener;
        this.items = items;
        this.itemLayoutType = viewType;
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void removeItems(List<Integer> positions) {
        // Reverse-sort the list
        Collections.sort(positions, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return rhs - lhs;
            }
        });

        // Split the list in ranges
        while (!positions.isEmpty()) {
            if (positions.size() == 1) {
                removeItem(positions.get(0));
                positions.remove(0);
            } else {
                int count = 1;
                while (positions.size() > count && positions.get(count).equals(positions.get(count - 1) - 1)) {
                    ++count;
                }

                if (count == 1) {
                    removeItem(positions.get(0));
                } else {
                    removeRange(positions.get(count - 1), count);
                }

                for (int i = 0; i < count; ++i) {
                    positions.remove(0);
                }
            }
        }
    }

    private void removeRange(int positionStart, int itemCount) {
        for (int i = 0; i < itemCount; ++i) {
            items.remove(positionStart);
        }
        notifyItemRangeRemoved(positionStart, itemCount);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = 0;

        if(itemLayoutType == 1){
            layout = viewType == TYPE_INACTIVE ? R.layout.grid_book_item : R.layout.grid_book_item_selected;
        }else if(itemLayoutType == 0){
            layout = viewType == TYPE_INACTIVE ? R.layout.list_book_item : R.layout.list_book_item_selected;
        }
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v, clickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final BookDomain item = items.get(position);

        holder.title.setText(item.getTitle());
        holder.bookCover.setImageUrl(item.getCover(), imageLoader);
        holder.bookCover.setDefaultImageResId(R.drawable.book_cover);
        // Span the item if active
        final ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams sglp = (StaggeredGridLayoutManager.LayoutParams) lp;
            if(itemLayoutType == 0){
                sglp.setFullSpan(true);
            }
            holder.itemView.setLayoutParams(sglp);
        }

        // Highlight the item if it's selected
        Debug.i(TAG, "isSelected=" + isSelected(position));
        holder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

//    @Override
//    public int getItemViewType(int position) {
////        final  item = items.get(position);
//        return item.isActive() ? TYPE_ACTIVE : TYPE_INACTIVE;
//    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @SuppressWarnings("unused")
        private static final String TAG = ViewHolder.class.getSimpleName();

        TextView title;
//        TextView subtitle;
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
