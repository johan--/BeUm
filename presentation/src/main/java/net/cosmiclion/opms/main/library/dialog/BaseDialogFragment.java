package net.cosmiclion.opms.main.library.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.cosmiclion.beum.R;
import net.cosmiclion.opms.main.library.dialog.model.DialogParameter;
import net.cosmiclion.opms.utils.Debug;


public class BaseDialogFragment extends DialogFragment {
    private final String TAG = getClass().getSimpleName();
    private static final String BUNDLE_KEY_DIALOG_TITLE = "DIALOG_TITLE";
    private static final String BUNDLE_KEY_DIALOG_SUBTITLE = "DIALOG_SUBTITLE";
    private static final String BUNDLE_KEY_DIALOG_HINT = "DIALOG_INPUT_HINT";
    private EditText mEditText;
    private TextView tvTitle;
    private TextView tvSubTitle;
    private Button btnPositive;
    private Button btnNegative;

    public BaseDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static BaseDialogFragment newInstance(@NonNull DialogParameter parameter) {
        BaseDialogFragment frag = new BaseDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_DIALOG_TITLE, parameter.getTitle());
        bundle.putString(BUNDLE_KEY_DIALOG_SUBTITLE, parameter.getSubTitle());
        bundle.putString(BUNDLE_KEY_DIALOG_HINT, parameter.getHint());
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.base_dialog_frag, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvSubTitle = (TextView) view.findViewById(R.id.tvSubTitle);
        mEditText = (EditText) view.findViewById(R.id.etText);
        btnPositive = (Button) view.findViewById(R.id.btnOk);
        btnPositive.setOnClickListener(dialogListener);
        btnNegative = (Button) view.findViewById(R.id.btnCancel);
        btnNegative.setOnClickListener(dialogListener);
        Bundle bundle = getArguments();
        String title = bundle.getString(BUNDLE_KEY_DIALOG_TITLE, getResources().getString(R.string.sample_text_title));
        tvTitle.setText(title);
        String subTitle = bundle.getString(BUNDLE_KEY_DIALOG_SUBTITLE, getResources().getString(R.string.sample_text_subtitle));
        tvSubTitle.setText(subTitle);
//        String hint = bundle.getString(BUNDLE_KEY_DIALOG_HINT, getResources().getString(R.string.text_input_hint));
//        mEditText.setHint(hint);
        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
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
                    Debug.i(TAG, "ok " + getTextInput());
                    dismiss();
                    break;
                default:
                    break;
            }
        }
    };

    public String getTextInput() {
        String textInput = mEditText.getText().toString();
        return textInput == null ? "" : textInput;
    }
}