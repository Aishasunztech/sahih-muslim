package com.sunztech.sahihmuslim.BackgroundTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.view.MenuItem;

import com.sunztech.sahihmuslim.Database.DatabaseAccess;
import com.sunztech.sahihmuslim.Models.HadithItem;

import java.lang.ref.WeakReference;

public class UpdateBookmarkTask extends AsyncTask<Void,Void,Void> {
    private WeakReference<Context> contextWeakReference;
    private HadithItem hadithItem;
    private UpdateBookMarkListener listener;
    private MenuItem menuItem;


    public UpdateBookmarkTask(Context context,HadithItem hadithItem,MenuItem menuItem) {
        contextWeakReference = new WeakReference<>(context);
        this.hadithItem = hadithItem;
        this.menuItem = menuItem;

        if(context instanceof UpdateBookMarkListener)
        {
            listener = (UpdateBookMarkListener) context;
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(contextWeakReference.get());
        databaseAccess.open();
        databaseAccess.updateBookMark(hadithItem);
        databaseAccess.close();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        listener.bookMarkListener(menuItem,hadithItem);
    }

    public interface UpdateBookMarkListener{
        void bookMarkListener(MenuItem menuItem,HadithItem hadithItem);
    }
}
