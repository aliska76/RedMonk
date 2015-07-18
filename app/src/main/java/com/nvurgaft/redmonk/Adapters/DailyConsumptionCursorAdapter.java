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
 * Created by Koby on 18-Jul-15.
 */
public class DailyConsumptionCursorAdapter extends CursorAdapter {

    LayoutInflater layoutInflater;

    public DailyConsumptionCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return layoutInflater.inflate(R.layout.daily_consumption_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView calorieAndTimeTextView = (TextView) view.findViewById(R.id.dateAndTotalCaloriesTextView);
        TextView calorieDescriptionTextView = (TextView) view.findViewById(R.id.calorieDescriptionTextView);

        String date = cursor.getString(1);
        int calories = cursor.getInt(2);
        int carbohydrates = cursor.getInt(3);
        int proteins = cursor.getInt(4);
        int fats = cursor.getInt(5);

        calorieAndTimeTextView.setText(date + " - Calories : " + calories);
        calorieDescriptionTextView.setText("Carbohydrates : " + carbohydrates + " ,Proteins : " + proteins + " and Fats : " + fats);
    }

    @Override
    protected void onContentChanged() {
        super.onContentChanged();
    }
}
