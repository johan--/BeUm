package net.cosmiclion.beum.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;

import net.cosmiclion.beum.R;
import net.cosmiclion.beum.main.config.ConfigFragment;
import net.cosmiclion.beum.main.mylibrary.MyLibraryFragment;
import net.cosmiclion.beum.main.purchaselist.PurchaseListFragment;
import net.cosmiclion.beum.main.quickmenu.QuickMenuFragment;
import net.cosmiclion.beum.utils.ActivityUtils;
import net.cosmiclion.data.utils.Debug;

public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getName();

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_act);

        setupToolBar();
        setupDrawer();
        setupBottomNavigation();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //dont call **super**, if u want disable back button in current screen.
        //create a dialog to ask yes no question whether or not the user wants to exit
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public String getApplicationName() {
        int stringId = this.getApplicationInfo().labelRes;
        return this.getString(stringId);
    }

    private void setupToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
//        ab.setIcon(R.drawable.ic_opms);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setupDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.list_navigation_menu_item:
                                // Do nothing, we're already on that screen
                                Debug.i(TAG, "Logout");
                                break;

                            default:
                                break;
                        }
                        // Close the navigation drawer when an item is selected.
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    TextView mTextView;

    private void setupBottomNavigation() {
        QuickMenuFragment quickMenuFragment = QuickMenuFragment.newInstance("");
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), quickMenuFragment, R.id.contentFrame);

        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                final FragmentTransaction transaction = getSupportFragmentManager()
                        .beginTransaction();
                switch (item.getItemId()) {
                    case R.id.action_one:
//                        mTextView.setText(item.getTitle().toString().toUpperCase());

                        fragment = QuickMenuFragment.newInstance("");
//                        transaction.replace(R.id.contentFrame, fragment, "ID next fragment");
//                        transaction.commit();
                        break;
                    case R.id.action_two:
//                        mTextView.setText(item.getTitle().toString().toUpperCase());
                        fragment = MyLibraryFragment.newInstance("");
//                        transaction.replace(R.id.contentFrame, fragment, "ID next fragment");
//                        transaction.commit();
                        break;
                    case R.id.action_three:
                        fragment = PurchaseListFragment.newInstance("");
                        break;
                    case R.id.action_four:
                        fragment = ConfigFragment.newInstance("");
                        break;
                }
                transaction.replace(R.id.contentFrame, fragment, "ID next fragment");
                transaction.commit();
                return true;
            }
        });


        // Setup view pager
        mTextView = (TextView) findViewById(R.id.text);
//        final CustomViewPager viewpager = (CustomViewPager) findViewById(R.id.viewpager);
//        viewpager.setAdapter(new MainPagerAdapter(this, getSupportFragmentManager(), "deviceid"));
//        viewpager.setOffscreenPageLimit(MainPagerAdapter.NUMB_ITEMS);
//        viewpager.setCurrentItem(0);
//
////      viewpager.setPagingEnabledd(true);
////        updatePage(viewpager.getCurrentItem());
//
////        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
////        tabLayout.setupWithViewPager(viewpager);
//
//        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            int curr = 0;
//            @Override
//            public void onPageScrolled(int i, float v, int i1) {
//            }
//
//            @Override
//            public void onPageSelected(int i) {
////                updatePage(i);
//                Log.d(TAG,i+"");
//                updatePage(i);
//                bottomNavigationView.getMenu().getItem(0).setChecked(false);
//                bottomNavigationView.getMenu().getItem(1).setChecked(true);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int i) {
//            }
//        });

    }

    private void updatePage(int selectedPage) {
        switch (selectedPage) {
            case MainPagerAdapter.QUICK_MENU_LAYOUT:
                Log.d(TAG, "QUICK_MENU_LAYOUT");
                break;
            case MainPagerAdapter.LIBRARY_LAYOUT:
                Log.d(TAG, "LIBRARY_LAYOUT");
                break;
            default:
                break;
        }
    }
}
