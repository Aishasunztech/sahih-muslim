package com.sunztech.sahihmuslim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
 ;
import com.sunztech.sahihmuslim.Adapters.HadithListAdapter;
import com.sunztech.sahihmuslim.BackgroundTasks.GetChapterHadithList;
import com.sunztech.sahihmuslim.BackgroundTasks.SearchHadithTask;
import com.sunztech.sahihmuslim.Models.HadithItem;
import com.sunztech.sahihmuslim.Utilities.AppConstants;
import com.sunztech.sahihmuslim.Utilities.MyUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sunztech.sahihmuslim.MyApplication.numberOfClicks;
import static com.sunztech.sahihmuslim.Utilities.AppConstants.counter;

public class ChapterDetailsActivity extends AppCompatActivity implements
        GetChapterHadithList.HadithListener, SearchHadithTask.SearchHadithListener
        , HadithListAdapter.HadithListClickedListener {

    @BindView(R.id.chapter_details_bar)
    Toolbar toolbar;
    @BindView(R.id.chapter_hadith_list)
    RecyclerView rc;
    @BindView(R.id.adView)
    AdView mAdView;

    private InterstitialAd mInterstitialAd;
    private int clickedPosition = 0;

    private String sender, columnName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_details);
        ButterKnife.bind(this);

        sender = getIntent().getStringExtra(AppConstants.CHAPTER_DETAILS_SENDER);
        rc.setAdapter(null);
        rc.setLayoutManager(new LinearLayoutManager(this));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MyUtils.changeToolbarFont(toolbar, this);

        int chapterId = getIntent().getIntExtra(AppConstants.CHAPTER_ID, 1);
        if (sender.equals(AppConstants.BOOK_DETAILS_ACTVIITY)) {
            new GetChapterHadithList(this, chapterId).execute();
        } else if (sender.equals(AppConstants.SEARCH_FRAGMENT)) {
            columnName = getIntent().getStringExtra(AppConstants.SEARCH_COLUMN_NAME);
            String value = getIntent().getStringExtra(AppConstants.SEARCHING_VALUE);
            new SearchHadithTask(this, columnName, value).execute();
        }

       /*   MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }

        });*/
        MobileAds.initialize(this,
                getString(R.string.app_id));

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_id));

        mInterstitialAd.setAdListener(new AdListener() {

            @Override
            public void onAdClosed() {
                sendToHadithDetails();
            }

            @Override
            public void onAdLoaded() {
                if(mInterstitialAd.isLoaded())
                {
                    mInterstitialAd.show();
                }
                else{
                    sendToHadithDetails();
                }
            }
        });

    }

    @Override
    public void getChapHadithList(ArrayList<HadithItem> hadithItems) {
        setHadithList(hadithItems);
    }

    @Override
    public void searchedHadith(ArrayList<HadithItem> hadithItems) {
        setHadithList(hadithItems);
    }

    private void setHadithList(ArrayList<HadithItem> hadithItems) {
        getSupportActionBar().setTitle(hadithItems.size() + " Records Found");

        if (sender.equals(AppConstants.BOOK_DETAILS_ACTVIITY)) {
            rc.setAdapter(new HadithListAdapter(this, hadithItems, false));

        } else if (sender.equals(AppConstants.SEARCH_FRAGMENT)) {
            if (columnName != null && columnName.equals("English")) {
                rc.setAdapter(new HadithListAdapter(this, hadithItems, true));

            } else {
                rc.setAdapter(new HadithListAdapter(this, hadithItems, false));

            }

        }
        rc.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;

    }


    @Override
    public void clickedHadit(int position) {
        clickedPosition = position;
        if(numberOfClicks % counter == 0)
        {
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
        }else{
            sendToHadithDetails();
        }
        numberOfClicks++;
    }

    private void sendToHadithDetails() {
        Intent intent = new Intent(this, HadithDetailsActivity.class);
        intent.putExtra(AppConstants.CURRENT_HADITH, clickedPosition);
//                    intent.putParcelableArrayListExtra(AppConstants.HADITH_LIST, hadithItems);
        startActivity(intent);
    }


}
