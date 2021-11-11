package com.jjl.dxz.platform.meeting.widget.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.jjl.dxz.platform.meeting.R;

public class ProgressDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage(getString(R.string.please_wait));
        pd.setCancelable(false);
        pd.setIndeterminate(true);
        setCancelable(false);
        return pd;
    }
}
