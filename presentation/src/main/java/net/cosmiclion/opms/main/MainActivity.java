package net.cosmiclion.opms.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;

import net.cosmiclion.beum.R;
import net.cosmiclion.opms.ebookreader.skyepub.BookViewActivity;
import net.cosmiclion.opms.ebookreader.skyepub.SkyApplication;
import net.cosmiclion.opms.ebookreader.skyepub.SkySetting;
import net.cosmiclion.opms.login.LoginActivity;
import net.cosmiclion.opms.main.config.ConfigFragment;
import net.cosmiclion.opms.main.help.HelpFragment;
import net.cosmiclion.opms.main.notices.NoticesFragment;
import net.cosmiclion.opms.main.quickmenu.QuickMenuFragment;
import net.cosmiclion.opms.utils.Debug;
import net.cosmiclion.opms.utils.PromptDialogFragment;
import net.cosmiclion.opms.utils.tasks.BookItem;
import net.cosmiclion.opms.utils.tasks.DownloadDocumentTask;
import net.cosmiclion.opms.utils.tasks.ExtractAssetTask;

import java.io.File;
import java.util.ArrayList;

import static net.cosmiclion.opms.main.config.ConfigFragment.CONFIG_DOUBLE_PAGE;
import static net.cosmiclion.opms.main.config.ConfigFragment.CONFIG_DOUBLE_PAGE_KEY;
import static net.cosmiclion.opms.main.config.ConfigFragment.CONFIG_PAGE_ANIMATION_KEY;
import static net.cosmiclion.opms.main.config.ConfigFragment.CONFIG_PAGE_NONE;
import static net.cosmiclion.opms.main.config.ConfigFragment.CONFIG_ROTATE;
import static net.cosmiclion.opms.main.config.ConfigFragment.CONFIG_ROTATE_KEY;
import static net.cosmiclion.opms.main.config.ConfigFragment.CONFIG_THEME_KEY;
import static net.cosmiclion.opms.main.config.ConfigFragment.CONFIG_THEME_WHITE;

public class MainActivity extends AppCompatActivity implements
        QuickMenuFragment.ViewPurchaseListListener,
        PromptDialogFragment.DialogListener {

    private String TAG = getClass().getName();
    public static final String SCREEN_TAG = "SCREEN_TAG";
    public static final int NOTICES_SCREEN = 0;
    public static final int HELP_SCREEN = 1;
    private static final String BUNDLE_NUMB_FRAG = "BUNDLE_NUMB_FRAG";

    private DrawerLayout mDrawerLayout;
    private BottomNavigationView mBottomNavigationView;
    private NavigationView mNavigationView;
    private Toolbar mToolBar;
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mPrefsEditor;

    private AHBottomNavigation bottomNavigation;
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private AHBottomNavigationViewPager viewPager;

    private int mNumbFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_act);
        setupToolBar();
        setupDrawer();
//        showQuickMenuFragment();
//        setupBottomNavigation();
        setupAHBottomNavigation();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("message", "This is my message to be reloaded");
        outState.putInt(BUNDLE_NUMB_FRAG, mNumbFragment);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            String message = savedInstanceState.getString("message");
            mNumbFragment = savedInstanceState.getInt(BUNDLE_NUMB_FRAG);
//            Toast.makeText(this, mNumbFragment + "-" + message, Toast.LENGTH_LONG).show();
            setupHomeIcon();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.menu_item_notify:
                Debug.i(TAG, "menu_item_notify");
                showNotifyScreen();
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
            showPromptLogoutDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * the menu layout has the 'add/new' menu item
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);

        final MenuItem item = menu.findItem(R.id.menu_item_notify);
        MenuItemCompat.setActionView(item, R.layout.notify_counter);
        RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(item);

        TextView tv = (TextView) notifCount.findViewById(R.id.actionbar_notifcation_textview);
        tv.setText("12");

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(item);
            }
        };
        item.getActionView().setOnClickListener(onClickListener);
        ImageView ivMenuNotify = (ImageView) notifCount.findViewById(R.id.ivMenuNotify);
        ivMenuNotify.setOnClickListener(onClickListener);
        return super.onCreateOptionsMenu(menu);
    }

    public String getApplicationName() {
        int stringId = this.getApplicationInfo().labelRes;
        return this.getString(stringId);
    }

    private void setupToolBar() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(true);
//        ab.setIcon(R.drawable.ic_opms_logo);

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                final FragmentManager fm = getSupportFragmentManager();
                Debug.i(TAG, "mNumbFragment=" + mNumbFragment);
                mNumbFragment = fm.getBackStackEntryCount();
                setupHomeIcon();
            }
        });
    }

    private void setupHomeIcon() {
        if (mNumbFragment > 0) {
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
                        switch (menuItem.getItemId()) {
                            case R.id.nav_item_home:
                                Debug.i(TAG, "Home");
                                bottomNavigation.setCurrentItem(0);
                                break;
                            case R.id.nav_item_website:
                                Debug.i(TAG, "website");
                                String url = "malltest.wjopms.com";
                                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                                    url = "http://" + url;
                                }
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                startActivity(Intent.createChooser(intent, "Choose browser"));// Choose browser is arbitrary :)

                                break;
                            case R.id.nav_item_logout:
                                Debug.i(TAG, "logout");
                                showPromptLogoutDialog();
                                break;
                            case R.id.nav_item_help:
                                showHelpScreen();
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

    public void showConfigFragment(FragmentManager fragmentManager, FragmentTransaction transaction, Fragment fragment) {
        fragment = fragmentManager.findFragmentByTag(ConfigFragment.FRAGMENT);
        if (fragment == null || !fragment.isVisible()) {
            mBottomNavigationView.getMenu().findItem(R.id.action_four).setChecked(true);
            ConfigFragment configFragment = ConfigFragment.newInstance();
            transaction.replace(R.id.contentFrame, configFragment, ConfigFragment.FRAGMENT);
            transaction.commit();
        }
    }

    private void showHelpScreen() {
        FragmentTransaction mTransaction = getSupportFragmentManager().beginTransaction();

        HelpFragment helpFragment = HelpFragment.newInstance();
        mTransaction.replace(R.id.fragmentContainer, helpFragment, HelpFragment.FRAGMENT);
        mTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        mTransaction.addToBackStack(null);
        mTransaction.commit();
    }

    private void showNotifyScreen() {
        FragmentTransaction mTransaction = getSupportFragmentManager().beginTransaction();
        NoticesFragment noticesFragment = NoticesFragment.newInstance();

        mTransaction.add(R.id.fragmentContainer, noticesFragment, NoticesFragment.FRAGMENT);
        mTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        mTransaction.addToBackStack(null);
        mTransaction.commit();

//        Intent intent = new Intent(MainActivity.this, NoticesActivity.class);
//        String extraValue = null;
//        if (type == NOTICES_SCREEN) {
//            extraValue = NoticesFragment.class.getSimpleName();
//        } else if (type == HELP_SCREEN) {
//            extraValue = HelpFragment.class.getSimpleName();
//        }
//        intent.putExtra(MainActivity.SCREEN_TAG, extraValue);
//        startActivity(intent);
    }


    private void setBottomViewChecked() {
        mBottomNavigationView.getMenu().getItem(0).setChecked(false);
        mBottomNavigationView.getMenu().getItem(1).setChecked(false);
        mBottomNavigationView.getMenu().getItem(2).setChecked(false);
        mBottomNavigationView.getMenu().getItem(3).setChecked(false);
    }

    private void setHomeMenu() {
        mToolBar.setNavigationIcon(R.drawable.ic_menu);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void setupAHBottomNavigation() {
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation_lib);
        viewPager = (AHBottomNavigationViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new MainPagerAdapter(this, getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(MainPagerAdapter.NUMB_ITEMS);
        viewPager.setCurrentItem(0);
//        viewPager.setHorizontalFadingEdgeEnabled(true);
        viewPager.setPagingEnabled(true);

        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.quick_menu_tab_name, R.drawable.ic_quickmenu, R.color.basic_black);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.my_library_tab_name, R.drawable.ic_library, R.color.basic_black);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.purchase_list_tab_name, R.drawable.ic_purchase, R.color.basic_black);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.configuration_tab_name, R.drawable.ic_config, R.color.basic_black);

        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);
        bottomNavigationItems.add(item4);

        String accentColor = getResources().getString(R.color.yellow_700);
        String inActiveColor = getResources().getString(R.color.basic_black);

        bottomNavigation.setForceTitlesDisplay(true);
        bottomNavigation.addItems(bottomNavigationItems);
        bottomNavigation.setAccentColor(Color.parseColor(accentColor));
        bottomNavigation.setInactiveColor(Color.parseColor(inActiveColor));
        bottomNavigation.setColored(false);
        bottomNavigation.setCurrentItem(0);
//        bottomNavigation.setBehaviorTranslationEnabled(true);
//        bottomNavigation.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                viewPager.setCurrentItem(position, true);
                Debug.i(TAG, "wasSelected=" + wasSelected + " position=" + position);
                return true;
            }
        });

        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override
            public void onPositionChange(int y) {
                Debug.i(TAG, "BottomNavigation Position: =" + y);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                Debug.i(TAG, "frag id=" + viewPager.getCurrentItem());
                bottomNavigation.setCurrentItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    private void showPromptLogoutDialog() {
        Log.d(TAG, "show dialog");
        String title = getResources().getString(R.string.dialog_logout_title);
        String subTitle = getResources().getString(R.string.dialog_text_sort_subtitle);
        PromptDialogFragment dialog = new PromptDialogFragment();
        dialog.setDialogTitle(title);
        dialog.setDialogClickListener(this);
        dialog.show(getFragmentManager(), "DIALOG_PROMPT");
    }

    /**
     * Opens a demo document from assets directory
     */
    public void openDemoDocument(final BookItem item) {
        Log.d("openDemoDocument", "fileName = " + item.filePath);
        String appName = getApplicationName();
        if (SkySetting.getStorageDirectory() == null) {
            SkySetting.setStorageDirectory(getFilesDir().getAbsolutePath(),
                    appName);
        }
        final File demoDocumentFile = new File(getFilesDir(), item.filePath);
        if (demoDocumentFile.exists()) {
            Log.d(TAG, "demoDocumentFile");
            if (item.isPDF == 1) {
                openPlugPdfViewerAct(Uri.fromFile(demoDocumentFile));
            } else {
                openViewer(item);
            }
        } else {
            Log.d(TAG, "! demoDocumentFile");
            ExtractAssetTask task = new ExtractAssetTask(MainActivity.this, new DownloadDocumentTask.DownloadedFileCallback() {
                @Override
                public void onFileDownloaded(Uri uri) {
                    if (item.isPDF == 1) {
                        openPlugPdfViewerAct(uri);
                    } else {
                        openViewer(item);
                    }

                }
            });
            task.execute(item.filePath);
        }
    }

    private void openPlugPdfViewerAct(Uri uri) {
//        Intent intent = new Intent(this, PlugPDFView.class);
//        intent.putExtra("FILE_PATH", uri.getPath());
//        startActivity(intent);
//        finish();
    }

    private void openViewer(BookItem item) {
        try {
            SkyApplication app = (SkyApplication) getApplication();

            boolean isDoublePage;
            boolean isRotate;
            int themeIndex;
            int pageTransition = CONFIG_PAGE_NONE;

            mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            mPrefsEditor = mPrefs.edit();

            isDoublePage = mPrefs.getBoolean(CONFIG_DOUBLE_PAGE_KEY, CONFIG_DOUBLE_PAGE);
            isRotate = !mPrefs.getBoolean(CONFIG_ROTATE_KEY, CONFIG_ROTATE);
            themeIndex = mPrefs.getInt(CONFIG_THEME_KEY, CONFIG_THEME_WHITE);
            pageTransition = mPrefs.getInt(CONFIG_PAGE_ANIMATION_KEY, CONFIG_PAGE_NONE);

            Intent intent;
            intent = new Intent(this, BookViewActivity.class);

            intent.putExtra("BOOKCODE", item.bookCode);
            intent.putExtra("TITLE", item.title);
            intent.putExtra("AUTHOR", "");
            intent.putExtra("BOOKNAME", item.filePath);
            intent.putExtra("POSITION", 0.0);
            intent.putExtra("THEMEINDEX", themeIndex);
            intent.putExtra("DOUBLEPAGED", isDoublePage);
            intent.putExtra("transitionType", pageTransition);
            intent.putExtra("GLOBALPAGINATION", app.setting.globalPagination);
            intent.putExtra("ROTATION", isRotate);
            Log.d(TAG, "BOOKNAME = " + item.bookCode + " - " + app.setting.theme + " - " + app.setting.doublePaged + " - "
                    + app.setting.transitionType + " - " + app.setting.globalPagination + " - ");

            Log.d(TAG, "BOOKNAME = " + item.filePath);

            intent.putExtra("RTL", false);
            intent.putExtra("VERTICALWRITING", false);

            startActivity(intent);
        } catch (Exception e) {
            Log.d(TAG, "ERROR: " + e.getMessage());
        }
    }


    @Override
    public void viewAllPurchaseList() {
        viewPager.setCurrentItem(2);
    }

    @Override
    public void onClickPositive() {
        Intent myIntent = new Intent(this, LoginActivity.class);
        startActivity(myIntent);
        finish();
    }

    @Override
    public void onClickNegative() {

    }
}
