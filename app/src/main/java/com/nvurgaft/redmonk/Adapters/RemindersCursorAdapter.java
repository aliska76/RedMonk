package com.nvurgaft.redmonk.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.nvurgaft.redmonk.R;

/**
 * Created by Koby on 02-Jul-15.
 */
public class RemindersCursorAdapter extends CursorAdapter {

    LayoutInflater inflater;

    public RemindersCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.reminder_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView reminderTimeTextView = (TextView) view.findViewById(R.id.reminderTimeTextView);
        TextView reminderTodoTextView = (TextView) view.findViewById(R.id.reminderTodoTextView);
        CheckBox resolvedCheckBox = (CheckBox) view.findViewById(R.id.resolveCheckBox);
        resolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.setChecked(isChecked);
                if (isChecked) {
                    buttonView.setText(buttonView.getContext().getText(R.string.resolved));
                } else {
                    buttonView.setText(buttonView.getContext().getText(R.string.unresolved));
                }
            }
        });

        String time_hour = cursor.getString(2);
        String time_minute = cursor.getString(3);
        String todo = cursor.getString(4);
        String resolvedString = cursor.getString(5);
        boolean resolved = Boolean.valueOf(resolvedString);
        resolvedCheckBox.setChecked(resolved);
        if (resolved) {
            resolvedCheckBox.setText(context.getText(R.string.resolved));
        } else {
            resolvedCheckBox.setText(context.getText(R.string.unresolved));
        }

        int hour = Integer.valueOf(time_hour);
        int minute = Integer.valueOf(time_minute);

        StringBuilder sb = new StringBuilder();
        sb.append(time_hour).append(":");
        if (String.valueOf(time_minute).length() == 1) {
            sb.append(0);
        }
        sb.append(time_minute);
        if (Integer.valueOf(time_hour) > 12) {
            sb.append(" (").append(hour - 12).append(":");
            sb.append(String.valueOf(time_minute).length() == 1 ? 0 : "").append(minute);
            sb.append(" PM)");
        } else {
            sb.append(" (").append(hour).append(":");
            sb.append(String.valueOf(time_minute).length() == 1 ? 0 : "").append(minute);
            sb.append(" AM)");
        }

        reminderTimeTextView.setText(sb.toString());
        reminderTodoTextView.setText(todo);
    }

    @Override
    protected void onContentChanged() {
        super.onContentChanged();
    }
}
