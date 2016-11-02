package net.cosmiclion.opms.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import net.cosmiclion.beum.R;
import net.cosmiclion.opms.UseCaseHandler;
import net.cosmiclion.opms.login.LoginActivity;
import net.cosmiclion.opms.main.config.ConfigFragment;
import net.cosmiclion.opms.main.help.HelpFragment;
import net.cosmiclion.opms.main.library.LibraryFragment;
import net.cosmiclion.opms.main.library.LibraryPresenter;
import net.cosmiclion.opms.main.library.usecase.GetBooks;
import net.cosmiclion.opms.main.mylibrary.source.LibraryRepository;
import net.cosmiclion.opms.main.mylibrary.source.local.LibraryLocalDataSource;
import net.cosmiclion.opms.main.mylibrary.source.remote.LibraryRemoteDataSource;
import net.cosmiclion.opms.main.notices.NoticesFragment;
import net.cosmiclion.opms.main.purchase.PurchaseFragment;
import net.cosmiclion.opms.main.quickmenu.QuickMenuFragment;
import net.cosmiclion.opms.main.quickmenu.QuickMenuPresenter;
import net.cosmiclion.opms.main.quickmenu.QuickMenuRepository;
import net.cosmiclion.opms.main.quickmenu.source.local.QuickMenuLocalDataSource;
import net.cosmiclion.opms.main.quickmenu.source.remote.QuickMenuRemoteDataSource;
import net.cosmiclion.opms.main.quickmenu.usecase.GetQuickMenuItemDetail;
import net.cosmiclion.opms.main.quickmenu.usecase.GetQuickMenuItems;
import net.cosmiclion.opms.utils.ActivityUtils;
import net.cosmiclion.opms.utils.Debug;

public class MainActivity extends AppCompatActivity {
    private String TAG = getClass().getName();

    private DrawerLayout mDrawerLayout;

    private QuickMenuPresenter mQuickMenuPresenter;

    private LibraryPresenter mLibraryPresenter;

    private BottomNavigationView mBottomNavigationView;

    private NavigationView mNavigationView;

    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_act);
        setupToolBar();
        setupDrawer();
        showQuickMenuFragment();
        setupBottomNavigation();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.menu_item_notify:
                Debug.i(TAG, "menu_item_notify");
                setBottomViewChecked();
                showNotification();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setIcon(R.drawable.ic_opms);

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                final FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true); // show back button

                    mToolBar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
                    mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    });
                } else {
                    setHomeMenu();
                }
            }
        });
    }

    /**
     * the menu layout has the 'add/new' menu item
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setupDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        if (mNavigationView != null) {
            setupDrawerContent(mNavigationView);
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        setBottomViewChecked();
                        switch (menuItem.getItemId()) {
                            case R.id.nav_item_home:
                                Debug.i(TAG, "Home");
                                showQuickMenuFragment();
                                break;
                            case R.id.nav_item_website:
                                Debug.i(TAG, "website");
                                String url = "cosmiclion.net";
                                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                                    url = "http://" + url;
                                }
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                startActivity(Intent.createChooser(intent, "Choose browser"));// Choose browser is arbitrary :)

                                break;
                            case R.id.nav_item_logout:
                                Debug.i(TAG, "logout");
                                Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(myIntent);
                                break;
                            case R.id.nav_item_help:
                                Debug.i(TAG, "help");
                                HelpFragment helpFragment = HelpFragment.newInstance();
                                transaction.replace(R.id.contentFrame, helpFragment, HelpFragment.FRAGMENT);
                                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                transaction.commit();
                                break;
                            default:
                                break;
                        }
                        // Close the navigation drawer when an item is selected.
//                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    private void showQuickMenuFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(QuickMenuFragment.FRAGMENT);
        if (fragment == null) {
            QuickMenuFragment quickMenuFragment = QuickMenuFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), quickMenuFragment, R.id.contentFrame);
            final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            mQuickMenuPresenter = new QuickMenuPresenter(UseCaseHandler.getInstance(), quickMenuFragment,
                    new GetQuickMenuItems(QuickMenuRepository.getInstance(
                            QuickMenuRemoteDataSource.getInstance(getApplicationContext()),
                            QuickMenuLocalDataSource.getInstance(getApplicationContext()))),
                    new GetQuickMenuItemDetail(QuickMenuRepository.getInstance(
                            QuickMenuRemoteDataSource.getInstance(getApplicationContext()),
                            QuickMenuLocalDataSource.getInstance(getApplicationContext())))
            );
            transaction.replace(R.id.contentFrame, quickMenuFragment, QuickMenuFragment.FRAGMENT);
            transaction.commit();
            if (mBottomNavigationView != null) {
                MenuItem item = mBottomNavigationView.getMenu().getItem(0);
                boolean isChecked = item.isChecked();
                if (!isChecked) {
                    item.setChecked(true);
                }
            }
        }
    }

    private void showLibraryFragment(FragmentManager fragmentManager, FragmentTransaction transaction, Fragment fragment) {
        fragment = fragmentManager.findFragmentByTag(LibraryFragment.FRAGMENT);
        if (fragment == null || !fragment.isVisible()) {
            mBottomNavigationView.getMenu().findItem(R.id.action_two).setChecked(true);
            LibraryFragment libraryFragment = LibraryFragment.newInstance();
            mLibraryPresenter = new LibraryPresenter(UseCaseHandler.getInstance(), libraryFragment,
                    new GetBooks(LibraryRepository.getInstance(
                            LibraryRemoteDataSource.getInstance(getApplicationContext()),
                            LibraryLocalDataSource.getInstance(getApplicationContext())))
            );
            transaction.replace(R.id.contentFrame, libraryFragment, LibraryFragment.FRAGMENT);
            transaction.commit();
        }

    }

    private void showPurchaseFragment(FragmentManager fragmentManager, FragmentTransaction transaction, Fragment fragment) {
        fragment = fragmentManager.findFragmentByTag(PurchaseFragment.FRAGMENT);
        if (fragment == null || !fragment.isVisible()) {
            mBottomNavigationView.getMenu().findItem(R.id.action_three).setChecked(true);
            PurchaseFragment purchaseListFragment = PurchaseFragment.newInstance();
            transaction.replace(R.id.contentFrame, purchaseListFragment, PurchaseFragment.FRAGMENT);
            transaction.commit();
        }
    }

    private void showConfigFragment(FragmentManager fragmentManager, FragmentTransaction transaction, Fragment fragment) {
        fragment = fragmentManager.findFragmentByTag(ConfigFragment.FRAGMENT);
        if (fragment == null || !fragment.isVisible()) {
            mBottomNavigationView.getMenu().findItem(R.id.action_four).setChecked(true);
            ConfigFragment configFragment = ConfigFragment.newInstance();
            transaction.replace(R.id.contentFrame, configFragment, ConfigFragment.FRAGMENT);
            transaction.commit();
        }
    }

    private void setupBottomNavigation() {

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setHorizontalFadingEdgeEnabled(false);

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = null;
                setHomeMenu();
                switch (item.getItemId()) {
                    case R.id.action_one:
                        showQuickMenuFragment();
                        break;
                    case R.id.action_two:
                        showLibraryFragment(fragmentManager, transaction, fragment);
                        break;
                    case R.id.action_three:
                        showPurchaseFragment(fragmentManager, transaction, fragment);
                        break;
                    case R.id.action_four:
                        showConfigFragment(fragmentManager, transaction, fragment);
                        break;
                }

                return true;
            }
        });
    }

    private void showNotification() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(NoticesFragment.FRAGMENT);
        if (fragment == null || (fragment != null && !fragment.isVisible())) {
            NoticesFragment noticesFragment = NoticesFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentFrame, noticesFragment, NoticesFragment.FRAGMENT);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    private void setBottomViewChecked() {
        mBottomNavigationView.getMenu().getItem(0).setChecked(false);
        mBottomNavigationView.getMenu().getItem(1).setChecked(false);
        mBottomNavigationView.getMenu().getItem(2).setChecked(false);
        mBottomNavigationView.getMenu().getItem(3).setChecked(false);
    }

    private void setHomeMenu() {
        mToolBar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);

            }
        });
    }
}
