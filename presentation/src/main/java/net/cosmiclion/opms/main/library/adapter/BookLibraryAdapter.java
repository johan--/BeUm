package net.cosmiclion.opms.main.library.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import net.cosmiclion.beum.R;
import net.cosmiclion.opms.main.library.model.BookDomain;
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
    public static final int LAYOUT_GRID_TYPE = 100;
    public static final int LAYOUT_LIST_TYPE = 200;
    private final int ITEM_NOT_SELECTED = 0;
    private final int ITEM_SELECTED = 1;
    private final int ITEM_EVEN = 0, ITEM_ODD = 1;

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

    /**
     * This method creates different RecyclerView.ViewHolder objects based on the item view type.\
     *
     * @param viewGroup ViewGroup container for the item
     * @param viewType  type of view to be inflated
     * @return viewHolder to be inflated
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        BookLibraryAdapter.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        int layout = 0;
        if (itemLayoutType == LAYOUT_GRID_TYPE) {
//            layout = viewType == ITEM_NOT_SELECTED ? R.layout.grid_book_item_even : R.layout.grid_book_item_selected;
//            layout = R.layout.grid_book_item_even;
//            View v1 = inflater.inflate(layout, viewGroup, false);
//            viewHolder = new ViewHolder(v1, clickListener);
            switch (viewType) {
                case ITEM_EVEN:
                    layout = R.layout.grid_book_item_even;
                    View v3 = inflater.inflate(layout, viewGroup, false);
                    viewHolder = new ViewHolder(v3, clickListener);
                    break;
                case ITEM_ODD:
                    layout = R.layout.grid_book_item_odd;
                    View v4 = inflater.inflate(layout, viewGroup, false);
                    viewHolder = new ViewHolder(v4, clickListener);
                    break;
            }
        } else if (itemLayoutType == LAYOUT_LIST_TYPE) {
            switch (viewType) {
                case ITEM_EVEN:
                    layout = R.layout.list_book_item_even;
                    View v3 = inflater.inflate(layout, viewGroup, false);
                    viewHolder = new ViewHolder(v3, clickListener);
                    break;
                case ITEM_ODD:
                    layout = R.layout.list_book_item_odd;
                    View v4 = inflater.inflate(layout, viewGroup, false);
                    viewHolder = new ViewHolder(v4, clickListener);
                    break;
            }
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams sglp = (StaggeredGridLayoutManager.LayoutParams) lp;
            if (itemLayoutType == LAYOUT_LIST_TYPE) {
                sglp.setFullSpan(true);
            }
            holder.itemView.setLayoutParams(sglp);
        }
        // Highlight the item if it's selected
        holder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
        holder.imgPoint.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);

        final BookDomain item = items.get(position);
//        holder.title.setText(item.getTitle());
        holder.bookCover.setImageUrl(item.getCover(), imageLoader);
        holder.bookCover.setDefaultImageResId(R.drawable.book_cover);
        if (itemLayoutType == LAYOUT_GRID_TYPE) {

        } else if (itemLayoutType == LAYOUT_LIST_TYPE) {
//            holder.author.setText("Ronaldinho");
        }
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

//    @Override
//    public int getItemViewType(int position) {
//        final  item = items.get(position);
//        return item.isActive() ? TYPE_ACTIVE : TYPE_INACTIVE;
//    }

    //Returns the view type of the item at position for the purposes of view recycling.
    @Override
    public int getItemViewType(int position) {
        if (itemLayoutType == LAYOUT_GRID_TYPE) {
            if ((position % 4) == 3) {
                return ITEM_ODD;
            } else if((position % 4) == 2){
                return ITEM_ODD;
            }else{
                return ITEM_EVEN;
            }
        }else{
            if (position % 2 == 0) {
                return ITEM_EVEN;
            } else {
                return ITEM_ODD;
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @SuppressWarnings("unused")
        private static final String TAG = ViewHolder.class.getSimpleName();
        private TextView title;
        private TextView author;
        private View selectedOverlay;
        private NetworkImageView bookCover;
        private ImageLoader imageLoader;
        private ClickListener listener;
        private ImageView imgPoint;

        public ViewHolder(View itemView, ClickListener listener) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tvBookName);
            author = (TextView) itemView.findViewById(R.id.tvBookAuthor);
            bookCover = (NetworkImageView) itemView.findViewById(R.id.ivBookCover);
            selectedOverlay = itemView.findViewById(R.id.selected_overlay);
            imgPoint = (ImageView) itemView.findViewById(R.id.selected_point);
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
