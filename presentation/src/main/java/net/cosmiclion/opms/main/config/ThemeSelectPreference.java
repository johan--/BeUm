package net.cosmiclion.opms.main.config;

import android.content.Context;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;

import net.cosmiclion.beum.R;

/**
 * Created by longpham on 10/28/2016.
 */
public class ThemeSelectPreference extends Preference {

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
