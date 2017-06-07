package com.olicor.miclima;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;



public class AlertDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        Context contexto = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(contexto)
                .setTitle(R.string.error_title)
                .setMessage(R.string.error_message)
                .setPositiveButton(R.string.error_ok_button_text, null);
        AlertDialog dialogo = builder.create();
        return dialogo;
    }
}
