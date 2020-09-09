package com.sunztech.sahihmuslim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
 ;
import com.sunztech.sahihmuslim.Adapters.BookMarkAdapter;
import com.sunztech.sahihmuslim.BackgroundTasks.GetBookmarksTask;
import com.sunztech.sahihmuslim.Models.HadithItem;
import com.sunztech.sahihmuslim.Utilities.AppConstants;
import com.sunztech.sahihmuslim.Utilities.MyUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sunztech.sahihmuslim.MyApplication.numberOfClicks;
import static com.sunztech.sahihmuslim.Utilities.AppConstants.counter;

public class BookMarkActivity extends AppCompatActivity implements GetBookmarksTask.BookMarkListener, BookMarkAdapter.BookMarkListener {

    @BindView(R.id.bookMarkList)
    RecyclerView rc;
    @BindView(R.id.book_mark_bar)
    Toolbar toolbar;
    @BindView(R.id.adView)
    AdView mAdView;
    @BindView(R.id.no_bookMark)
    TextView no_bookmark;

    private InterstitialAd mInterstitialAd;
    private int clickedPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Bookmarks");
        MyUtils.changeToolbarFont(toolbar, this);


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
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {

            @Override
            public void onAdClosed() {
                sendToHadithDetails();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        new GetBookmarksTask(this).execute();

    }

    @Override
    public void bookmarkListener(ArrayList<HadithItem> hadithItems) {
        if(hadithItems.size()>0) {
            no_bookmark.setVisibility(View.GONE);
            rc.setAdapter(new BookMarkAdapter(this, hadithItems));
            rc.setLayoutManager(new LinearLayoutManager(this));
        }else{
            no_bookmark.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBookMarkClick(int position) {
        clickedPosition = position;
        if(numberOfClicks % counter == 0)
        {
            if(mInterstitialAd.isLoaded())
            {
                mInterstitialAd.show();
            }
            else{
                sendToHadithDetails();
            }
        }else{
            sendToHadithDetails();
        }
        numberOfClicks++;


    }

    private void sendToHadithDetails() {
        Intent intent = new Intent(this, HadithDetailsActivity.class);
        intent.putExtra(AppConstants.CURRENT_HADITH, clickedPosition);
        startActivity(intent);
    }
}
