package com.sunztech.sahihmuslim.Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.sunztech.sahihmuslim.Adapters.ChaptersAdapter;
import com.sunztech.sahihmuslim.ChapterDetailsActivity;
import com.sunztech.sahihmuslim.Models.BookChapter;
import com.sunztech.sahihmuslim.BackgroundTasks.GetChaptersTask;
import com.sunztech.sahihmuslim.R;
import com.sunztech.sahihmuslim.Utilities.AppConstants;
import com.sunztech.sahihmuslim.Utilities.MyUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sunztech.sahihmuslim.MyApplication.numberOfClicks;
import static com.sunztech.sahihmuslim.Utilities.AppConstants.counter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChapterFragment extends Fragment implements GetChaptersTask.ChapterListener, ChaptersAdapter.ChaptersClickedListener {

    @BindView(R.id.chapter_list)
    RecyclerView rc;
    private Activity activity;

    private InterstitialAd mInterstitialAd;
    private int clickedChapter = 0;

    public ChapterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();

        mInterstitialAd = new InterstitialAd(activity);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_id));

        mInterstitialAd.setAdListener(new AdListener() {

            @Override
            public void onAdClosed() {
                sendToChapterDetaisl();
            }

            @Override
            public void onAdLoaded() {
                if(mInterstitialAd.isLoaded())
                {
                    mInterstitialAd.show();
                }
                else{
                    sendToChapterDetaisl();
                }
            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chapter, container, false);
        ButterKnife.bind(this, view);
        new GetChaptersTask(this).execute();
        return view;
    }

    @Override
    public void getChapters(ArrayList<BookChapter> chapters) {
        rc.setAdapter(new ChaptersAdapter(this,chapters));
        rc.setLayoutManager(new LinearLayoutManager(activity));
    }

    @Override
    public void onResume() {
        super.onResume();
        MyUtils.hideKeyboard(activity);

    }

    @Override
    public void chapterClicked(int chapterId) {
        clickedChapter = chapterId;
        if(numberOfClicks % counter == 0)
        {
            mInterstitialAd.loadAd(new AdRequest.Builder().build());

        }else{
            sendToChapterDetaisl();
        }
        numberOfClicks++;

    }

    private void sendToChapterDetaisl() {
        Intent intent = new Intent(activity, ChapterDetailsActivity.class);
        intent.putExtra(AppConstants.CHAPTER_DETAILS_SENDER,AppConstants.BOOK_DETAILS_ACTVIITY);
        intent.putExtra(AppConstants.CHAPTER_ID,clickedChapter);
        startActivity(intent);
    }
}
