package com.reactnativewidgets.bridge;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.reactnativewidgets.StockWidget;

import org.json.JSONObject;


public class RNStockModule extends ReactContextBaseJavaModule {

    private ReactContext mReactContext;

    @Override
    public String getName() {
        return "RNStockModule";
    }

    public RNStockModule(final ReactApplicationContext reactContext) {
        super(reactContext);
        mReactContext = reactContext;
    }

    @ReactMethod
    public void selectStock(String symbol, int appWidgetId, Callback cb) {
        //http://facebook.github.io/react-native/docs/native-modules-android.html#argument-types

        Log.d("RNStockModule", "stockSelected: symbol " + symbol + " appWidgetId "+ appWidgetId);

        Intent intent = new Intent(mReactContext, StockWidget.class);
        intent.setAction(StockWidget.ACTION_APPWIDGET_SET_STOCK);
        intent.putExtra("symbol", symbol);
        intent.putExtra("appWidgetId", appWidgetId);

        mReactContext.sendBroadcast(intent);
        cb.invoke();
    }


}
