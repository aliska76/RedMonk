package com.nvurgaft.redmonk;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;


/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link RedMonkEmergencyWidgetConfigureActivity RedMonkEmergencyWidgetConfigureActivity}
 */
public class RedMonkEmergencyWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            RedMonkEmergencyWidgetConfigureActivity.deleteTitlePref(context, appWidgetIds[i]);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        String cName = RedMonkEmergencyWidgetConfigureActivity.loadTitlePref(context, appWidgetId, "_name");
        String cRole = RedMonkEmergencyWidgetConfigureActivity.loadTitlePref(context, appWidgetId, "_role");
        String cFirst = RedMonkEmergencyWidgetConfigureActivity.loadTitlePref(context, appWidgetId, "_first");
        String cSecond = RedMonkEmergencyWidgetConfigureActivity.loadTitlePref(context, appWidgetId, "_second");
        String cThird = RedMonkEmergencyWidgetConfigureActivity.loadTitlePref(context, appWidgetId, "_third");

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.red_monk_emergency_widget);
        views.setTextViewText(R.id.nameAndRoleTextView, cName + " - " + cRole);
        views.setTextViewText(R.id.firstContactTextView, cFirst);
        views.setTextViewText(R.id.secondContactTextView, cSecond);
        views.setTextViewText(R.id.thirdContactTextView, cThird);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}

