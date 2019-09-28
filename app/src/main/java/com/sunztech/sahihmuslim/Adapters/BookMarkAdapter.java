package com.sunztech.sahihmuslim.Adapters;

import android.content.Context;
import android.content.Intent;
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

public class BookMarkAdapter extends RecyclerView.Adapter<BookMarkAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<HadithItem> hadithItems;
    private BookMarkListener listener;

    public BookMarkAdapter(Context context, ArrayList<HadithItem> hadithItems) {
        this.context = context;
        this.hadithItems = hadithItems;
        listener = (BookMarkListener) context;

        AppConstants.CURRENT_HADITH_LIST.clear();
        AppConstants.CURRENT_HADITH_LIST = hadithItems;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_mark_list_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HadithItem hadithItem = hadithItems.get(position);
        holder.tv_arab.setText(hadithItem.getArabic());
        holder.tv_urdu.setText(hadithItem.getUrdu());
        holder.tv_eng.setText(hadithItem.getEnglish());
        holder.tv_bookmark_num.setText(context.getResources().getString(R.string.sahih_bukhari) + " " + hadithItem.getHadees_number());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, HadithDetailsActivity.class);
//                intent.putExtra(AppConstants.CURRENT_HADITH,position);
//
////                intent.putParcelableArrayListExtra(AppConstants.HADITH_LIST,hadithItems);
//                context.startActivity(intent);
                listener.onBookMarkClick(position);
            }
        });
        MyUtils.setTypeface(context,holder.tv_arab,holder.tv_urdu,holder.tv_eng);
    }

    @Override
    public int getItemCount() {
        return hadithItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_arab,tv_urdu,tv_eng,tv_bookmark_num;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_arab = itemView.findViewById(R.id.tv_book_mark_arb);
            tv_urdu = itemView.findViewById(R.id.tv_book_mark_urdu);
            tv_eng = itemView.findViewById(R.id.tv_book_mark_eng);
            tv_bookmark_num = itemView.findViewById(R.id.bookMark_number);
        }
    }

    public interface BookMarkListener{
        void onBookMarkClick(int position);
    }
}
