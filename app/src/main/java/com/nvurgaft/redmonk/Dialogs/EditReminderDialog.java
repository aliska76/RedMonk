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
import android.widget.TimePicker;
import android.widget.Toast;

import com.nvurgaft.redmonk.Entities.Reminder;
import com.nvurgaft.redmonk.R;

/**
 * Created by Koby on 17-Jul-15.
 */
public class EditReminderDialog extends DialogFragment {

    protected NoticeDialogListener mListener;

    protected TimePicker reminderTimePicker;
    protected EditText todoEditText;

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, Reminder reminder, boolean isEdit);

        public void onDialogNegativeClick(DialogFragment dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.reminders_dialog_fragment_view, null);

        reminderTimePicker = (TimePicker) dialogView.findViewById(R.id.reminderTimePicker);
        todoEditText = (EditText) dialogView.findViewById(R.id.todoEditText);

        boolean isEdit = false;
        long reminderId = 0L;
        int hour = 0;
        int minute = 0;
        String todo = "N/A";
        boolean resolved = false;

        Bundle passedBundle = getArguments();
        if (passedBundle != null) {
            isEdit = passedBundle.getBoolean("isEdit", false);
            reminderId = passedBundle.getInt("rid", 0);
            hour = passedBundle.getInt("hour", 0);
            minute = passedBundle.getInt("minute", 0);
            todo = passedBundle.getString("todo", "N/A");
            resolved = Boolean.valueOf(passedBundle.getString("resolved", "false"));
        }

        final boolean action = isEdit;
        reminderTimePicker.setCurrentHour(hour);
        reminderTimePicker.setCurrentMinute(minute);
        todoEditText.setText(todo);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (isEdit) {
            builder.setMessage(R.string.reminders_dialog_title_edit);
        } else {
            builder.setMessage(R.string.reminders_dialog_title_create);
        }
        builder.setView(dialogView)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Reminder reminder = new Reminder();

                        reminderTimePicker.clearFocus();
                        int cHour = reminderTimePicker.getCurrentHour();
                        int cMinute = reminderTimePicker.getCurrentMinute();
                        String todoText = todoEditText.getText().toString();
                        Toast.makeText(getActivity(), "Time is " + cHour + ":" + cMinute, Toast.LENGTH_SHORT).show();

                        reminder.setReminderId(System.currentTimeMillis()/1000L);
                        reminder.setHour(cHour);
                        reminder.setMinute(cMinute);
                        reminder.setTodo(todoText);
                        reminder.setResolved(String.valueOf(false));

                        // send the contact back to the activity to be registered
                        mListener.onDialogPositiveClick(EditReminderDialog.this, reminder, action);


                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogNegativeClick(EditReminderDialog.this);
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
