package net.cosmiclion.opms.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.cosmiclion.beum.R;
import net.cosmiclion.opms.UseCaseHandler;
import net.cosmiclion.opms.main.config.ConfigFragment;
import net.cosmiclion.opms.main.library.LibraryFragment;
import net.cosmiclion.opms.main.purchase.PurchaseFragment;
import net.cosmiclion.opms.main.quickmenu.QuickMenuFragment;
import net.cosmiclion.opms.main.quickmenu.QuickMenuPresenter;
import net.cosmiclion.opms.main.quickmenu.QuickMenuRepository;
import net.cosmiclion.opms.main.quickmenu.source.local.QuickMenuLocalDataSource;
import net.cosmiclion.opms.main.quickmenu.source.remote.QuickMenuRemoteDataSource;
import net.cosmiclion.opms.main.quickmenu.usecase.GetQuickMenuItemDetail;
import net.cosmiclion.opms.main.quickmenu.usecase.GetQuickMenuItems;

public class MainPagerAdapter extends FragmentPagerAdapter {

    private final String TAG = getClass().getSimpleName();
    public static final int NUMB_ITEMS = 4;
    public final int QUICK_MENU_LAYOUT = 0;
    public final int LIBRARY_LAYOUT = 1;
    public final int PURCHASE_LIST_LAYOUT = 2;
    public final int CONFIGURATION_LAYOUT = 3;

    private Context mContext;

    private QuickMenuFragment quickMenuFragment;
    private LibraryFragment libraryFragment;
    private PurchaseFragment purchaseFragment;

    public MainPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;

        quickMenuFragment = QuickMenuFragment.newInstance();
        new QuickMenuPresenter(UseCaseHandler.getInstance(), quickMenuFragment,
                new GetQuickMenuItems(QuickMenuRepository.getInstance(
                        QuickMenuRemoteDataSource.getInstance(mContext),
                        QuickMenuLocalDataSource.getInstance(mContext))),
                new GetQuickMenuItemDetail(QuickMenuRepository.getInstance(
                        QuickMenuRemoteDataSource.getInstance(mContext),
                        QuickMenuLocalDataSource.getInstance(mContext)))
        );
        libraryFragment = LibraryFragment.newInstance();
        purchaseFragment = PurchaseFragment.newInstance();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case QUICK_MENU_LAYOUT:
                return quickMenuFragment;
            case LIBRARY_LAYOUT:
                return libraryFragment;
            case PURCHASE_LIST_LAYOUT:
                return purchaseFragment;
            case CONFIGURATION_LAYOUT:
                return ConfigFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case QUICK_MENU_LAYOUT:
                return mContext.getString(R.string.quick_menu_tab_name);
            case LIBRARY_LAYOUT:
                return mContext.getString(R.string.my_library_tab_name);
            case PURCHASE_LIST_LAYOUT:
                return mContext.getString(R.string.purchase_list_tab_name);
            case CONFIGURATION_LAYOUT:
                return mContext.getString(R.string.configuration_tab_name);
            default:
                return mContext.getString(R.string.quick_menu_tab_name);
        }
    }

    @Override
    public int getCount() {
        return NUMB_ITEMS;
    }
}
