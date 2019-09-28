package com.sunztech.sahihmuslim.BackgroundTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.sunztech.sahihmuslim.Database.DatabaseAccess;
import com.sunztech.sahihmuslim.Models.HadithItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class GetBookmarksTask extends AsyncTask<Void,Void, ArrayList<HadithItem>> {
    private WeakReference<Context> contextWeakReference;
    private BookMarkListener listener;

    public GetBookmarksTask(Context context) {
        contextWeakReference = new WeakReference<>(context);
        if(context instanceof  BookMarkListener)
        {
            listener  = (BookMarkListener) context;
        }
    }

    @Override
    protected ArrayList<HadithItem> doInBackground(Void... voids) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(contextWeakReference.get());
        databaseAccess.open();
        ArrayList<HadithItem> hadithItems = databaseAccess.getBookMarks();
        databaseAccess.close();
        return hadithItems;
    }

    @Override
    protected void onPostExecute(ArrayList<HadithItem> hadithItems) {
        if(hadithItems != null) {
            listener.bookmarkListener(hadithItems);
        }
    }

    public interface BookMarkListener{
        void bookmarkListener(ArrayList<HadithItem> hadithItems);
    }
}
