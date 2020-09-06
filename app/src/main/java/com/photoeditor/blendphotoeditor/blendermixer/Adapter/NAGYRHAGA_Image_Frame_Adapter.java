package com.photoeditor.blendphotoeditor.blendermixer.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.photoeditor.blendphotoeditor.blendermixer.NAGYRHAGA_FirstEditPageActivity;
import com.photoeditor.blendphotoeditor.blendermixer.R;
/*import com.naygrh.multiplephotoblendermixerphotoeditor.Activity.NAGYRHAGA_FirstEditPageActivity;
import com.naygrh.multiplephotoblendermixerphotoeditor.R;*/

public class NAGYRHAGA_Image_Frame_Adapter extends Adapter<NAGYRHAGA_Image_Frame_Adapter.MyViewHolder> {
    String[] effect;
    Context mContext;
    int w;

    public static class MyViewHolder extends ViewHolder {
        public RelativeLayout getMain;
        public ImageView img;

        public MyViewHolder(View view) {
            super(view);
            this.img = (ImageView) view.findViewById(R.id.img);
            this.getMain = (RelativeLayout) view.findViewById(R.id.getMain);
        }
    }

    public NAGYRHAGA_Image_Frame_Adapter(Context context, String[] strArr) {
        this.mContext = context;
        this.effect = strArr;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.nagyrhaga_image_filter_adpter, viewGroup, false));
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, final int i) {
        this.w = this.mContext.getResources().getDisplayMetrics().widthPixels;
        RequestManager with = Glide.with(this.mContext);
        StringBuilder sb = new StringBuilder();
        sb.append("file:///android_asset/All Bag/");
        sb.append(this.effect[i]);
        with.load(Uri.parse(sb.toString())).into(myViewHolder.img);
        LayoutParams layoutParams = new LayoutParams((this.w * 167) / 1080, (this.w * 167) / 1080);
        layoutParams.addRule(13);
        layoutParams.setMargins((this.w * 10) / 1080, (this.w * 20) / 1080, (this.w * 10) / 1080, (this.w * 20) / 1080);
        myViewHolder.getMain.setLayoutParams(layoutParams);
        LayoutParams layoutParams2 = new LayoutParams((this.w * 159) / 1080, (this.w * 159) / 1080);
        layoutParams2.addRule(13);
        myViewHolder.img.setLayoutParams(layoutParams2);
        if (this.effect[i].equals("01.png")) {
            myViewHolder.getMain.setBackgroundColor(Color.parseColor("#20223B"));
        }
        myViewHolder.itemView.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View view) {
                ((NAGYRHAGA_FirstEditPageActivity) NAGYRHAGA_Image_Frame_Adapter.this.mContext).setFrame(i);
            }
        });
    }

    public int getItemCount() {
        return this.effect.length;
    }
}
