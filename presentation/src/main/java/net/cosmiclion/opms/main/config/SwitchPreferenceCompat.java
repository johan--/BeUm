package net.cosmiclion.opms.main.config;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import net.cosmiclion.beum.R;

/**
 * Created by longpham on 11/16/2016.
 */

public class SwitchPreferenceCompat extends CheckBoxPreference {

    public SwitchPreferenceCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SwitchPreferenceCompat(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public SwitchPreferenceCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwitchPreferenceCompat(Context context) {
        super(context);
        init();
    }

    private void init() {
        setWidgetLayoutResource(R.layout.config_switch);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        holder.itemView.setClickable(false);

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
    }
}