package com.reactnativewidgets.bridge;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

//https://developer.android.com/topic/libraries/architecture/workmanager/basics
public class RNWorker extends Worker {

    public RNWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public ListenableWorker.Result doWork() {
        Log.d("RNWorker", "doWork");

        return ListenableWorker.Result.success();
    }
}