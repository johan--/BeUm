package net.cosmiclion.opms.ebookreader;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.skytree.epub.PageTransition;
import com.skytree.epub.Parallel;
import com.skytree.epub.ReflowableControl;
import com.skytree.epub.SkyProvider;

import net.cosmiclion.beum.R;
import net.cosmiclion.opms.ebookreader.skyepub.SkySetting;
import net.cosmiclion.opms.ebookreader.skyepub.SkyUtility;

public class ViewerActivity extends Activity {
    ReflowableControl rv;

    boolean isControlsShown = true;
    double pagePositionInBook = -1;
    int bookCode;
    String fileName;
    Parallel currentParallel;
    boolean autoStartPlayingWhenNewPagesLoaded = false;
    boolean autoMoveChapterWhenParallesFinished = true;
    boolean isAutoPlaying = true;
    boolean isPageTurnedByMediaOverlay = true;

    boolean isDoublePagedForLandscape;
    boolean isGlobalPagination;

    boolean isRTL = false;
    boolean isVerticalWriting = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);

        makeFullScreen();
        if (Build.VERSION.SDK_INT >= 11 ){
            rv = new ReflowableControl(this);                        // in case that device supports transparent webkit, the background image under the content can be shown. in some devices, content may be overlapped.
        } else {
            rv = new ReflowableControl(this, Color.WHITE);            // in case that device can not support transparent webkit, the background color will be set in one color.
        }
    }

    public void makeFullScreen() {
//		if (SkyUtility.isNexus() && isFullScreenForNexus) {
        SkyUtility.makeFullscreen(this);
//		}
    }

    private void setRvLayout(Bundle bundle){
        // if false highlight will be drawed on the back of text - this is default.
        // for the very old devices of which GPU does not support transparent webView background, set the value to true.
        rv.setDrawingHighlightOnFront(false);

        // set the bookCode to identify the book file.
        rv.bookCode = this.bookCode;

        // set bitmaps for engine.
//        rv.setPagesStackImage(this.getBitmap("PagesStack.png"));
//        rv.setPagesCenterImage(this.getBitmap("PagesCenter.png"));
        // for epub3 which has page-progression-direction="rtl", rv.isRTL() will return true.
        // for old RTL epub which does not have <spine toc="ncx" page-progression-direction="rtl"> in opf file.
        // you can enforce RTL mode.


/*
        // delay times for proper operations.
		// !! DO NOT SET these values if there's no issue on your epub reader. !!
		// !! if delayTime is decresed, performance will be increase
		// !! if delayTime is set to too low value, a lot of problem can be occurred.
		// bringDelayTime(default 500 ms) is for curlView and mainView transition - if the value is too short, blink may happen.
		rv.setBringDelayTime(500);
		// reloadDelayTime(default 100) is used for delay before reload (eg. changeFont, loadChapter or etc)
		rv.setReloadDelayTime(100);
		// reloadDelayTimeForRotation(default 1000) is used for delay before rotation
		rv.setReloadDelayTimeForRotation(1000);
		// retotaionDelayTime(default 1500) is used for delay after rotation.
		rv.setRotationDelayTime(1500);
		// finalDelayTime(default 500) is used for the delay after loading chapter.
		rv.setFinalDelayTime(500);
		// rotationFactor affects the delayTime before Rotation. default value 1.0f
		rv.setRotationFactor(1.0f);
		// If recalcDelayTime is too short, setContentBackground function failed to work properly.
		rv.setRecalcDelayTime(2500);
*/

        // set the max width or height for background.
        rv.setMaxSizeForBackground(1024);
        rv.setBaseDirectory(SkySetting.getStorageDirectory());
        rv.setBookName(fileName);
//        Log.d(TAG, "fileName = " + fileName);
//        Log.d(TAG, "BaseDirectory = " + SkySetting.getStorageDirectory());
        // set the file path of epub to open
        // Be sure that the file exists before setting.
//		rv.setBookPath(SkySetting.getStorageDirectory() + "/books/"+fileName);
        // if true, double pages will be displayed on landscape mode.
//        rv.setDoublePagedForLandscape(this.isDoublePagedForLandscape);
//        // set the initial font style for book.
//        rv.setFont(setting.fontName, this.getRealFontSize(setting.fontSize));
//        // set the initial line space for book.
//        rv.setLineSpacing(this.getRealLineSpace(setting.lineSpacing)); // the value is supposed to be percent(%).
//        // set the horizontal gap(margin) on both left and right side of each page.
//        rv.setHorizontalGapRatio(0.30);
//        // set the vertical gap(margin) on both top and bottom side of each page.
//        rv.setVerticalGapRatio(0.22);
//        // set the HighlightListener to handle text highlighting.
//        rv.setHighlightListener(new BookViewActivity.HighlightDelegate());
//        // set the PageMovedListener which is called whenever page is moved.
//        rv.setPageMovedListener(new BookViewActivity.PageMovedDelegate());
//        // set the SelectionListener to handle text selection.
//        rv.setSelectionListener(new BookViewActivity.SelectionDelegate());
//        // set the pagingListener which is called when GlobalPagination is true. this enables the calculation for the total number of pages in book, not in chapter.
//        rv.setPagingListener(new BookViewActivity.PagingDelegate());
//        // set the searchListener to search keyword.
//        rv.setSearchListener(new BookViewActivity.SearchDelegate());
//        // set the stateListener to monitor the state of sdk engine.
//        rv.setStateListener(new BookViewActivity.StateDelegate());
//        // set the clickListener which is called when user clicks
//        rv.setClickListener(new BookViewActivity.ClickDelegate());
//        // set the bookmarkListener to toggle bookmark
//        rv.setBookmarkListener(new BookViewActivity.BookmarkDelegate());
//        // set the scriptListener to set custom javascript.
//        rv.setScriptListener(new BookViewActivity.ScriptDelegate());

        // enable/disable scroll mode
        rv.setScrollMode(false);

        // for some anroid device, when rendering issues are occurred, use "useSoftwareLayer"
//		rv.useSoftwareLayer();
        // In search keyword, if true, sdk will return search result with the full information such as position, pageIndex.
        rv.setFullSearch(true);
        // if true, sdk will return raw text for search result, highlight text or body text without character escaping.
        rv.setRawTextRequired(false);

        // if true, sdk will read the content of book directry from file system, not via Internal server.
//		rv.setDirectRead(true);

        // If you want to make your own provider, please look into EpubProvider.java in Advanced demo.
//		EpubProvider epubProvider = new EpubProvider();
//		rv.setContentProvider(epubProvider);

        // SkyProvider is the default ContentProvider which is presented with SDK.
        // SkyProvider can read the content of epub file without unzipping.
        // SkyProvider is also fully integrated with SkyDRM solution.
        SkyProvider skyProvider = new SkyProvider();
//        skyProvider.setKeyListener(new BookViewActivity.KeyDelegate());
        rv.setContentProvider(skyProvider);

        // set the start positon to open the book.
        rv.setStartPositionInBook(pagePositionInBook);
        // DO NOT USE BELOW, if true , sdk will use DOM to highlight text.
//		rv.useDOMForHighlight(false);
        // if true, globalPagination will be activated.
        // this enables the calculation of page number based on entire book ,not on each chapter.
        // this globalPagination consumes huge computing power.
        // AVOID GLOBAL PAGINATION FOR LOW SPEC DEVICES.
        rv.setGlobalPagination(this.isGlobalPagination);
        // set the navigation area on both left and right side to go to the previous or next page when the area is clicked.
        rv.setNavigationAreaWidthRatio(0.1f); // both left and right side.
        // set the device locked to prevent Rotation.
        rv.setRotationLocked(true);
//        isRotationLocked = setting.lockRotation;
        // set the mediaOverlayListener for MediaOverlay.
//        rv.setMediaOverlayListener(new BookViewActivity.MediaOverlayDelegate());
        // set the audio playing based on Sequence.
        rv.setSequenceBasedForMediaOverlay(false);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT); // width,height

        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        params.height = RelativeLayout.LayoutParams.MATCH_PARENT;

        rv.setLayoutParams(params);
        Theme themeIndex=   new Theme("white", Color.BLACK, 0xffffffff, Color.argb(240, 94, 61, 35), Color.LTGRAY, Color.argb(240, 208, 208, 208), Color.argb(120, 251, 192, 45), Color.DKGRAY, 0x22222222, "Phone-Portrait-White.png", "Phone-Landscape-White.png", "Phone-Landscape-Double-White.png", R.drawable.ic_viewer_bookmark);

//        this.applyThemeToRV(themeIndex);

//        if (this.isFullScreenForNexus && SkyUtility.isNexus() && Build.VERSION.SDK_INT >= 19) {
//            rv.setImmersiveMode(true);
//        }
        // If you want to get the license key for commercial use, please email us (skytree21@gmail.com).
        // Without the license key, watermark message will be shown in background.
        rv.setLicenseKey("a99b-3914-a63b-8ecb");

        // set PageTransition Effect
        int transitionType = bundle.getInt("transitionType");
        if (transitionType == 0) {
            rv.setPageTransition(PageTransition.None);
        } else if (transitionType == 1) {
            rv.setPageTransition(PageTransition.Slide);
        } else if (transitionType == 2) {
            rv.setPageTransition(PageTransition.Curl);
        }

        // setCurlQuality effects the image quality when tuning page in Curl Transition Mode.
        // If "Out of Memory" occurs in high resolution devices with big screen,
        // this value should be decreased like 0.25f or below.
//        if (this.getMaxSize() > 1280) {
//            rv.setCurlQuality(0.5f);
//        }

//        // set the color of text selector.
//        rv.setSelectorColor(getCurrentTheme().selectorColor);
//        // set the color of text selection area.
//        rv.setSelectionColor(getCurrentTheme().selectionColor);

        // setCustomDrawHighlight & setCustomDrawCaret work only if SDK >= 11
        // if true, sdk will ask you how to draw the highlighted text
        rv.setCustomDrawHighlight(true);
        // if true, sdk will require you to draw the custom selector.
        rv.setCustomDrawCaret(true);

        rv.setFontUnit("px");

        rv.setFingerTractionForSlide(true);
//        rv.setVideoListener(new BookViewActivity.VideoDelegate());

        // make engine not to send any event to iframe
        // if iframe clicked, onIFrameClicked will be fired with source of iframe
        // By Using that source of iframe, you can load the content of iframe in your own webView or another browser.
        rv.setSendingEventsToIFrameEnabled(false);

        // make engine send any event to video(tag) or not
        // if video tag is clicked, onVideoClicked will be fired with source of iframe
        // By Using that source of video, you can load the content of video in your own media controller or another browser.
        rv.setSendingEventsToVideoEnabled(true);

        // make engine send any event to video(tag) or not
        // if video tag is clicked, onVideoClicked will be fired with source of iframe
        // By Using that source of video, you can load the content of video in your own media controller or another browser.
        rv.setSendingEventsToAudioEnabled(true);

        // if true, sdk will return the character offset from the chapter beginning , not from element index.
        // then startIndex, endIndex of highlight will be 0 (zero)
        rv.setGlobalOffset(true);
        // if true, sdk will return the text of each page in the PageInformation object which is passed in onPageMoved event.
        rv.setExtractText(true);

    }

    class Theme {
        public String name;
        public int foregroundColor;
        public int backgroundColor;
        public int controlColor;
        public int controlHighlightColor;
        public String portraitName = "";
        public String landscapeName = "";
        public String doublePagedName = "";
        public int seekBarColor;
        public int seekThumbColor;
        public int selectorColor;
        public int selectionColor;
        public int bookmarkId;

        Theme(String name, int foregroundColor, int backgroundColor, int controlColor, int controlHighlightColor, int seekBarColor, int seekThumbColor, int selectorColor, int selectionColor, String portraitName, String landscapeName, String doublePagedName, int bookmarkId) {
            this.name = name;
            this.foregroundColor = foregroundColor;
            this.backgroundColor = backgroundColor;
            this.portraitName = portraitName;
            this.landscapeName = landscapeName;
            this.doublePagedName = doublePagedName;
            this.controlColor = controlColor;
            this.controlHighlightColor = controlHighlightColor;
            this.seekBarColor = seekBarColor;
            this.seekThumbColor = seekThumbColor;
            this.selectorColor = selectorColor;
            this.selectionColor = selectionColor;
            this.bookmarkId = bookmarkId;
        }
    }
}
