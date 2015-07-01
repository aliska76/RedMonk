package com.nvurgaft.redmonk.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nvurgaft.redmonk.Entities.Contact;
import com.nvurgaft.redmonk.Logic.Validators;
import com.nvurgaft.redmonk.R;

/**
 * Created by Koby on 29-Jun-15.
 */
public class EditContactDialog extends DialogFragment {

    protected NoticeDialogListener mListener;

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, Contact contact);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.contacts_dialog_fragment_view, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.contacts_dialog_title)
                .setView(dialogView)
                .setPositiveButton(R.string.contacts_dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String dialogContact = ((EditText) dialogView.findViewById(R.id.dialogContactEditText)).getText().toString();
                        String dialogContactRole = ((EditText) dialogView.findViewById(R.id.dialogContactRoleEditText)).getText().toString();
                        String dialogFirstNumber = ((EditText) dialogView.findViewById(R.id.dialogFirstNumberEditText)).getText().toString();
                        String dialogSecondNumber = ((EditText) dialogView.findViewById(R.id.dialogSecondNumberEditText)).getText().toString();
                        String dialogThirdNumber = ((EditText) dialogView.findViewById(R.id.dialogThirdNumberEditText)).getText().toString();


                        if (!Validators.validateName(dialogContact)) {
                            Toast.makeText(getActivity(), "Invalid name", Toast.LENGTH_SHORT).show();
                        } else if (!Validators.validatePhoneNumber(dialogContactRole)) {
                            Toast.makeText(getActivity(), "Invalid number", Toast.LENGTH_SHORT).show();
                        } else if (!Validators.validatePhoneNumber(dialogFirstNumber)) {
                            Toast.makeText(getActivity(), "Invalid first number", Toast.LENGTH_SHORT).show();
                        } else if (!Validators.validatePhoneNumber(dialogSecondNumber) && !dialogSecondNumber.isEmpty()) {
                            Toast.makeText(getActivity(), "Invalid second number", Toast.LENGTH_SHORT).show();
                        } else if (!Validators.validatePhoneNumber(dialogThirdNumber) && !dialogThirdNumber.isEmpty()) {
                            Toast.makeText(getActivity(), "Invalid third number", Toast.LENGTH_SHORT).show();
                        } else {
                            Contact contact = new Contact();
                            contact.setContactName(dialogContact);
                            contact.setContactRole(dialogContactRole);
                            contact.setFirstNumber(dialogFirstNumber);
                            contact.setSecondNumber(dialogSecondNumber);
                            contact.setThirdNumber(dialogThirdNumber);

                            mListener.onDialogPositiveClick(EditContactDialog.this, contact);
                        }

                    }
                })
                .setNegativeButton(R.string.contacts_dialog_negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogNegativeClick(EditContactDialog.this);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

}
