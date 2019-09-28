package com.sunztech.sahihmuslim.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.sunztech.sahihmuslim.Models.HadithItem;
import com.sunztech.sahihmuslim.R;

public class ReferenceDialog extends Dialog {

    HadithItem hadeesItem;
    private TextView tvBaab,tv_Volume,tvBook,tvStatus,tvTakhreej,tvWazahat,tvStatusRef,btnOk;

    public ReferenceDialog(@NonNull Context context,HadithItem hadithItem, int themeResId) {
        super(context, themeResId);
        this.hadeesItem = hadithItem;
    }

//    public ReferenceDialog(Context context, HadithItem hadeesItem, int width, int height)
//    {
//        super(context);
//        this.context = context;
//        this.hadeesItem = hadeesItem;
//        this.width = width;
//        this.height = height;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reference_dialog);

        tvBaab = findViewById(R.id.tv_ref_baab);
        tv_Volume = findViewById(R.id.tv_ref_volume);
        tvBook = findViewById(R.id.tv_ref_book);
        tvStatus = findViewById(R.id.tv_ref_status);
        tvStatusRef = findViewById(R.id.tv_ref_statusRef);
        tvTakhreej = findViewById(R.id.tv_ref_takhreej);
        tvWazahat = findViewById(R.id.tv_ref_wazahat);
        btnOk = findViewById(R.id.dialog_Ok);



        tvBaab.setText(hadeesItem.getBaab());
        if(!hadeesItem.getVolume().contains(""))
        {
            String[] bookAndVolume = hadeesItem.getVolume().split("-");
            tv_Volume.setText(bookAndVolume[1]);
            tvBook.setText(bookAndVolume[0]);
        }
        else{
            tv_Volume.setText("");
        }

        tvStatusRef.setText(hadeesItem.getStatus_Ref());
        tvStatus.setText(hadeesItem.getStatus());
        tvTakhreej.setText(hadeesItem.getTakhreej());
        tvWazahat.setText(hadeesItem.getWazahat());

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReferenceDialog.this.dismiss();
            }
        });
    }

}
