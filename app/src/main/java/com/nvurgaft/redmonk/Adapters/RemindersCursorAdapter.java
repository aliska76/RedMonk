package com.nvurgaft.redmonk.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView reminderTimeTextView = (TextView) view.findViewById(R.id.reminderTimeTextView);
        TextView reminderTodoTextView = (TextView) view.findViewById(R.id.reminderTodoTextView);
        String time = cursor.getString(1);
        String todo = cursor.getString(2);
        reminderTimeTextView.setText(time);
        reminderTodoTextView.setText(todo);
    }

    @Override
    protected void onContentChanged() {
        super.onContentChanged();
    }
}
