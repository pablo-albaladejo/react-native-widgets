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

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Implementation of App Widget functionality.
 */
public class StockWidget extends AppWidgetProvider {

    //https://developer.android.com/reference/android/appwidget/AppWidgetManager
    public static final String ACTION_APPWIDGET_CONFIGURE = "ACTION_WIDGET_CONFIGURE";
    public static final String ACTION_APPWIDGET_SET_STOCK = "ACTION_APPWIDGET_SET_STOCK";
    public static final String ACTION_APPWIDGET_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE";
    public static final String ACTION_APPWIDGET_ENABLED = "android.appwidget.action.ACTION_APPWIDGET_ENABLED";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, JSONObject stock) {
        Log.d("WIDGET_PROVIDER", "updateAppWidget " + appWidgetId);

        RemoteViews views = null;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String symbol = prefs.getString("widget_"+appWidgetId+"_symbol", null);

        if(symbol == null){
            views = new RemoteViews(context.getPackageName(), R.layout.configure_widget);

            Intent intent = new Intent(context, StockWidget.class);
            intent.setAction(ACTION_APPWIDGET_CONFIGURE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            views.setOnClickPendingIntent(R.id.appwidget_configure, pendingIntent );

        }else if(stock != null){

            try{
                views = new RemoteViews(context.getPackageName(), R.layout.stock_widget);

                CharSequence symbolText = stock.getString("symbol");
                CharSequence openText = "214.05";
                CharSequence priceText = "213.26";

                views.setTextViewText(R.id.appwidget_symbol, symbolText);
                views.setTextViewText(R.id.appwidget_open, openText);
                views.setTextViewText(R.id.appwidget_price, priceText);

            }catch (JSONException e){
                Log.d("WIDGET_PROVIDER", "JSONException " + e);
            }

        }else{
            views = new RemoteViews(context.getPackageName(), R.layout.stock_widget_no_data);
            views.setTextViewText(R.id.appwidget_symbol, symbol);
        }

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(final Context context, final Intent incomingIntent) {
        super.onReceive(context, incomingIntent);
        Log.d("WIDGET_PROVIDER", "onReceive");

        Bundle extras = incomingIntent.getExtras();

        switch (incomingIntent.getAction()){
            case ACTION_APPWIDGET_UPDATE:
            case ACTION_APPWIDGET_ENABLED:

                int[] appWidgetIds = extras.getIntArray(AppWidgetManager.EXTRA_APPWIDGET_IDS);
                for (int appWidgetId : appWidgetIds) {
                    Log.d("WIDGET_PROVIDER", "ACTION_APPWIDGET_UPDATE: " + appWidgetId);
                    updateAppWidget(context, AppWidgetManager.getInstance(context), appWidgetId, null);
                }

                break;

            case ACTION_APPWIDGET_CONFIGURE:
                Log.d("WIDGET_PROVIDER", "ACTION_APPWIDGET_CONFIGURE: " + extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID));
                configureWidget(context, extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID));

                break;
            case ACTION_APPWIDGET_SET_STOCK:
                Log.d("WIDGET_PROVIDER", "ACTION_APPWIDGET_SET_STOCK: ");

                setStock(context, extras.getString("symbol"), extras.getInt("appWidgetId"));

                break;

            default:
                Log.d("WIDGET_PROVIDER", "onReceive default " + incomingIntent.getAction());
                break;
        }
    }

    private void configureWidget(Context context, int appWidgetId){
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


    private void setStock(Context context, String symbol, int appWidgetId){
        Log.d("WIDGET_PROVIDER", "setStock: symbol " + symbol + " appWidgetId " + appWidgetId);

        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("widget_"+appWidgetId+"_symbol", symbol);
        editor.apply();

        updateAppWidget(context, AppWidgetManager.getInstance(context), appWidgetId, null);
    }
}

