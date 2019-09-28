package com.sunztech.sahihmuslim.Adapters;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.sunztech.sahihmuslim.Fragments.ChapterFragment;
import com.sunztech.sahihmuslim.Fragments.SearchHadithFragment;

public class MainPagerAdapter extends FragmentStatePagerAdapter {
    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                ChapterFragment fragment = new ChapterFragment();
                return fragment;
            case 1:
                SearchHadithFragment searchHadithFragment = new SearchHadithFragment();
                return searchHadithFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return "Chapter";
            case 1:
                return "Search";
                default:
                    return "Chapter";
        }
    }
}
