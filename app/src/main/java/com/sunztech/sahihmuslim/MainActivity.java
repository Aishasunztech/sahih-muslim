package com.sunztech.sahihmuslim;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
;
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


      /*   MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }

        });*/

        MobileAds.initialize(this,
                getString(R.string.app_id));
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.d("", "onAdFailedToLoad: " + errorCode);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });
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
                if (isChapters) {
                    Intent intent = new Intent(MainActivity.this, BookDetailsActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, BookMarkActivity.class);
                    startActivity(intent);
                }
            }
        });

        new Handler().postDelayed(() -> {
            Intent mainIntent = new Intent(MainActivity.this, StaticAddActivity.class);
            startActivity(mainIntent);
        }, 500);

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
        if (numberOfClicks % counter == 0) {
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
            isChapters = false;
            numberOfClicks++;

        } else {
            Intent intent = new Intent(this, BookMarkActivity.class);
            startActivity(intent);
            numberOfClicks++;
        }


    }

    public void gotoHadith(View view) {
        if (numberOfClicks % counter == 0) {
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
            isChapters = true;
            numberOfClicks++;

        } else {
            Intent intent = new Intent(this, BookDetailsActivity.class);
            startActivity(intent);
            numberOfClicks++;
        }

    }

    public void shareBook(View view) {
        MyUtils.shareApp("https://play.google.com/store/apps/details?id=" + this.getPackageName(), this);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("Alert!");
        dialog.setMessage("Are you sure you want to close this app?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                MainActivity.super.onBackPressed();
            }
        }).setNegativeButton("No ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final AlertDialog alert = dialog.create();
        alert.show();

    }
}
