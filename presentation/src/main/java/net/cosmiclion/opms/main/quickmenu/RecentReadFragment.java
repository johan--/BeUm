package net.cosmiclion.opms.main.quickmenu;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import net.cosmiclion.beum.R;
import net.cosmiclion.opms.main.MainActivity;
import net.cosmiclion.opms.main.purchase.model.BookPurchaseData;
import net.cosmiclion.opms.utils.Debug;
import net.cosmiclion.opms.utils.tasks.BookItem;

import static net.cosmiclion.opms.utils.Constants.APP_PATH;

/**
 * Created by longpham on 11/17/2016.
 */

public class RecentReadFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();
    private String coverImage;
    private String filename;
    private String product_title;

    public static RecentReadFragment newInstance(BookPurchaseData book) {
        RecentReadFragment fragmentFirst = new RecentReadFragment();
        Bundle args = new Bundle();
        args.putString("IMAGE", book.cover_image1);
        args.putString("FILE_NAME", book.filename);
        args.putString("TITLE", book.product_title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        coverImage = getArguments().getString("IMAGE");
        filename = getArguments().getString("FILE_NAME");
        product_title = getArguments().getString("TITLE");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recent_read_frag, container, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.ivBook);
        Debug.i(TAG, filename + "-Image_path_recent_read=" + APP_PATH + "/" + coverImage);
        imageView.setImageBitmap(BitmapFactory.decodeFile(APP_PATH + "/" + coverImage));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "llll");
                ((MainActivity) getActivity()).openDemoDocument(
                        new BookItem(0, product_title, 0, filename, 0));
            }
        });

        return view;
    }
}