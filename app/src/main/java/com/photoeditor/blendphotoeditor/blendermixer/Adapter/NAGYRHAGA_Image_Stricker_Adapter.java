package com.photoeditor.blendphotoeditor.blendermixer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.photoeditor.blendphotoeditor.blendermixer.NAGYRHAGA_GetAllStickerActivity;
import com.photoeditor.blendphotoeditor.blendermixer.R;

import java.util.ArrayList;

/*import com.naygrh.multiplephotoblendermixerphotoeditor.Activity.NAGYRHAGA_GetAllStickerActivity;
import com.naygrh.multiplephotoblendermixerphotoeditor.R;*/

public class NAGYRHAGA_Image_Stricker_Adapter extends BaseAdapter {
    int h;
    private ArrayList<String> image;
    LayoutInflater inflater = null;
    /* access modifiers changed from: private */
    public Context mContext;
    int w;

    public long getItemId(int i) {
        return (long) i;
    }

    public NAGYRHAGA_Image_Stricker_Adapter(Context context, ArrayList<String> arrayList) {
        this.mContext = context;
        this.image = arrayList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.image = arrayList;
    }

    public int getCount() {
        return this.image.size();
    }

    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.inflater.inflate(R.layout.nagyrhaga_stricker_image, null);
        }
        RoundedImageView roundedImageView = (RoundedImageView) view.findViewById(R.id.icon);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.myStricker);
        this.w = this.mContext.getResources().getDisplayMetrics().widthPixels;
        this.h = this.mContext.getResources().getDisplayMetrics().heightPixels;
        StringBuilder sb = new StringBuilder();
        sb.append("file:///android_asset/All Sticker/");
        sb.append((String) this.image.get(i));
        Glide.with(this.mContext).load(sb.toString()).into(roundedImageView);
        LayoutParams layoutParams = new LayoutParams((this.w * 226) / 1080, (this.w * 226) / 1080);
        layoutParams.setMargins((this.w * 20) / 1080, (this.w * 20) / 1080, (this.w * 12) / 1080, (this.w * 20) / 1080);
        relativeLayout.setLayoutParams(layoutParams);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams((this.w * 220) / 1080, (this.w * 220) / 1080);
        layoutParams2.addRule(13);
        roundedImageView.setLayoutParams(layoutParams2);
       /* roundedImageView.setBackgroundResource(R.drawable.main_bg);*/
        roundedImageView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ((NAGYRHAGA_GetAllStickerActivity) NAGYRHAGA_Image_Stricker_Adapter.this.mContext).setStricker(i);
            }
        });
        return view;
    }
}
