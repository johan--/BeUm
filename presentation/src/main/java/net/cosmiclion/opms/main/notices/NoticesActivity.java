package net.cosmiclion.opms.main.notices;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import net.cosmiclion.beum.R;
import net.cosmiclion.opms.login.LoginActivity;
import net.cosmiclion.opms.main.MainActivity;
import net.cosmiclion.opms.main.help.HelpFragment;
import net.cosmiclion.opms.utils.Debug;

public class NoticesActivity extends AppCompatActivity {
    public static final String FRAGMENT = "NoticesActivity";

    public String TAG = getClass().getSimpleName();

    private NavigationView mNavigationView;

    private Toolbar mToolBar;

    private DrawerLayout mDrawerLayout;

    private String fragmentName;

    public NoticesActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notices_act);
        setupToolBar();
//        setupDrawer();
        setupFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Debug.i(TAG, "onBackPressed 1");
                onBackPressed();
                return true;
            case R.id.menu_item_notify:
                Debug.i(TAG, "menu_item_notify");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem notify = menu.findItem(R.id.menu_item_notify);
        notify.setVisible(false);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //dont call **super**, if u want disable back button in current screen.
        //create a dialog to ask yes no question whether or not the user wants to exit
    }

    private void setupToolBar() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(true);
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                final FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    Debug.i(TAG, "onBackPressed =" + fm.getBackStackEntryCount());
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true); // show back button
                    mToolBar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
                    mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    });
                } else {
                    Debug.i(TAG, "onBackPressed =" + fm.getBackStackEntryCount());
                }
            }
        });
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
        navigationView.setCheckedItem(NavigationView.NO_ID);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        switch (menuItem.getItemId()) {
                            case R.id.nav_item_home:
                                Debug.i(TAG, "Home");
                                goHomeScreen();
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
                                Intent myIntent = new Intent(NoticesActivity.this, LoginActivity.class);
                                startActivity(myIntent);
                                break;
                            case R.id.nav_item_help:
                                Debug.i(TAG, "help");
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

    private void setupFragment() {
        fragmentName = getIntent().getStringExtra(MainActivity.SCREEN_TAG);
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        Fragment fragment = null;

//        Menu navMenu = mNavigationView.getMenu();
        if (fragmentName.equals(NoticesFragment.class.getSimpleName())) {
            fragment = NoticesFragment.newInstance();
//            navMenu.findItem(R.id.nav_item_home).setChecked(false);
        } else if (fragmentName.equals(HelpFragment.class.getSimpleName())) {
            fragment = HelpFragment.newInstance();
//            navMenu.findItem(R.id.nav_item_help).setChecked(true);
        }

        transaction.replace(R.id.contentFrame, fragment);
        transaction.commit();
    }

    private void goHomeScreen() {
        finish();
    }

    private void showHelpScreen() {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        Fragment fragment = HelpFragment.newInstance();
        transaction.replace(R.id.contentFrame, fragment);
        transaction.commit();
    }
}
