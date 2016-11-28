package net.cosmiclion.opms.main.notices.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.cosmiclion.beum.R;

import java.util.List;

/**
 * Created by longpham on 11/1/2016.
 */
public class NoticesAdapter extends RecyclerView.Adapter<NoticesAdapter.ViewHolder> {
    @SuppressWarnings("unused")
    private String TAG = getClass().getSimpleName();
    private final int ITEM_EVEN = 0, ITEM_ODD = 1;
    private ViewHolder.ClickListener clickListener;
    private List<String> notices;

    public NoticesAdapter(ViewHolder.ClickListener clickListener, List<String> notices) {
        super();
        this.notices = notices;
        this.clickListener = clickListener;
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
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        NoticesAdapter.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        int layout = 0;
        switch (viewType) {
            case ITEM_EVEN:
                layout = R.layout.notices_list_item_even;
                View v1 = inflater.inflate(layout, viewGroup, false);
                viewHolder = new NoticesAdapter.ViewHolder(v1, clickListener);
                break;
            case ITEM_ODD:
                layout = R.layout.notices_list_item_odd;
                View v2 = inflater.inflate(layout, viewGroup, false);
                viewHolder = new NoticesAdapter.ViewHolder(v2, clickListener);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(notices.get(position));

    }

    @Override
    public int getItemCount() {
        return notices.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @SuppressWarnings("unused")
        private static final String TAG = ViewHolder.class.getSimpleName();
        private TextView title;
        private ClickListener listener;

        public ViewHolder(View itemView, ClickListener listener) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tvNotice);
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
}