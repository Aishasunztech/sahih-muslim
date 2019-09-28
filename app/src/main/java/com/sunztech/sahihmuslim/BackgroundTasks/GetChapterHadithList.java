package com.sunztech.sahihmuslim.BackgroundTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.sunztech.sahihmuslim.Database.DatabaseAccess;
import com.sunztech.sahihmuslim.Models.HadithItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class GetChapterHadithList extends AsyncTask<Void,Void, ArrayList<HadithItem>> {
    private WeakReference<Context> contextWeakReference;
    private int chapterId;
    private HadithListener listener;

    public GetChapterHadithList(Context context, int chapterId) {
        contextWeakReference = new WeakReference<>(context);
        this.chapterId = chapterId;
        if(context instanceof HadithListener)
        {
            listener = (HadithListener) context;
        }
    }


    @Override
    protected ArrayList<HadithItem> doInBackground(Void... voids) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(contextWeakReference.get());
        databaseAccess.open();
        ArrayList<HadithItem> hadithItems = databaseAccess.getChapterHadith(chapterId);
        databaseAccess.close();
        return hadithItems;
    }

    @Override
    protected void onPostExecute(ArrayList<HadithItem> hadithItems) {
        if(hadithItems != null && hadithItems.size() >0)
        {
            listener.getChapHadithList(hadithItems);
        }
    }

    public interface HadithListener{
        void getChapHadithList(ArrayList<HadithItem> hadithItems);

    }
}
