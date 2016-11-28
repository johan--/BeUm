package net.cosmiclion.opms.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.cosmiclion.beum.R;
import net.cosmiclion.opms.main.config.ConfigFragment;
import net.cosmiclion.opms.main.library.LibraryFragment;
import net.cosmiclion.opms.main.purchase.PurchaseFragment;
import net.cosmiclion.opms.main.quickmenu.QuickMenuFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {

    public static final int NUMB_ITEMS = 4;
    public final int QUICK_MENU_LAYOUT = 0;
    public final int LIBRARY_LAYOUT = 1;
    public final int PURCHASE_LIST_LAYOUT = 2;
    public final int CONFIGURATION_LAYOUT = 3;

    private Context context;

    public MainPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case QUICK_MENU_LAYOUT:
                return QuickMenuFragment.newInstance();
            case LIBRARY_LAYOUT:
                return LibraryFragment.newInstance();
            case PURCHASE_LIST_LAYOUT:
                return PurchaseFragment.newInstance();
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
                return context.getString(R.string.quick_menu_tab_name);
            case LIBRARY_LAYOUT:
                return context.getString(R.string.my_library_tab_name);
            case PURCHASE_LIST_LAYOUT:
                return context.getString(R.string.purchase_list_tab_name);
            case CONFIGURATION_LAYOUT:
                return context.getString(R.string.configuration_tab_name);
            default:
                return context.getString(R.string.quick_menu_tab_name);
        }
    }

    @Override
    public int getCount() {
        return NUMB_ITEMS;
    }
}
