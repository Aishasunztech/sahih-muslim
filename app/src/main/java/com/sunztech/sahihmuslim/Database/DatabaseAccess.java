package com.sunztech.sahihmuslim.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sunztech.sahihmuslim.Models.BookChapter;
import com.sunztech.sahihmuslim.Models.HadithItem;

import java.util.ArrayList;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor c ;
    private Context context;

    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
        this.context = context;

    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.db = openHelper.getWritableDatabase();
    }

    public void close() {
        if (db != null) {
            this.db.close();
        }
    }

    public ArrayList<BookChapter> getChapters() {

        ArrayList<BookChapter> arrayOfChapters = new ArrayList<>();
        c = db.rawQuery("SELECT DISTINCT Kitab_ID,Kitab,Kitab_Eng" +
                "  FROM tbl_Muslim", new String[]{});
        if (c != null) {
            while (c.moveToNext()) {
                BookChapter bookChapter = new BookChapter();
                bookChapter.setChapterId(c.getInt(0));
                bookChapter.setIn_urdu(c.getString(1));
                bookChapter.setIn_eng(c.getString(2));
                arrayOfChapters.add(bookChapter);
            }
        }
        return arrayOfChapters;
    }

    public ArrayList<HadithItem> getChapterHadith(int chapterId) {
        HadithItem hadees  ;
        c = db.rawQuery("select * from tbl_Muslim WHERE Kitab_ID = " + chapterId, new String[]{});
        ArrayList<HadithItem> hadeesItems = new ArrayList<>();
        if (c != null) {
            while (c.moveToNext()) {

                hadees = new HadithItem();
                hadees.setHadees_number(c.getInt(1));
                hadees.setBookUR(c.getString(2));
                hadees.setBaab_Eng(c.getString(3));
                hadees.setKitab_Eng(c.getString(4));
                hadees.setBaab(c.getString(5));
                hadees.setKitab(c.getString(6));
                hadees.setArabic(c.getString(7));
                hadees.setRavi(c.getString(8));
                hadees.setUrdu(c.getString(9));
                hadees.setEnglish(c.getString(10));
                hadees.setVolume(c.getString(11));
                hadees.setBookEng(c.getString(12));
                hadees.setBaab_ID(c.getInt(13));
                hadees.setKitab_ID(c.getInt(14));
                hadees.setTakhreej(c.getString(15));
                hadees.setWazahat(c.getString(16));
                hadees.setStatus(c.getString(17));
                hadees.setStatus_Ref(c.getString(18));
                hadees.setEnglish_Ref(c.getString(19));
                hadees.setBookNo(c.getInt(20));
                hadees.setSahih_Zaeef(c.getString(21));
                hadees.setIs_bookmarked(c.getInt(22));


                hadeesItems.add(hadees);


            }
        }

        return hadeesItems;
    }

    public void updateBookMark(HadithItem hadithItem)
    {
        ContentValues args = new ContentValues();
        if(hadithItem.isIs_bookmarked() == 0 )
        {
            args.put("is_bookmarked", false);

        }
        else{
            args.put("is_bookmarked", true);

        }
        db.update("tbl_Muslim", args, "hadees_number = " + hadithItem.getHadees_number(), null);
    }

    public ArrayList<HadithItem> getBookMarks()
    {
        HadithItem hadees  ;
        c = db.rawQuery("select * from tbl_Muslim WHERE is_bookmarked = " + 1, new String[]{});
        ArrayList<HadithItem> hadeesItems = new ArrayList<>();
        if (c != null) {
            while (c.moveToNext()) {

                hadees = new HadithItem();
                hadees.setHadees_number(c.getInt(1));
                hadees.setBookUR(c.getString(2));
                hadees.setBaab_Eng(c.getString(3));
                hadees.setKitab_Eng(c.getString(4));
                hadees.setBaab(c.getString(5));
                hadees.setKitab(c.getString(6));
                hadees.setArabic(c.getString(7));
                hadees.setRavi(c.getString(8));
                hadees.setUrdu(c.getString(9));
                hadees.setEnglish(c.getString(10));
                hadees.setVolume(c.getString(11));
                hadees.setBookEng(c.getString(12));
                hadees.setBaab_ID(c.getInt(13));
                hadees.setKitab_ID(c.getInt(14));
                hadees.setTakhreej(c.getString(15));
                hadees.setWazahat(c.getString(16));
                hadees.setStatus(c.getString(17));
                hadees.setStatus_Ref(c.getString(18));
                hadees.setEnglish_Ref(c.getString(19));
                hadees.setBookNo(c.getInt(20));
                hadees.setSahih_Zaeef(c.getString(21));
                hadees.setIs_bookmarked(c.getInt(22));


                hadeesItems.add(hadees);


            }
        }

        return hadeesItems;
    }

    public ArrayList<HadithItem> searchHadith(String columnName, String searchingValue) {
        HadithItem hadees  ;
        if(columnName.equals("hadees_number")) {
            c = db.rawQuery("select * from tbl_Muslim WHERE " + columnName + " = " + searchingValue, new String[]{});

        }
        else{
            c = db.rawQuery("select * from tbl_Muslim WHERE "  + columnName + " like '%" + searchingValue + "%'", new String[]{});

        }
        ArrayList<HadithItem> hadeesItems = new ArrayList<>();
        if (c != null) {
            while (c.moveToNext()) {

                hadees = new HadithItem();
                hadees.setHadees_number(c.getInt(1));
                hadees.setBookUR(c.getString(2));
                hadees.setBaab_Eng(c.getString(3));
                hadees.setKitab_Eng(c.getString(4));
                hadees.setBaab(c.getString(5));
                hadees.setKitab(c.getString(6));
                hadees.setArabic(c.getString(7));
                hadees.setRavi(c.getString(8));
                hadees.setUrdu(c.getString(9));
                hadees.setEnglish(c.getString(10));
                hadees.setVolume(c.getString(11));
                hadees.setBookEng(c.getString(12));
                hadees.setBaab_ID(c.getInt(13));
                hadees.setKitab_ID(c.getInt(14));
                hadees.setTakhreej(c.getString(15));
                hadees.setWazahat(c.getString(16));
                hadees.setStatus(c.getString(17));
                hadees.setStatus_Ref(c.getString(18));
                hadees.setEnglish_Ref(c.getString(19));
                hadees.setBookNo(c.getInt(20));
                hadees.setSahih_Zaeef(c.getString(21));
                hadees.setIs_bookmarked(c.getInt(22));


                hadeesItems.add(hadees);


            }
        }

        return hadeesItems;

    }


    public HadithItem getRandomHadith()
    {
        HadithItem hadees = null;
        c = db.rawQuery("SELECT * FROM tbl_Muslim ORDER BY RANDOM() LIMIT 1", new String[]{});
        if (c != null) {
            while (c.moveToNext()) {

                hadees = new HadithItem();
                hadees.setHadees_number(c.getInt(1));
                hadees.setBookUR(c.getString(2));
                hadees.setBaab_Eng(c.getString(3));
                hadees.setKitab_Eng(c.getString(4));
                hadees.setBaab(c.getString(5));
                hadees.setKitab(c.getString(6));
                hadees.setArabic(c.getString(7));
                hadees.setRavi(c.getString(8));
                hadees.setUrdu(c.getString(9));
                hadees.setEnglish(c.getString(10));
                hadees.setVolume(c.getString(11));
                hadees.setBookEng(c.getString(12));
                hadees.setBaab_ID(c.getInt(13));
                hadees.setKitab_ID(c.getInt(14));
                hadees.setTakhreej(c.getString(15));
                hadees.setWazahat(c.getString(16));
                hadees.setStatus(c.getString(17));
                hadees.setStatus_Ref(c.getString(18));
                hadees.setEnglish_Ref(c.getString(19));
                hadees.setBookNo(c.getInt(20));
                hadees.setSahih_Zaeef(c.getString(21));
                hadees.setIs_bookmarked(c.getInt(22));

            }
        }
        return hadees;
    }


}
