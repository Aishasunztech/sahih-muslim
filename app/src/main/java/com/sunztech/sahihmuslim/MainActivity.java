package com.sunztech.sahihmuslim;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.sunztech.sahihmuslim.Utilities.MyUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sunztech.sahihmuslim.MyApplication.numberOfClicks;
import static com.sunztech.sahihmuslim.Utilities.AppConstants.counter;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_chapters)
    TextView tv_chapters;
    @BindView(R.id.tv_bookmark)
    TextView tv_bookMark;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.adViewMain)
    AdView mAdview;

    private boolean isChapters = true;

    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        MyUtils.setTypeface(this, null, null, tv_chapters);
        MyUtils.setTypeface(this, null, null, tv_bookMark);

        setSupportActionBar(toolbar);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }

            @Override
            public void onAdClosed() {
                if(isChapters)
                {
                    Intent intent = new Intent(MainActivity.this, BookDetailsActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(MainActivity.this, BookMarkActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_rate_us:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + this.getPackageName())));
                break;
            case R.id.nav_exit:
                new AlertDialog.Builder(this)
                        .setTitle("Exit")
                        .setMessage("Are you sure you want to exit?")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            MainActivity.this.finish();

                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
                break;
            case R.id.nav_more:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:SunzTech")));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void gotoBookMark(View view) {
        if(numberOfClicks % counter == 0)
        {
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
            isChapters = false;
            numberOfClicks++;

        }else{
            Intent intent = new Intent(this, BookMarkActivity.class);
            startActivity(intent);
            numberOfClicks++;
        }


    }

    public void gotoHadith(View view) {
        if(numberOfClicks % counter == 0)
        {
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
            isChapters = true;
            numberOfClicks++;

        }else{
            Intent intent = new Intent(this, BookDetailsActivity.class);
            startActivity(intent);
            numberOfClicks++;
        }

    }

    public void shareBook(View view)
    {
        MyUtils.shareApp("https://play.google.com/store/apps/details?id=" + this.getPackageName(), this);
    }

}
