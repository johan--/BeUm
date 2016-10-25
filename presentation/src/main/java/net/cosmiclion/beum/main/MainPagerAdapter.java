package net.cosmiclion.beum.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.cosmiclion.beum.R;
import net.cosmiclion.beum.main.mylibrary.MyLibraryRootFragment;
import net.cosmiclion.beum.main.quickmenu.QuickMenuRootFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {

	public static final int NUMB_ITEMS = 2;
	public static final int QUICK_MENU_LAYOUT = 0;
	public static final int LIBRARY_LAYOUT = 1;
	public static final int PURCHASE_LIST_LAYOUT = 2;
	public static final int CONFIGURATION_LAYOUT = 3;

	private Context context;
	private String deviceId;

	public MainPagerAdapter(Context context, FragmentManager fm, String deviceId) {
		super(fm);
		this.context = context;
		this.deviceId = deviceId;
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case QUICK_MENU_LAYOUT:
			return QuickMenuRootFragment.newInstance(deviceId);
		case LIBRARY_LAYOUT:
			return MyLibraryRootFragment.newInstance(deviceId);
//		case FAVORITES_POS:
//			return FavoritesFragment.newInstance();
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

		default:
			return "";
		}
	}

	@Override
	public int getCount() {
		return NUMB_ITEMS;
	}
}
