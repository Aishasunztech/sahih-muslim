package com.sunztech.sahihmuslim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.tabs.TabLayout;
import com.sunztech.sahihmuslim.Adapters.MainPagerAdapter;
import com.sunztech.sahihmuslim.Utilities.MyUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sunztech.sahihmuslim.Utilities.MyUtils.changeTabFont;
import static com.sunztech.sahihmuslim.Utilities.MyUtils.hideKeyboard;
import static com.sunztech.sahihmuslim.Utilities.MyUtils.hideKeyboard;

public class BookDetailsActivity extends AppCompatActivity {

    @BindView(R.id.book_toolbar)
    Toolbar toolbar;
    @BindView(R.id.book_detail_tab)
    TabLayout tabLayout;
    @BindView(R.id.book_detail_pager)
    ViewPager viewPager;
    @BindView(R.id.adView)
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MyUtils.changeToolbarFont(toolbar,this);

        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        changeTabFont(tabLayout,this);

         MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_DOWN)
        {
            View view = getCurrentFocus();

            if((view instanceof EditText))
            {
                Rect outRect = new Rect();
                view.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)ev.getRawX(), (int)ev.getRawY())) {
                    view.clearFocus();
                    hideKeyboard(this);
                }
            }


//            if(view.getId() != R.id.et_marketSearch)
//            {
//                et_market_search.setFocusable(false);
//                hideKeyboard(SecureMarketActivity.this);
//            }
        }
        return super.dispatchTouchEvent(ev);
    }


}
