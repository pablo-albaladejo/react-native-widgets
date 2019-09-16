package com.reactnativewidgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.reactnativewidgets.bridge.RNWorker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

/**
 * Implementation of App Widget functionality.
 */
public class StockWidget extends AppWidgetProvider {

    //https://developer.android.com/reference/android/appwidget/AppWidgetManager
    public static final String ACTION_APPWIDGET_CONFIGURE = "ACTION_WIDGET_CONFIGURE";
    public static final String ACTION_APPWIDGET_SET_STOCK = "ACTION_APPWIDGET_SET_STOCK";
    public static final String ACTION_APPWIDGET_OPEN_STOCK = "ACTION_APPWIDGET_OPEN_STOCK";
    public static final String ACTION_APPWIDGET_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE";
    public static final String ACTION_APPWIDGET_ENABLED = "android.appwidget.action.APPWIDGET_ENABLED";
    public static final String ACTION_APPWIDGET_DISABLED = "android.appwidget.action.APPWIDGET_DISABLED";


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, JSONObject stock) {
        Log.d("WIDGET_PROVIDER", "updateAppWidget " + appWidgetId);

        RemoteViews views = null;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String symbol = prefs.getString("widget_" + appWidgetId + "_symbol", null);

        if (symbol == null) {
            views = new RemoteViews(context.getPackageName(), R.layout.configure_widget);

            Intent intent = new Intent(context, StockWidget.class);
            intent.setAction(ACTION_APPWIDGET_CONFIGURE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            views.setOnClickPendingIntent(R.id.appwidget_configure, pendingIntent);

        } else if (stock != null) {

            try {
                views = new RemoteViews(context.getPackageName(), R.layout.stock_widget);

                CharSequence symbolText = stock.getString("symbol");
                CharSequence openText = stock.getString("open");
                CharSequence priceText = stock.getString("price");

                views.setTextViewText(R.id.appwidget_symbol, symbolText);
                views.setTextViewText(R.id.appwidget_open, openText);
                views.setTextViewText(R.id.appwidget_price, priceText);

                Intent intent = new Intent(context, StockWidget.class);
                intent.setAction(ACTION_APPWIDGET_OPEN_STOCK);
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                intent.putExtra("symbol", symbol);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                views.setOnClickPendingIntent(R.id.appwidget_symbol, pendingIntent);

            } catch (JSONException e) {
                Log.d("WIDGET_PROVIDER", "JSONException " + e);
            }

        } else {

            Intent intent = new Intent(context, StockWidget.class);
            intent.setAction(ACTION_APPWIDGET_OPEN_STOCK);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.putExtra("symbol", symbol);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            views = new RemoteViews(context.getPackageName(), R.layout.stock_widget_no_data);
            views.setTextViewText(R.id.appwidget_symbol, symbol);
            views.setOnClickPendingIntent(R.id.appwidget_symbol, pendingIntent);
        }

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private void refresh(Context context, int[] appWidgetIds){
        for (int appWidgetId : appWidgetIds) {
            Log.d("WIDGET_PROVIDER", "ACTION_APPWIDGET_UPDATE: " + appWidgetId);
            updateAppWidget(context, AppWidgetManager.getInstance(context), appWidgetId, null);
        }

    }

    @Override
    public void onReceive(final Context context, final Intent incomingIntent) {
        super.onReceive(context, incomingIntent);

        Bundle extras = incomingIntent.getExtras();
        String action = incomingIntent.getAction();

        Log.d("WIDGET_PROVIDER", "onReceive action:" + action);

        switch (action) {

            case ACTION_APPWIDGET_ENABLED:
                PeriodicWorkRequest.Builder myWorkBuilder =  new PeriodicWorkRequest.Builder(RNWorker.class, 15, TimeUnit.MINUTES).addTag("WIDGETJOB01");

                PeriodicWorkRequest myWork = myWorkBuilder.build();
                WorkManager.getInstance(context)
                        .enqueueUniquePeriodicWork("widgetJob", ExistingPeriodicWorkPolicy.REPLACE, myWork);

                break;

            case ACTION_APPWIDGET_DISABLED:
                WorkManager.getInstance(context).cancelAllWorkByTag("WIDGETJOB01");
                break;

            case ACTION_APPWIDGET_UPDATE:
                refresh(context,extras.getIntArray(AppWidgetManager.EXTRA_APPWIDGET_IDS));
                break;

            case ACTION_APPWIDGET_CONFIGURE:
                Log.d("WIDGET_PROVIDER", "ACTION_APPWIDGET_CONFIGURE: " + extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID));
                configureWidget(context, extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID));

                break;
            case ACTION_APPWIDGET_SET_STOCK:
                Log.d("WIDGET_PROVIDER", "ACTION_APPWIDGET_SET_STOCK");
                try {
                    setStock(context, new JSONObject(extras.getString("stock")), extras.getInt("appWidgetId"));
                } catch (JSONException e) {
                    Log.d("WIDGET_PROVIDER", "JSONException " + e);
                }

                break;

            case ACTION_APPWIDGET_OPEN_STOCK:
                Log.d("WIDGET_PROVIDER", "ACTION_APPWIDGET_SET_STOCK");
                openStock(context, extras.getString("symbol"));
                break;
            default:
                Log.d("WIDGET_PROVIDER", "onReceive default " + incomingIntent.getAction());
                break;
        }
    }

    private void configureWidget(Context context, int appWidgetId) {
        Bundle payload = new Bundle();
        payload.putInt("appWidgetId", appWidgetId);

        Bundle widget = new Bundle();
        widget.putBundle("payload", payload);

        Intent intent = new Intent(context, CustomReactActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.putExtra("module", "WidgetStockSearch");
        intent.putExtra("bundle", widget);

        context.startActivity(intent);
    }


    private void setStock(Context context, JSONObject stock, int appWidgetId) {
        try {
            String symbol = stock.getString("symbol");

            Log.d("WIDGET_PROVIDER", "setStock: symbol " + symbol + " appWidgetId " + appWidgetId);

            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            editor.putString("widget_" + appWidgetId + "_symbol", symbol);
            editor.apply();

            updateAppWidget(context, AppWidgetManager.getInstance(context), appWidgetId, stock);
        } catch (JSONException e) {
            Log.d("WIDGET_PROVIDER", "JSONException " + e);
        }

    }

    private void openStock(Context context, String symbol) {
        Log.d("WIDGET_PROVIDER", "openStock: symbol " + symbol);

        Bundle payload = new Bundle();
        payload.putString("symbol", symbol);

        Bundle widget = new Bundle();
        widget.putBundle("navigation", payload);

        Intent intent = new Intent(context, CustomReactActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("module", "ReactNativeWidgets");
        intent.putExtra("bundle", widget);

        context.startActivity(intent);
    }
}

