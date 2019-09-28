package com.sunztech.sahihmuslim.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class HadithItem implements Parcelable {
    private int hadees_number;
    private String BookUR;
    private String Baab_Eng;
    private String Kitab_Eng;
    private String Baab;
    private String Kitab;
    private String Arabic;
    private String Ravi;
    private String Urdu;
    private String English;
    private String Volume;
    private String BookEng;
    private int Baab_ID;
    private int Kitab_ID;
    private String Takhreej;
    private String Wazahat;
    private String Status;
    private String Status_Ref;
    private String English_Ref;
    private int BookNo;
    private String Sahih_Zaeef;
    private int is_bookmarked;

    public int getHadees_number() {
        return hadees_number;
    }

    public void setHadees_number(int hadees_number) {
        this.hadees_number = hadees_number;
    }

    public String getBookUR() {
        return BookUR;
    }

    public void setBookUR(String bookUR) {
        BookUR = bookUR;
    }

    public String getBaab_Eng() {
        return Baab_Eng;
    }

    public void setBaab_Eng(String baab_Eng) {
        Baab_Eng = baab_Eng;
    }

    public String getKitab_Eng() {
        return Kitab_Eng;
    }

    public void setKitab_Eng(String kitab_Eng) {
        Kitab_Eng = kitab_Eng;
    }

    public String getBaab() {
        return Baab;
    }

    public void setBaab(String baab) {
        Baab = baab;
    }

    public String getKitab() {
        return Kitab;
    }

    public void setKitab(String kitab) {
        Kitab = kitab;
    }

    public String getArabic() {
        return Arabic;
    }

    public void setArabic(String arabic) {
        Arabic = arabic;
    }

    public String getRavi() {
        return Ravi;
    }

    public void setRavi(String ravi) {
        Ravi = ravi;
    }

    public String getUrdu() {
        return Urdu;
    }

    public void setUrdu(String urdu) {
        Urdu = urdu;
    }

    public String getEnglish() {
        return English;
    }

    public void setEnglish(String english) {
        English = english;
    }

    public String getVolume() {
        return Volume;
    }

    public void setVolume(String volume) {
        Volume = volume;
    }

    public String getBookEng() {
        return BookEng;
    }

    public void setBookEng(String bookEng) {
        BookEng = bookEng;
    }

    public int getBaab_ID() {
        return Baab_ID;
    }

    public void setBaab_ID(int baab_ID) {
        Baab_ID = baab_ID;
    }

    public int getKitab_ID() {
        return Kitab_ID;
    }

    public void setKitab_ID(int kitab_ID) {
        Kitab_ID = kitab_ID;
    }

    public String getTakhreej() {
        return Takhreej;
    }

    public void setTakhreej(String takhreej) {
        Takhreej = takhreej;
    }

    public String getWazahat() {
        return Wazahat;
    }

    public void setWazahat(String wazahat) {
        Wazahat = wazahat;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getStatus_Ref() {
        return Status_Ref;
    }

    public void setStatus_Ref(String status_Ref) {
        Status_Ref = status_Ref;
    }

    public String getEnglish_Ref() {
        return English_Ref;
    }

    public void setEnglish_Ref(String english_Ref) {
        English_Ref = english_Ref;
    }

    public int getBookNo() {
        return BookNo;
    }

    public void setBookNo(int bookNo) {
        BookNo = bookNo;
    }

    public String getSahih_Zaeef() {
        return Sahih_Zaeef;
    }

    public void setSahih_Zaeef(String sahih_Zaeef) {
        Sahih_Zaeef = sahih_Zaeef;
    }

    public int isIs_bookmarked() {
        return is_bookmarked;
    }

    public void setIs_bookmarked(int is_bookmarked) {
        this.is_bookmarked = is_bookmarked;
    }



    public HadithItem() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.hadees_number);
        dest.writeString(this.BookUR);
        dest.writeString(this.Baab_Eng);
        dest.writeString(this.Kitab_Eng);
        dest.writeString(this.Baab);
        dest.writeString(this.Kitab);
        dest.writeString(this.Arabic);
        dest.writeString(this.Ravi);
        dest.writeString(this.Urdu);
        dest.writeString(this.English);
        dest.writeString(this.Volume);
        dest.writeString(this.BookEng);
        dest.writeInt(this.Baab_ID);
        dest.writeInt(this.Kitab_ID);
        dest.writeString(this.Takhreej);
        dest.writeString(this.Wazahat);
        dest.writeString(this.Status);
        dest.writeString(this.Status_Ref);
        dest.writeString(this.English_Ref);
        dest.writeInt(this.BookNo);
        dest.writeString(this.Sahih_Zaeef);
        dest.writeInt(this.is_bookmarked);
    }

    protected HadithItem(Parcel in) {
        this.hadees_number = in.readInt();
        this.BookUR = in.readString();
        this.Baab_Eng = in.readString();
        this.Kitab_Eng = in.readString();
        this.Baab = in.readString();
        this.Kitab = in.readString();
        this.Arabic = in.readString();
        this.Ravi = in.readString();
        this.Urdu = in.readString();
        this.English = in.readString();
        this.Volume = in.readString();
        this.BookEng = in.readString();
        this.Baab_ID = in.readInt();
        this.Kitab_ID = in.readInt();
        this.Takhreej = in.readString();
        this.Wazahat = in.readString();
        this.Status = in.readString();
        this.Status_Ref = in.readString();
        this.English_Ref = in.readString();
        this.BookNo = in.readInt();
        this.Sahih_Zaeef = in.readString();
        this.is_bookmarked = in.readInt();
    }

    public static final Parcelable.Creator<HadithItem> CREATOR = new Parcelable.Creator<HadithItem>() {
        @Override
        public HadithItem createFromParcel(Parcel source) {
            return new HadithItem(source);
        }

        @Override
        public HadithItem[] newArray(int size) {
            return new HadithItem[size];
        }
    };
}
