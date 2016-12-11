package net.cosmiclion.opms.main.notices;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.cosmiclion.beum.R;
import net.cosmiclion.opms.main.notice.NoticeFragment;

public class NoticesRootFragment extends Fragment {

    public static NoticesRootFragment newInstance() {
        NoticesRootFragment fragment = new NoticesRootFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.notices_root_frag, container, false);

        FragmentTransaction mTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        NoticeFragment noticesFragment = NoticeFragment.newInstance();
        noticesFragment.setArguments(getArguments());
//        mTransaction.replace(R.id.childNoticesFrame, noticesFragment, NoticesFragment.FRAGMENT);
        mTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        mTransaction.addToBackStack(null);
        mTransaction.commit();
//
//        Context context = getActivity();
//        new NoticesPresenter(
//                UseCaseHandler.getInstance(),
//                noticesFragment,
//                new DoGetNotices(
//                        NoticesRepository.getInstance(
//                                NoticesRemoteDataSource.getInstance(context),
//                                NoticesLocalDataSource.getInstance(context))));
        return view;
    }


}
