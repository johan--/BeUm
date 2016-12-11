package net.cosmiclion.opms.main.config;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import net.cosmiclion.beum.R;
import net.cosmiclion.opms.utils.Debug;

/**
 * Created by longpham on 10/28/2016.
 */
public class PageSlidePreference extends Preference {
    private final String TAG = getClass().getSimpleName();

    private Context mContext;

    // This is the constructor called by the inflater
    public PageSlidePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setWidgetLayoutResource(R.layout.config_select_pref);
    }

    public PageSlidePreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);

        Resources resources = getContext().getResources();
        TextView title = (TextView) holder.findViewById(android.R.id.title);
        int textSize = (int) resources.getDimension(R.dimen.config_title_textSize);
        int padding = (int) resources.getDimension(R.dimen.config_padding);
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        title.setPadding(padding, 0, 0, 0);

        TextView summary = (TextView) holder.findViewById(android.R.id.summary);
        textSize = (int) resources.getDimension(R.dimen.config_summary_textSize);
        summary.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        summary.setPadding(padding, 0, 0, 0);

        holder.itemView.setClickable(false); // disable parent click

        btnNone = (RadioButton) holder.findViewById(R.id.pageEffectNone);
        btnNone.setClickable(true); // enable custom view click
        btnNone.setOnClickListener(onClickListener);

        btnSlide = (RadioButton) holder.findViewById(R.id.pageEffectSlide);
        btnSlide.setClickable(true); // enable custom view click
        btnSlide.setOnClickListener(onClickListener);

        btnFlipping = (RadioButton) holder.findViewById(R.id.pageEffectFlipping);
        btnFlipping.setClickable(true); // enable custom view click
        btnFlipping.setOnClickListener(onClickListener);

        setPageSlide(pageAnim);
    }

    private int pageAnim;

    private RadioButton btnNone, btnSlide, btnFlipping;

    private void setPageSlide(int type) {
        switch (type) {
            case ConfigFragment.CONFIG_PAGE_NONE:
                Debug.i(TAG, "pageEffectNone");
                btnNone.setChecked(true);
                break;

            case ConfigFragment.CONFIG_PAGE_SLIDE:
                Debug.i(TAG, "pageEffectSlide");
                btnSlide.setChecked(true);
                break;

            case ConfigFragment.CONFIG_PAGE_FLIPPING:
                Debug.i(TAG, "pageEffectFlipping");
                btnFlipping.setChecked(true);
                break;

            default:
                Debug.i(TAG, "pageEffectNone Default");
                btnNone.setChecked(true);
                break;
        }
    }

    public void getPageSlideSelected(int type) {
        Debug.i(TAG, "pageEffectNone type=" + type);
        pageAnim = type;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.pageEffectNone:
                    Debug.i(TAG, "pageEffectNone");
                    mPageListener.onPageSlideChange(ConfigFragment.CONFIG_PAGE_NONE);
                    break;

                case R.id.pageEffectSlide:
                    Debug.i(TAG, "pageEffectSlide");
                    mPageListener.onPageSlideChange(ConfigFragment.CONFIG_PAGE_SLIDE);
                    break;

                case R.id.pageEffectFlipping:
                    Debug.i(TAG, "pageEffectFlipping");
                    mPageListener.onPageSlideChange(ConfigFragment.CONFIG_PAGE_FLIPPING);
                    break;

                default:
                    Debug.i(TAG, "pageEffectNone Default");
                    mPageListener.onPageSlideChange(ConfigFragment.CONFIG_PAGE_NONE);
                    break;
            }
        }
    };

    protected interface PageSlideListener {
        void onPageSlideChange(int slide);
    }

    private PageSlideListener mPageListener;

    public void setOnPageSlideChangeListener(PageSlideListener pageListener) {
        mPageListener = pageListener;
    }

}
