package com.reactnativewidgets.bridge;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class RNWorker extends Worker {

    public RNWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public ListenableWorker.Result doWork() {
        Log.d("RNWorker", "doWork");


        /*Intent service = new Intent(getApplicationContext(), RNService.class);
        Bundle bundle = new Bundle();

        bundle.putString("foo", "bar");
        service.putExtras(bundle);

        getApplicationContext().startService(service);*/

        return ListenableWorker.Result.success();
    }
}