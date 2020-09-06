package com.photoeditor.blendphotoeditor.blendermixer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.photoeditor.blendphotoeditor.blendermixer.Adapter.NAGYRHAGA_MyCreationAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/*import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;*/

public class NAGYRHAGA_MyCreationListPageActivity extends AppCompatActivity {
    public static ArrayList<String> photo = new ArrayList<>();
    NAGYRHAGA_MyCreationAdapter adapter;
    ImageView back;
    String folder_name;
    GridView gridview;
    int h;
    File[] listFile = null;
    int w;
    private final String TAG = NAGYRHAGA_MyCreationListPageActivity.class.getSimpleName();

    private InterstitialAd mInterstitialAd;
    AdView mAdView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.nagyrhaga_activity_my_creation_list_page);
        getWindow().addFlags(1024);
        StringBuilder sb = new StringBuilder();
        sb.append(getResources().getString(R.string.app_name));
        sb.append("/My Creation");
        this.folder_name = sb.toString();
        this.w = getResources().getDisplayMetrics().widthPixels;
        this.h = getResources().getDisplayMetrics().heightPixels;
        init();
        clicks();
        photo.clear();
        getFromSdcard();
        this.adapter = new NAGYRHAGA_MyCreationAdapter(this, photo);
        this.gridview.setAdapter(this.adapter);
        this.adapter.notifyDataSetChanged();
        setLayout();

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString (R.string.inter_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        /*finter();*/
    }

    private void setLayout() {
        LayoutParams layoutParams = new LayoutParams((this.w * 90) / 1080, (this.w * 90) / 1080);
        layoutParams.addRule(13);
        this.back.setLayoutParams(layoutParams);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        getFromSdcard();
        this.adapter.notifyDataSetChanged();
    }

    public void getFromSdcard() {
        photo.clear();
        File file = new File(Environment.getExternalStorageDirectory(), this.folder_name);
        if (file.isDirectory()) {
            this.listFile = file.listFiles();
            Arrays.sort(this.listFile, new Comparator() {
                public int compare(Object obj, Object obj2) {
                    File file = (File) obj;
                    File file2 = (File) obj2;
                    if (file.lastModified() > file2.lastModified()) {
                        return -1;
                    }
                    return file.lastModified() < file2.lastModified() ? 1 : 0;
                }
            });
            for (int i = 0; i < this.listFile.length; i++) {
                if (this.listFile[i].getName().contains("temp")) {
                    this.listFile[i].delete();
                } else if (this.listFile[i].isFile()) {
                    photo.add(this.listFile[i].getAbsolutePath());
                }
            }
        }
    }

    private void clicks() {
        this.back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NAGYRHAGA_MyCreationListPageActivity.this.onBackPressed();
            }
        });
    }

    private void init() {
        this.gridview = (GridView) findViewById(R.id.gridview);
        this.back = (ImageView) findViewById(R.id.back);
    }

    public void preview(int i) {
        Intent intent = new Intent(getApplicationContext(), NAGYRHAGA_MyCreationViewPageActivity.class);
        intent.putExtra("path", (String) photo.get(i));
        startActivity(intent);
    }
/*
    public void finter(){
        AudienceNetworkAds.initialize(this);
        interstitialAd = new InterstitialAd (this, getString (R.string.f_inter));
        // Set listeners for the Interstitial Ad
        interstitialAd.setAdListener(new InterstitialAdListener () {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!");
            }
        });

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd();
    }
*/

}
