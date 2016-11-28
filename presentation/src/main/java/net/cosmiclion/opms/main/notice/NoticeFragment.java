package net.cosmiclion.opms.main.notice;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.cosmiclion.beum.R;


public class NoticeFragment extends Fragment implements View.OnClickListener{
    public String TAG = getClass().getSimpleName();

    public static final String FRAGMENT = "NoticeFragment";

    private TextView tvNoticeBack;

    public NoticeFragment() {
        // Required empty public constructor
    }

    public static NoticeFragment newInstance() {
        NoticeFragment fragment = new NoticeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notice_frag, container, false);

        tvNoticeBack = (TextView) view.findViewById(R.id.tvNoticeBack);
        tvNoticeBack.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvNoticeBack:
                getActivity().onBackPressed();
                break;

            default:
                break;
        }

    }
}
