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
 * Created by Koby on 28-Jun-15.
 */
public class ContactsAdapter extends CursorAdapter {

    public ContactsAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.contact_item, parent);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView contactNameTextView = (TextView) view.findViewById(R.id.contact_item_name);
        String contactName = cursor.getString(0);
        contactNameTextView.setText(contactName);
    }
}
