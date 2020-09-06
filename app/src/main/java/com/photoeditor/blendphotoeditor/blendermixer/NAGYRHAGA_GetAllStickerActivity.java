package com.photoeditor.blendphotoeditor.blendermixer;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.photoeditor.blendphotoeditor.blendermixer.Adapter.NAGYRHAGA_Image_Stricker_Adapter;

import java.util.ArrayList;
import java.util.Arrays;

/*import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;*/

public class NAGYRHAGA_GetAllStickerActivity extends AppCompatActivity {
    public static ArrayList<String> photo = new ArrayList<>();
    String[] AllStricker;
    NAGYRHAGA_Image_Stricker_Adapter NAGYRHAGAImage_stricker_adapter;
    GridView StrickerGrid;
    ImageView back;
    Bitmap finalBitmap;
    int h;
    private final String TAG = NAGYRHAGA_GetAllStickerActivity.class.getSimpleName();
    private InterstitialAd mInterstitialAd;
    int w;
    AdView mAdView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.nagyrhaga_activity_get_all_sticker);
        getWindow().addFlags(1024);
        this.w = getResources().getDisplayMetrics().widthPixels;
        this.h = getResources().getDisplayMetrics().heightPixels;
        init();
        clicks();
        /*finter();*/

        setDesign();
        photo.clear();
        getAllStricker();

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString (R.string.inter_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

    }

    private void getAllStricker() {
        try {
            this.AllStricker = getAssets().list("All Sticker");
            this.NAGYRHAGAImage_stricker_adapter = new NAGYRHAGA_Image_Stricker_Adapter(this, new ArrayList(Arrays.asList(this.AllStricker)));
            this.StrickerGrid.setAdapter(this.NAGYRHAGAImage_stricker_adapter);
        } catch (Exception unused) {
        }
    }

    public void setStricker(int i) {
        try {
            AssetManager assets = getAssets();
            StringBuilder sb = new StringBuilder();
            sb.append("All Sticker/");
            sb.append(this.AllStricker[i]);
            NAGYRHAGA_SecondEditPageActivity.myStricker = BitmapFactory.decodeStream(assets.open(sb.toString()));
            setResult(-1);
            finish();
        } catch (Exception unused) {
        }
    }

    private void setDesign() {
        LayoutParams layoutParams = new LayoutParams((this.w * 90) / 1080, (this.w * 90) / 1080);
        layoutParams.addRule(13);
        this.back.setLayoutParams(layoutParams);
    }

    private void clicks() {
        this.back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NAGYRHAGA_GetAllStickerActivity.this.finish();
            }
        });
    }

    private void init() {
        this.back = (ImageView) findViewById(R.id.back);
        this.StrickerGrid = (GridView) findViewById(R.id.StrickerGrid);
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
