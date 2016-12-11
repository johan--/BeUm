package net.cosmiclion.opms.main.config;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.preference.PreferenceViewHolder;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import net.cosmiclion.beum.R;

/**
 * Created by longpham on 11/16/2016.
 */

public class SwitchPreferenceCompat extends android.support.v7.preference.SwitchPreferenceCompat {

    private final String TAG = getClass().getSimpleName();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SwitchPreferenceCompat(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public SwitchPreferenceCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SwitchPreferenceCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwitchPreferenceCompat(Context context) {
        super(context, null);
    }


    @Override
    public void onBindViewHolder(PreferenceViewHolder view) {
        super.onBindViewHolder(view);
//        view.itemView.setClickable(false);

        Resources resources = getContext().getResources();
        TextView title = (TextView) view.findViewById(android.R.id.title);
        int textSize = (int) resources.getDimension(R.dimen.config_title_textSize);
        int padding = (int) resources.getDimension(R.dimen.config_padding);
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        title.setPadding(padding, 0, 0, 0);

        TextView summary = (TextView) view.findViewById(android.R.id.summary);
        textSize = (int) resources.getDimension(R.dimen.config_summary_textSize);
        summary.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        summary.setPadding(padding, 0, 0, 0);

        View checkableView = view.findViewById(android.R.id.toggle);
        if (checkableView != null && checkableView instanceof Checkable) {
            if (checkableView instanceof Switch) {
                final Switch switchView = (Switch) checkableView;
                switchView.setOnCheckedChangeListener(null);
            } else if (checkableView instanceof SwitchCompat) {
                final SwitchCompat switchView = (SwitchCompat) checkableView;
                switchView.setOnCheckedChangeListener(null);
            }

            ((Checkable) checkableView).setChecked(isChecked());

            if (checkableView instanceof Switch) {
                final Switch switchView = (Switch) checkableView;
                switchView.setTextOn(getSwitchTextOn());
                switchView.setTextOff(getSwitchTextOff());
                switchView.setOnCheckedChangeListener(mListener2);
            } else if (checkableView instanceof SwitchCompat) {
                final SwitchCompat switchView = (SwitchCompat) checkableView;
                switchView.setTextOn(getSwitchTextOn());
                switchView.setTextOff(getSwitchTextOff());
                switchView.setOnCheckedChangeListener(mListener2);
            }
        }

        syncSummaryView(view);
    }

    /**
     * Sync a summary view contained within view's sub-hierarchy with the correct summary text.
     *
     * @param view View where a summary should be located
     */

    @Override
    protected void syncSummaryView(View view) {
//        Debug.i("Hello", "Hello doublePage");
        // Sync the summary view
        TextView summaryView = (TextView) view.findViewById(android.R.id.summary);
        if (summaryView != null) {
            boolean useDefaultSummary = true;
            if (isChecked() && !TextUtils.isEmpty(getSummaryOn())) {
                summaryView.setText(getSummaryOn());
                useDefaultSummary = false;
            } else if (!isChecked() && !TextUtils.isEmpty(getSummaryOff())) {
                summaryView.setText(getSummaryOff());
                useDefaultSummary = false;
            }

            if (useDefaultSummary) {
                final CharSequence summary = getSummary();
                if (summary != null) {
                    summaryView.setText(summary);
                    useDefaultSummary = false;
                }
            }

            int newVisibility = View.GONE;
            if (!useDefaultSummary) {
                // Someone has written to it
                newVisibility = View.VISIBLE;
            }
            if (newVisibility != summaryView.getVisibility()) {
                summaryView.setVisibility(newVisibility);
            }
        }
    }


    private final Listener mListener2 = new Listener();

    private class Listener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//            Debug.i(TAG, "onCheckedChanged buttonView "+ getKey());
            String buttonKey = getKey();
            if (isChecked) {
                switchListener.onSwitchChange(buttonKey, isChecked);
            } else {
                switchListener.onSwitchChange(buttonKey, isChecked);
            }
            if (!callChangeListener(isChecked)) {
                // Listener didn't like it, change it back.
                // CompoundButton will make sure we don't recurse.
                buttonView.setChecked(!isChecked);
                return;
            }
//            SwitchPreferenceCompat.this.setChecked(isChecked);
//            if (getOnPreferenceClickListener() != null) {
//                getOnPreferenceClickListener().onPreferenceClick(SwitchPreferenceCompat.this);
//            }
        }
    }

    public SwitchListener switchListener;

    public void setOnSwitchListener(SwitchListener listener) {
        switchListener = listener;
    }

    public interface SwitchListener {
        void onSwitchChange(String key, boolean isChecked);
    }
//    @Override
//    protected void onClick() {
//        Debug.i(TAG, "Hello onClick");
//        //super.onClick(); THIS IS THE IMPORTANT PART!
//    }
}