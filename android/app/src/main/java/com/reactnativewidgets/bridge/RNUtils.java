package com.reactnativewidgets.bridge;

import android.util.Log;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RNUtils {

    public static JSONObject toJsonObject(ReadableMap readableMap){
        JSONObject object = new JSONObject();
        try {
            ReadableMapKeySetIterator iter = readableMap.keySetIterator();
            while(iter.hasNextKey()) {
                String key = iter.nextKey();
                ReadableType type = readableMap.getType(key);
                switch(type) {
                    case Boolean:
                        object.put(key, readableMap.getBoolean(key));
                        break;
                    case Number:
                        object.put(key, readableMap.getDouble(key));
                        break;
                    case String:
                        object.put(key, readableMap.getString(key));
                        break;
                    case Map:
                        object.put(key, toJsonObject(readableMap.getMap(key)));
                        break;
                    case Array:
                        object.put(key, toJsonArray(readableMap.getArray(key)));
                        break;
                }
            }
        } catch (JSONException e){
            Log.d("RNUtils", "JSONException: " + e);

        } finally {
            return object;
        }
    }

    public static JSONArray toJsonArray(ReadableArray readableArray) {
        JSONArray array = new JSONArray();
        try{
            for (int idx = 0; idx < readableArray.size(); idx++) {
                ReadableType type = readableArray.getType(idx);
                switch(type) {
                    case Boolean:
                        array.put(readableArray.getBoolean(idx));
                        break;
                    case Number:
                        array.put(readableArray.getDouble(idx));
                        break;
                    case String:
                        array.put(readableArray.getString(idx));
                        break;
                    case Map:
                        array.put(toJsonObject(readableArray.getMap(idx)));
                        break;
                    case Array:
                        array.put(toJsonArray(readableArray.getArray(idx)));
                        break;
                }
            }
        } catch (JSONException e){
            Log.d("RNUtils", "JSONException: " + e);
        } finally {
            return array;
        }
    }
}
