package com.sunztech.sahihmuslim.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sunztech.sahihmuslim.HadithDetailsActivity;
import com.sunztech.sahihmuslim.Models.HadithItem;
import com.sunztech.sahihmuslim.R;
import com.sunztech.sahihmuslim.Utilities.AppConstants;
import com.sunztech.sahihmuslim.Utilities.MyUtils;

import java.util.ArrayList;

public class HadithListAdapter extends RecyclerView.Adapter<HadithListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<HadithItem> hadithItems;
    private boolean isSearchEgnlish;
    private HadithListClickedListener listener;


    public HadithListAdapter(Context context, ArrayList<HadithItem> hadithItems, boolean isSearchEgnlish) {
        this.context = context;
        this.hadithItems = hadithItems;
        this.isSearchEgnlish = isSearchEgnlish;
        listener = (HadithListClickedListener) context;

        AppConstants.CURRENT_HADITH_LIST.clear();
        AppConstants.CURRENT_HADITH_LIST = hadithItems;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chapter_hadith_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HadithItem hadithItem = hadithItems.get(position);
        holder.tv_number.setText(context.getResources().getString(R.string.book_name) + " " + hadithItem.getHadees_number() + "");
        MyUtils.setTypeface(context, null, null, holder.tv_number);

        if (isSearchEgnlish) {
            MyUtils.setTypeface(context, null, null, holder.tv_detail);
            holder.tv_detail.setText(hadithItem.getEnglish());
            holder.tv_detail.setGravity(Gravity.LEFT);

        } else {
            MyUtils.setTypeface(context, null, holder.tv_detail, null);
            holder.tv_detail.setText(hadithItem.getUrdu());
            holder.tv_detail.setGravity(Gravity.RIGHT);

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    listener.clickedHadit(position);
                } catch (Exception e) {
                }
//                listener.onItemClick(position,hadithItems);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hadithItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_number, tv_detail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_number = itemView.findViewById(R.id.hadithListHeading);
            tv_detail = itemView.findViewById(R.id.chap_hadith_Detail);
        }
    }

    public interface HadithListClickedListener{
        void clickedHadit(int position);
    }


}
