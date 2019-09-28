package com.sunztech.sahihmuslim.BackgroundTasks;

import android.os.AsyncTask;

import com.sunztech.sahihmuslim.Models.BookChapter;
import com.sunztech.sahihmuslim.Database.DatabaseAccess;
import com.sunztech.sahihmuslim.Fragments.ChapterFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class GetChaptersTask extends AsyncTask<Void,Void, ArrayList<BookChapter>> {
    private WeakReference<ChapterFragment> contextWeakReference;
    private ChapterListener listener;

    public GetChaptersTask(ChapterFragment fragment) {
        contextWeakReference = new WeakReference<>(fragment);
        if(fragment instanceof ChapterListener)
        {
            listener = (ChapterListener) fragment;
        }
    }

    @Override
    protected ArrayList<BookChapter> doInBackground(Void... voids) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(contextWeakReference.get().getActivity());
        databaseAccess.open();
        ArrayList<BookChapter> chapters = databaseAccess.getChapters();
        return chapters;
    }

    @Override
    protected void onPostExecute(ArrayList<BookChapter> bookChapters) {
        if(bookChapters != null && bookChapters.size()>0)
        {
            listener.getChapters(bookChapters);
        }

    }

    public interface ChapterListener{
        void getChapters(ArrayList<BookChapter> chapters);
    }
}
