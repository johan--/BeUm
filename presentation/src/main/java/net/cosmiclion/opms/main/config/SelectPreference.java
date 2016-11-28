package net.cosmiclion.opms.main.config;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import net.cosmiclion.beum.R;

/**
 * Created by longpham on 10/28/2016.
 */
public class SelectPreference extends Preference {
    private Context mContext;
    // This is the constructor called by the inflater
    public SelectPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setWidgetLayoutResource(R.layout.config_select_pref);
    }

    public SelectPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
//        View button = holder.findViewById(R.id.theme_dark);
//        button.setClickable(true); // enable custom view click
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // persist your value here
//            }
//        });
        // the rest of the click binding
    }

}
