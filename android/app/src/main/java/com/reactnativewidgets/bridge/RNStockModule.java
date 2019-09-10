package com.reactnativewidgets.bridge;

import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;


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
    public void selectStock(ReadableMap params) {
        //http://facebook.github.io/react-native/docs/native-modules-android.html#argument-types
        Log.d("RNStockModule", "stockSelected: " + RNUtils.toJsonObject(params));

    }

}
