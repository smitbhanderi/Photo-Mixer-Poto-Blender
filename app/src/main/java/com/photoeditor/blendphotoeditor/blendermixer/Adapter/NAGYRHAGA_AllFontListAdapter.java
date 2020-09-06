package com.photoeditor.blendphotoeditor.blendermixer.Adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.photoeditor.blendphotoeditor.blendermixer.NAGYRHAGA_AddTextPageActivity;
import com.photoeditor.blendphotoeditor.blendermixer.R;
/*import com.naygrh.multiplephotoblendermixerphotoeditor.Activity.NAGYRHAGA_AddTextPageActivity;
import com.naygrh.multiplephotoblendermixerphotoeditor.R;*/

public class NAGYRHAGA_AllFontListAdapter extends BaseAdapter {
    Typeface font;
    /* access modifiers changed from: private */
    public String[] image;
    LayoutInflater inflater = null;
    /* access modifiers changed from: private */
    public Context mContext;

    public long getItemId(int i) {
        return (long) i;
    }

    public NAGYRHAGA_AllFontListAdapter(Context context, String[] strArr) {
        this.mContext = context;
        this.image = strArr;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.image = strArr;
    }

    public int getCount() {
        return this.image.length;
    }

    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.inflater.inflate(R.layout.nagyrhaga_my_font_name, null);
        }
        TextView textView = (TextView) view.findViewById(R.id.myList);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.getText);
        textView.setText("Select Your Font Style");
        try {
            AssetManager assets = this.mContext.getAssets();
            StringBuilder sb = new StringBuilder();
            sb.append("fontstyle/");
            sb.append(this.image[i]);
            this.font = Typeface.createFromAsset(assets, sb.toString());
            textView.setTypeface(this.font);
        } catch (Exception e) {
            e.toString();
        }
        int i2 = this.mContext.getResources().getDisplayMetrics().widthPixels;
        int i3 = this.mContext.getResources().getDisplayMetrics().heightPixels;
        LayoutParams layoutParams = new LayoutParams((i2 * 845) / 1080, (i2 * 120) / 1080);
        int i4 = (i2 * 20) / 1080;
        layoutParams.setMargins(i4, (i2 * 40) / 1080, i4, (i2 * 5) / 1080);
        linearLayout.setLayoutParams(layoutParams);
        view.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NAGYRHAGA_AddTextPageActivity nAGYRHAGA_AddTextPageActivity = (NAGYRHAGA_AddTextPageActivity) NAGYRHAGA_AllFontListAdapter.this.mContext;
                StringBuilder sb = new StringBuilder();
                sb.append("fontstyle/");
                sb.append(NAGYRHAGA_AllFontListAdapter.this.image[i]);
                nAGYRHAGA_AddTextPageActivity.output(sb.toString());
            }
        });
        return view;
    }
}
