package com.sunztech.sahihmuslim.BackgroundTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.sunztech.sahihmuslim.Database.DatabaseAccess;
import com.sunztech.sahihmuslim.Models.HadithItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class SearchHadithTask extends AsyncTask<Void, Void, ArrayList<HadithItem>> {
    private WeakReference<Context> contextWeakReference;
    private String columnName;
    private String searchingValue;
    private SearchHadithListener listener;

    public SearchHadithTask(Context context, String columnName, String searchingValue) {
        contextWeakReference = new WeakReference<>(context);
        this.columnName = columnName;
        this.searchingValue = searchingValue;

        if (context instanceof SearchHadithListener) {
            listener = (SearchHadithListener) context;
        }
    }

    @Override
    protected ArrayList<HadithItem> doInBackground(Void... voids) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(contextWeakReference.get());
        databaseAccess.open();
        ArrayList<HadithItem> hadithItems = databaseAccess.searchHadith(columnName,searchingValue);
        databaseAccess.close();
        return hadithItems;
    }

    @Override
    protected void onPostExecute(ArrayList<HadithItem> hadithItems) {
        if (hadithItems != null && hadithItems.size() > 0) {
            listener.searchedHadith(hadithItems);
        }
        else{
            Toast.makeText(contextWeakReference.get(), "No Result Found", Toast.LENGTH_SHORT).show();
        }
    }

    public interface SearchHadithListener {
        void searchedHadith(ArrayList<HadithItem> hadithItems);
    }
}
