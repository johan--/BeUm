package net.cosmiclion.opms.main.library.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import net.cosmiclion.beum.R;
import net.cosmiclion.opms.main.library.dialog.model.DialogParameter;
import net.cosmiclion.opms.utils.Debug;


public class SortDialogFragment extends DialogFragment {
    private final String TAG = getClass().getSimpleName();
    private static final String BUNDLE_KEY_DIALOG_TITLE = "DIALOG_TITLE";
    private static final String BUNDLE_KEY_DIALOG_SUBTITLE = "DIALOG_SUBTITLE";
    private TextView tvTitle;
    private TextView tvSubTitle;
    private Button btnPositive;
    private Button btnNegative;

    public SortDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static SortDialogFragment newInstance(@NonNull DialogParameter parameter) {
        SortDialogFragment frag = new SortDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_DIALOG_TITLE, parameter.getTitle());
        bundle.putString(BUNDLE_KEY_DIALOG_SUBTITLE, parameter.getSubTitle());
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sort_dialog_frag, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvSubTitle = (TextView) view.findViewById(R.id.tvSubTitle);
        btnPositive = (Button) view.findViewById(R.id.btnOk);
        btnPositive.setOnClickListener(dialogListener);
        btnNegative = (Button) view.findViewById(R.id.btnCancel);
        btnNegative.setOnClickListener(dialogListener);

        Bundle bundle = getArguments();
        String title = bundle.getString(BUNDLE_KEY_DIALOG_TITLE, getResources().getString(R.string.sample_text_title));
        tvTitle.setText(title);
        String subTitle = bundle.getString(BUNDLE_KEY_DIALOG_SUBTITLE, getResources().getString(R.string.sample_text_subtitle));
        tvSubTitle.setText(subTitle);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    private View.OnClickListener dialogListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnCancel:
                    Debug.i(TAG, "cancel");
                    dismiss();
                    break;
                case R.id.btnOk:
                    Debug.i(TAG, "ok " );
                    dismiss();
                    break;
                default:
                    break;
            }
        }
    };

}