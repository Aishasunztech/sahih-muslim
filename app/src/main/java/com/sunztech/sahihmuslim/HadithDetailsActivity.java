package com.sunztech.sahihmuslim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
 ;
import com.sunztech.sahihmuslim.BackgroundTasks.UpdateBookmarkTask;
import com.sunztech.sahihmuslim.Dialogs.ReferenceDialog;
import com.sunztech.sahihmuslim.Models.HadithItem;
import com.sunztech.sahihmuslim.Utilities.AppConstants;
import com.sunztech.sahihmuslim.Utilities.MyUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HadithDetailsActivity extends AppCompatActivity implements UpdateBookmarkTask.UpdateBookMarkListener {

    @BindView(R.id.hadess_detail_bar) Toolbar toolbar;
    @BindView(R.id.tv_hadees_detail_arabic)
    TextView tv_arabic;
    @BindView(R.id.tv_hadees_detail_urdu) TextView tv_urdu;
    @BindView(R.id.btnNext)
    ImageView btnNext;
    @BindView(R.id.btnPrevious)
    ImageView btnPrev;
    @BindView(R.id.currentHadeesNum)
    TextView tv_currentHadees;
    @BindView(R.id.totalHadees)
    TextView tv_totalHadeesNumber;
    @BindView(R.id.hadees_detail_zoom_in_arb)
    ImageView zoom_in_arab;
    @BindView(R.id.hadees_detail_zoom_out_arb)
    ImageView zoom_out_arab;
    @BindView(R.id.hadees_detail_zoom_in_urdu)
    ImageView zoom_in_urdu;
    @BindView(R.id.hadees_detail_zoom_out_urdu)
    ImageView zoom_out_urdu;
    @BindView(R.id.btnEnglishHadees)
    TextView btnEnglish;
    @BindView(R.id.btnUrduHadees)
    TextView btnUrdu;
    @BindView(R.id.btnRaviHadees)
    TextView btnRavi;
    @BindView(R.id.btnReferenceHadees)
    TextView btnRef;
    @BindView(R.id.adView)
    AdView mAdView;




    private ArrayList<HadithItem> hadithItems;
    private int currentIndex;
    private HadithItem currentHadith;
    private Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hadith_details);
        ButterKnife.bind(this);

        hadithItems = AppConstants.CURRENT_HADITH_LIST;
        currentIndex = getIntent().getIntExtra(AppConstants.CURRENT_HADITH,0);



        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MyUtils.changeToolbarFont(toolbar,this);
        MyUtils.setTypeface(this,null,null,btnEnglish);
        MyUtils.setTypeface(this,null,null,btnUrdu);
        MyUtils.setTypeface(this,null,null,btnRavi);
        MyUtils.setTypeface(this,null,null,btnRef);


        MyUtils.setTypeface(this,tv_arabic,tv_urdu,null);
        tv_totalHadeesNumber.setText(hadithItems.size() + "");

        setHadith(currentIndex);

       /*   MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }

        });*/
        MobileAds.initialize(this,
                getString(R.string.app_id));

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


    }

    @OnClick(R.id.btnNext)
    public void nextClick()
    {
        if(currentIndex >= 0 && currentIndex < hadithItems.size())
        {
            setHadith(++currentIndex);
        }
    }

    @OnClick(R.id.btnPrevious)
    public void prevClick(){
        if(currentIndex>0)
        {
            setHadith(--currentIndex);
        }
    }

    @OnClick(R.id.hadees_detail_zoom_out_arb)
    public void zoomOutArab()
    {
        float fontSize = tv_arabic.getTextSize();
        tv_arabic.setTextSize(TypedValue.COMPLEX_UNIT_PX,--fontSize);
    }

    @OnClick(R.id.hadees_detail_zoom_in_arb)
    public void zoomOInArab()
    {
        float fontSize = tv_arabic.getTextSize();
        tv_arabic.setTextSize(TypedValue.COMPLEX_UNIT_PX,++fontSize);
    }

    @OnClick(R.id.hadees_detail_zoom_out_urdu)
    public void zoomOutUrdu()
    {
        float fontSize = tv_urdu.getTextSize();
        tv_urdu.setTextSize(TypedValue.COMPLEX_UNIT_PX,--fontSize);
    }

    @OnClick(R.id.hadees_detail_zoom_in_urdu)
    public void zoomOInUrdu()
    {
        float fontSize = tv_urdu.getTextSize();
        tv_urdu.setTextSize(TypedValue.COMPLEX_UNIT_PX,++fontSize);
    }

    @OnClick(R.id.btnEnglishHadees)
    public void englishHadith(){
        MyUtils.setTypeface(this,null,null,tv_urdu);
        tv_urdu.setText(currentHadith.getEnglish());
        tv_urdu.setGravity(Gravity.LEFT);
    }

    @OnClick(R.id.btnUrduHadees)
    public void urduHadith(){
        MyUtils.setTypeface(this,null,tv_urdu,null);
        tv_urdu.setText(currentHadith.getUrdu());
        tv_urdu.setGravity(Gravity.RIGHT);
    }

    @OnClick(R.id.btnRaviHadees)
    public void raviHadith(){
        MyUtils.setTypeface(this,null,tv_urdu,null);
        tv_urdu.setText(currentHadith.getRavi());
        tv_urdu.setGravity(Gravity.RIGHT);
    }

    @OnClick(R.id.btnReferenceHadees)
    public void refHadith()
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        ReferenceDialog referenceDialog = new ReferenceDialog(HadithDetailsActivity.this,currentHadith,R.style.setDateDialogTheme);
        referenceDialog.show();
    }

    private void setHadith(int currentIndex) {
        try {
            currentHadith = hadithItems.get(currentIndex);
            tv_arabic.setText(currentHadith.getArabic());
            tv_urdu.setText(currentHadith.getUrdu());
            getSupportActionBar().setTitle(getResources().getString(R.string.bookNameToolbar) + currentHadith.getHadees_number());
            tv_currentHadees.setText(currentIndex + 1 + "");
            invalidateOptionsMenu();
        }
        catch (Exception e)
        {
            Log.d("HadithException",e.toString());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.hadith_details_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.book_mark_menu);

        if(currentHadith != null) {
            if (currentHadith.isIs_bookmarked() == 0) {
                menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_bookmark_none));
            } else {
                menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_book_mark_done));

            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.share_menu_item:
                final String shareBody = "Book Name: " + currentHadith.getBookEng() +", Hadees # " + currentHadith.getHadees_number() + "\n\n" + currentHadith.getArabic() + "\n\n" + currentHadith.getUrdu() + "\n\n" ;
//                + "https//near.pk"
                MyUtils.shareContent(shareBody,this);

                break;
            case R.id.book_mark_menu:
                if(currentHadith.isIs_bookmarked() == 0)
                {
                    currentHadith.setIs_bookmarked(1);
                }
                else{
                    currentHadith.setIs_bookmarked(0);
                }
                new UpdateBookmarkTask(this,currentHadith,item).execute();
                break;
        }
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
    }

    @Override
    public void bookMarkListener(MenuItem menuItem,HadithItem hadithItem) {
        if(hadithItem.isIs_bookmarked() == 0)
        {
            menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_bookmark_none));

        }else{
            menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_book_mark_done));

        }
    }
}
