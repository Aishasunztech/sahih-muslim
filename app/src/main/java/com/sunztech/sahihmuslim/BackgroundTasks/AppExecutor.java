package com.sunztech.sahihmuslim.BackgroundTasks;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

import androidx.annotation.NonNull;

public class AppExecutor {

private Executor mainThread;
    private static AppExecutor appExecutor;
    public static AppExecutor getInstance() {
        if (appExecutor == null) {
            appExecutor = new AppExecutor();
        }
        return appExecutor;
    }


    public Executor getMainThread() {
        return mainThread;
    }

    private AppExecutor(){
        mainThread = new MainThreadExecutor();
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }

}
