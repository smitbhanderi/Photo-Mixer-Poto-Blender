package com.photoeditor.blendphotoeditor.blendermixer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.bumptech.glide.Glide;
import com.photoeditor.blendphotoeditor.blendermixer.NAGYRHAGA_MyCreationListPageActivity;
import com.photoeditor.blendphotoeditor.blendermixer.R;

import java.util.ArrayList;

/*import com.naygrh.multiplephotoblendermixerphotoeditor.Activity.NAGYRHAGA_MyCreationListPageActivity;
import com.naygrh.multiplephotoblendermixerphotoeditor.R;*/

public class NAGYRHAGA_MyCreationAdapter extends BaseAdapter {
    private ArrayList<String> image;
    LayoutInflater inflater = null;
    /* access modifiers changed from: private */
    public Context mContext;

    public long getItemId(int i) {
        return (long) i;
    }

    public NAGYRHAGA_MyCreationAdapter(Context context, ArrayList<String> arrayList) {
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
            view = this.inflater.inflate(R.layout.nagyrhaga_mywork_adapter, null);
        }
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.mainLay);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.video_thumb_bg);
        imageView.setScaleType(ScaleType.CENTER_CROP);
        int i2 = this.mContext.getResources().getDisplayMetrics().widthPixels;
        LayoutParams layoutParams = new LayoutParams(i2, -2);
        int i3 = (i2 * 50) / 1080;
        if (i == this.image.size() - 1) {
            layoutParams.setMargins(0, i3, 0, i3);
        } else {
            layoutParams.setMargins(0, i3, 0, 0);
        }
        relativeLayout.setLayoutParams(layoutParams);
        int i4 = (i2 * 508) / 1080;
        LayoutParams layoutParams2 = new LayoutParams(i4, i4);
        layoutParams2.addRule(13);
        imageView2.setLayoutParams(layoutParams2);
        int i5 = (i2 * 499) / 1080;
        LayoutParams layoutParams3 = new LayoutParams(i5, i5);
        layoutParams3.addRule(13);
        imageView.setLayoutParams(layoutParams3);
        Glide.with(this.mContext).load((String) this.image.get(i)).crossFade().error((int) R.mipmap.ic_launcher).into(imageView);
        view.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ((NAGYRHAGA_MyCreationListPageActivity) NAGYRHAGA_MyCreationAdapter.this.mContext).preview(i);
            }
        });
        return view;
    }
}
