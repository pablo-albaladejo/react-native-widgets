package com.reactnativewidgets.bridge;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class RNWorker extends Worker {

    public RNWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {
        Log.d("RNWorker", "doWork");

        Intent service = new Intent(getApplicationContext(), RNService.class);
        Bundle bundle = new Bundle();

        bundle.putString("foo", "bar");
        service.putExtras(bundle);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getApplicationContext().startForegroundService(service);
        }else{
            getApplicationContext().startService(service);
        }

        return Result.success();
    }
}