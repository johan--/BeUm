package net.cosmiclion.opms.ebookreader.skyepub;

import android.app.Application;

import com.skytree.epub.BookInformation;
import com.skytree.epub.SkyKeyManager;
import com.squareup.leakcanary.LeakCanary;

import net.cosmiclion.data.BuildConfig;
import net.cosmiclion.opms.utils.Debug;

import java.util.ArrayList;

public class SkyApplication extends Application {
    public String message = "We are the world.";
    public ArrayList<BookInformation> bis;
    public ArrayList<CustomFont> customFonts = new ArrayList<CustomFont>();
    public SkySetting setting;
    public SkyDatabase sd = null;
    public int sortType = 0;
    public SkyKeyManager keyManager;

    @Override
    public void onCreate() {
        super.onCreate();
        sd = new SkyDatabase(this);
        reloadBookInformations();
        loadSetting();
        createSkyDRM();
        this.initializeLeakDetection();
    }

    public void reloadBookInformations() {
        this.bis = sd.fetchBookInformations(sortType, "");
    }

    public void reloadBookInformations(String key) {
        this.bis = sd.fetchBookInformations(sortType, key);
    }

    public void loadSetting() {
        this.setting = sd.fetchSetting();
    }

    public void saveSetting() {
        sd.updateSetting(this.setting);
    }

    public void createSkyDRM() {
        this.keyManager = new SkyKeyManager("A3UBZzJNCoXmXQlBWD4xNo", "zfZl40AQXu8xHTGKMRwG69");
    }

    private void initializeLeakDetection() {
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);
            Debug.i("SkyApplication", "is Debug");
        } else {
            Debug.i("SkyApplication", "is not Debug");
        }
    }
}
