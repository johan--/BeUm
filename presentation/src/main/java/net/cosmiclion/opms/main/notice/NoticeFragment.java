package net.cosmiclion.opms.main.notice;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.cosmiclion.beum.R;
import net.cosmiclion.opms.main.notices.NoticesFragment;
import net.cosmiclion.opms.utils.Debug;


public class NoticeFragment extends Fragment implements View.OnClickListener {
    public String TAG = getClass().getSimpleName();

    public static final String FRAGMENT = "NoticeFragment";

    private TextView tvNoticeBack;
    private TextView tvNoticeTitle, tvNoticeContent;

    public NoticeFragment() {
        Debug.i(TAG,"NoticeFragment");
        // Required empty public constructor
    }

    public static NoticeFragment newInstance() {
        NoticeFragment fragment = new NoticeFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_item_notify).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notice_frag, container, false);
        Bundle bundle = getArguments();

        tvNoticeTitle = (TextView) view.findViewById(R.id.tvNoticeTitle);
        tvNoticeTitle.setText(bundle.getString(NoticesFragment.BUNDLE_KEY_NOTICE_TITLE));
        tvNoticeContent= (TextView) view.findViewById(R.id.tvNoticeContent);
        tvNoticeContent.setText(bundle.getString(NoticesFragment.BUNDLE_KEY_NOTICE_CONTENT));
        tvNoticeBack = (TextView) view.findViewById(R.id.tvNoticeBack);
        tvNoticeBack.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvNoticeBack:
                getActivity().onBackPressed();
                break;

            default:
                break;
        }

    }
}
