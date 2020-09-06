package com.photoeditor.blendphotoeditor.blendermixer.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.photoeditor.blendphotoeditor.blendermixer.NAGYRHAGA_SecondEditPageActivity;
import com.photoeditor.blendphotoeditor.blendermixer.R;

/*import android.support.annotation.NonNull;*/
/*import com.naygrh.multiplephotoblendermixerphotoeditor.Activity.NAGYRHAGA_SecondEditPageActivity;
import com.naygrh.multiplephotoblendermixerphotoeditor.R;*/

public class NAGYRHAGA_Image_Overlay_Adapter extends Adapter<NAGYRHAGA_Image_Overlay_Adapter.MyViewHolder> {
    Bitmap bitmap;
    String[] effect;
    Context mContext;
    int w;

    public static class MyViewHolder extends ViewHolder {
        public RelativeLayout getMain;
        public ImageView img;
        public ImageView newimg;

        public MyViewHolder(View view) {
            super(view);
            this.img = (ImageView) view.findViewById(R.id.img);
            this.newimg = (ImageView) view.findViewById(R.id.newimg);
            this.getMain = (RelativeLayout) view.findViewById(R.id.getMain);
        }
    }

    public NAGYRHAGA_Image_Overlay_Adapter(Context context, String[] strArr, Bitmap bitmap2) {
        this.mContext = context;
        this.effect = strArr;
        this.bitmap = bitmap2;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.nagyrhaga_image_frame_adpter, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        this.w = this.mContext.getResources().getDisplayMetrics().widthPixels;
        RequestManager with = Glide.with(this.mContext);
        StringBuilder sb = new StringBuilder();
        sb.append("file:///android_asset/All Over/");
        sb.append(this.effect[i]);
        with.load(Uri.parse(sb.toString())).into(myViewHolder.img);
        myViewHolder.newimg.setImageBitmap(this.bitmap);
        LayoutParams layoutParams = new LayoutParams((this.w * 167) / 1080, (this.w * 167) / 1080);
        layoutParams.addRule(13);
        layoutParams.setMargins((this.w * 10) / 1080, (this.w * 20) / 1080, (this.w * 10) / 1080, (this.w * 20) / 1080);
        myViewHolder.getMain.setLayoutParams(layoutParams);
        LayoutParams layoutParams2 = new LayoutParams((this.w * 159) / 1080, (this.w * 159) / 1080);
        layoutParams2.addRule(13);
        myViewHolder.newimg.setLayoutParams(layoutParams2);
        myViewHolder.img.setLayoutParams(layoutParams2);
        if (this.effect[i].equals("o0.png")) {
            myViewHolder.newimg.setImageResource(R.drawable.bottom_bg);
        }
        myViewHolder.itemView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ((NAGYRHAGA_SecondEditPageActivity) NAGYRHAGA_Image_Overlay_Adapter.this.mContext).setFrame(i);
            }
        });
    }

    public int getItemCount() {
        return this.effect.length;
    }
}
