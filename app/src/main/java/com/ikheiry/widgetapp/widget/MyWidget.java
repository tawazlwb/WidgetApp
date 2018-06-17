package com.ikheiry.widgetapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.ikheiry.widgetapp.MainActivity;
import com.ikheiry.widgetapp.R;

import static com.ikheiry.widgetapp.MyAppWidgetConfigActivity.KEY_BUTTON_TEXT;
import static com.ikheiry.widgetapp.MyAppWidgetConfigActivity.SHARED_PREFS;

public class MyWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds){
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
            String buttonText = preferences .getString(KEY_BUTTON_TEXT + appWidgetId, "Press Me");

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget);
            views.setOnClickPendingIntent(R.id.my_widget_bn, pendingIntent);
            views.setCharSequence(R.id.my_widget_bn, "setText", buttonText);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}
