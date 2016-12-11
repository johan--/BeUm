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
public class ThemeSelectPreference extends Preference {

    private final String TAG = getClass().getSimpleName();

    // This is the constructor called by the inflater
    public ThemeSelectPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWidgetLayoutResource(R.layout.config_theme_select_pref);
    }

    public ThemeSelectPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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

        btnThemeWhite =(RadioButton)  holder.findViewById(R.id.radioThemeWhite);
        btnThemeWhite.setClickable(true); // enable custom view click
        btnThemeWhite.setOnClickListener(onClickListener);

        btnThemeBrown =(RadioButton)  holder.findViewById(R.id.radioThemeBrown);
        btnThemeBrown.setClickable(true); // enable custom view click
        btnThemeBrown.setOnClickListener(onClickListener);

        btnThemeBlack = (RadioButton) holder.findViewById(R.id.radioThemeBlack);
        btnThemeBlack.setClickable(true); // enable custom view click
        btnThemeBlack.setOnClickListener(onClickListener);

        setSelectedTheme(selectedTheme);
    }

    private int selectedTheme;

    private RadioButton btnThemeWhite, btnThemeBrown, btnThemeBlack;

    private void setSelectedTheme(int type) {
        switch (type) {
            case ConfigFragment.CONFIG_THEME_WHITE:
                Debug.i(TAG, "btnThemeWhite");
                btnThemeWhite.setChecked(true);
                break;

            case ConfigFragment.CONFIG_THEME_BROWN:
                Debug.i(TAG, "btnThemeBrown");
                btnThemeBrown.setChecked(true);
                break;

            case ConfigFragment.CONFIG_THEME_BLACK:
                Debug.i(TAG, "btnThemeBlack");
                btnThemeBlack.setChecked(true);
                break;

            default:
                Debug.i(TAG, "btnThemeWhite Default");
                btnThemeWhite.setChecked(true);
                break;
        }
    }

    public void getSelectedTheme(int type) {
        Debug.i(TAG, "selectedTheme type=" + type);
        selectedTheme = type;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.radioThemeWhite:
                    Debug.i(TAG, "Theme white");
                    mThemeListener.onThemeChange(ConfigFragment.CONFIG_THEME_WHITE);
                    break;

                case R.id.radioThemeBrown:
                    Debug.i(TAG, "Theme brown");
                    mThemeListener.onThemeChange(ConfigFragment.CONFIG_THEME_BROWN);
                    break;

                case R.id.radioThemeBlack:
                    Debug.i(TAG, "Theme black");
                    mThemeListener.onThemeChange(ConfigFragment.CONFIG_THEME_BLACK);
                    break;

                default:
                    Debug.i(TAG, "Theme Default white");
                    mThemeListener.onThemeChange(ConfigFragment.CONFIG_THEME_WHITE);
                    break;
            }
        }
    };

    protected interface ThemeListener {
        void onThemeChange(int theme);
    }

    private ThemeListener mThemeListener;

    public void setOnThemeChangeListener(ThemeListener themeListener) {
        mThemeListener = themeListener;
    }
}
