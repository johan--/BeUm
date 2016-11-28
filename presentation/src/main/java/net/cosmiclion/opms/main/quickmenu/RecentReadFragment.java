package net.cosmiclion.opms.main.quickmenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.cosmiclion.beum.R;
import net.cosmiclion.opms.main.MainActivity;
import net.cosmiclion.opms.utils.tasks.BookItem;

/**
 * Created by longpham on 11/17/2016.
 */

public class RecentReadFragment extends Fragment {
    // Store instance variables
    private String title;
    private int page;

    // newInstance constructor for creating fragment with arguments
    public static RecentReadFragment newInstance(String bookTitle) {
        RecentReadFragment fragmentFirst = new RecentReadFragment();
        Bundle args = new Bundle();
//        args.putInt("someInt", page);
        args.putString("someTitle", bookTitle);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recent_read_frag, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.tvLabel);
        tvLabel.setText(page + " -- " + title);
        ImageView imageView = (ImageView) view.findViewById(R.id.ivBook);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "llll");
                ((MainActivity) getActivity()).openDemoDocument(
                        new BookItem(0, "Book Title", 0, "BK0000314002.epub", 0));
            }
        });
        return view;
    }
}