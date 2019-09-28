package com.sunztech.sahihmuslim.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.sunztech.sahihmuslim.Models.BookChapter;
import com.sunztech.sahihmuslim.ChapterDetailsActivity;
import com.sunztech.sahihmuslim.R;
import com.sunztech.sahihmuslim.Utilities.AppConstants;

import java.util.ArrayList;

import static com.sunztech.sahihmuslim.Utilities.MyUtils.setTypeface;

public class ChaptersAdapter extends RecyclerView.Adapter<ChaptersAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<BookChapter> chapters;
    private ChaptersClickedListener listener;

    public ChaptersAdapter(Fragment fragment, ArrayList<BookChapter> chapters) {
        this.context = fragment.getActivity();
        this.chapters = chapters;
        listener = (ChaptersClickedListener) fragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_chapter_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BookChapter chapter = chapters.get(position);
        holder.tv_eng.setText(chapter.getIn_eng());
        holder.tv_urdu.setText(chapter.getIn_urdu());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                listener.chapterClicked(chapter.getChapterId());
            }
        });

        setTypeface(context,null,holder.tv_urdu,holder.tv_eng);
    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_urdu,tv_eng;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_eng = itemView.findViewById(R.id.book_chap_eng);
            tv_urdu = itemView.findViewById(R.id.book_chap_urdu);
        }
    }

    public interface ChaptersClickedListener{
        void chapterClicked(int chapterId);
    }
}
