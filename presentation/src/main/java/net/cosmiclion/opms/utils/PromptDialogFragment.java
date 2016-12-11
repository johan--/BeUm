package net.cosmiclion.opms.utils;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import net.cosmiclion.beum.R;

public class PromptDialogFragment extends DialogFragment {
    private final String TAG = getClass().getSimpleName();
    private static final String BUNDLE_KEY_DIALOG_TITLE = "DIALOG_TITLE";
    private static final String BUNDLE_KEY_DIALOG_SUBTITLE = "DIALOG_SUBTITLE";
    private static final String BUNDLE_KEY_DIALOG_HINT = "DIALOG_INPUT_HINT";
    private TextView tvTitle;
    private TextView tvSubTitle;
    private Button btnPositive;
    private Button btnNegative;

    private String dialogTitle;
    private String dialogSubTitle;
    private String dialogHint;

    public PromptDialogFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.prompt_dialog_frag, container, false);
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

        tvTitle.setText((dialogTitle == null ? "" : dialogTitle));
        tvSubTitle.setText(dialogSubTitle == null ? "" : dialogSubTitle);

        // Show soft keyboard automatically and request focus to field
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
                    mListener.onClickNegative();
                    break;
                case R.id.btnOk:
                    dismiss();
                    mListener.onClickPositive();
                    break;
                default:
                    break;
            }
        }
    };

    public interface DialogListener {
        void onClickPositive();

        void onClickNegative();
    }

    public DialogListener mListener;

    public void setDialogClickListener(DialogListener listener) {
        this.mListener = listener;
    }

    public String getDialogTitle() {
        return dialogTitle;
    }

    public void setDialogTitle(String dialogTitle) {
        this.dialogTitle = dialogTitle;
    }

    public String getDialogSubTitle() {
        return dialogSubTitle;
    }

    public void setDialogSubTitle(String dialogSubTitle) {
        this.dialogSubTitle = dialogSubTitle;
    }

    public String getDialogHint() {
        return dialogHint;
    }

    public void setDialogHint(String dialogHint) {
        this.dialogHint = dialogHint;
    }
}