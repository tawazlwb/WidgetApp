package com.ikheiry.widgetapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RemoteViews;

public class MyAppWidgetConfigActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "prefs";
    public static final String KEY_BUTTON_TEXT = "keyButtonText";

    private int appWIdgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private EditText editTextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_app_widget_config);

        Intent configIntent = getIntent();
        Bundle extras = configIntent.getExtras();

        if(extras != null){
            appWIdgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWIdgetId);
        setResult(RESULT_CANCELED, resultValue);

        if(appWIdgetId == AppWidgetManager.INVALID_APPWIDGET_ID){
            finish();
        }

        editTextButton = findViewById(R.id.edit_text_button);
    }

    public void confirmConfiguration(View view) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        String buttonText = editTextButton.getText().toString();

        RemoteViews views = new RemoteViews(this.getPackageName(), R.layout.my_widget);
        views.setOnClickPendingIntent(R.id.my_widget_bn, pendingIntent);

        views.setCharSequence(R.id.my_widget_bn, "setText", buttonText);
        //views.setInt(R.id.my_widget_bn, "setBackgroundColor", Color.RED);
        //views.setBoolean(R.id.my_widget_bn, "setEnabled", false);

        appWidgetManager.updateAppWidget(appWIdgetId, views);

        SharedPreferences preferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_BUTTON_TEXT + appWIdgetId, buttonText);
        editor.apply();

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWIdgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }
}
