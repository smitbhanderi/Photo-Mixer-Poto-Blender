package com.photoeditor.blendphotoeditor.blendermixer;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;

/*import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;*/

public class NAGYRHAGA_MyCreationViewPageActivity extends AppCompatActivity {
    ImageView back;
    ImageView delete;
    int h;
    AdView mAdView;
    Intent intent;
    private final String TAG = NAGYRHAGA_MyCreationViewPageActivity.class.getSimpleName();
    private InterstitialAd mInterstitialAd;

    ImageView myCreationImage;
    ImageView share;
    int w;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.nagyrhaga_activity_my_creation_view_page);
        getWindow().addFlags(1024);
        this.w = getResources().getDisplayMetrics().widthPixels;
        this.h = getResources().getDisplayMetrics().heightPixels;
        init();
        clicks();
       /* finter();*/
        setDesign();

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString (R.string.inter_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        this.intent = getIntent();
        this.myCreationImage.setImageURI(Uri.parse(this.intent.getStringExtra("path")));
    }

    private void setDesign() {
        LayoutParams layoutParams = new LayoutParams((this.w * 90) / 1080, (this.w * 90) / 1080);
        layoutParams.addRule(13);
        this.back.setLayoutParams(layoutParams);
        LayoutParams layoutParams2 = new LayoutParams((this.w * 90) / 1080, (this.w * 90) / 1080);
        layoutParams2.addRule(13);
        this.delete.setLayoutParams(layoutParams2);
        this.share.setLayoutParams(layoutParams2);
        LayoutParams layoutParams3 = new LayoutParams(this.w * 1080, (this.w * 1080) / 1080);
        layoutParams3.setMargins(0, (this.w * 30) / 1080, 0, 0);
        this.myCreationImage.setLayoutParams(layoutParams3);
    }

    private void clicks() {
        this.back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    NAGYRHAGA_MyCreationViewPageActivity.this.onBackPressed();
                }

            }
        });
        this.share.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("image/*");
                File file = new File(NAGYRHAGA_MyCreationViewPageActivity.this.intent.getStringExtra("path"));
                StringBuilder sb = new StringBuilder();
                sb.append("file://");
                sb.append(file.getAbsolutePath());
                intent.putExtra("android.intent.extra.STREAM", Uri.parse(sb.toString()));

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();

                } else {
                    NAGYRHAGA_MyCreationViewPageActivity.this.startActivity(Intent.createChooser(intent, "Share Image"));
                }

            }
        });
        this.delete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    NAGYRHAGA_MyCreationViewPageActivity.this.dialogDesign();
                }

            }
        });
    }

    /* access modifiers changed from: private */
    public void dialogDesign() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.nagyrhaga_delete_dailog);
        ImageView imageView = (ImageView) dialog.findViewById(R.id.submit);
        ImageView imageView2 = (ImageView) dialog.findViewById(R.id.cancel);
        LayoutParams layoutParams = new LayoutParams((this.w * 280) / 1080, (this.w * 120) / 1080);
        layoutParams.addRule(13);
        imageView.setLayoutParams(layoutParams);
        imageView2.setLayoutParams(layoutParams);
        ((LinearLayout) dialog.findViewById(R.id.mainLay)).setLayoutParams(new LayoutParams((this.w * 900) / 1080, (this.w * 588) / 1080));
        imageView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
                File file = new File(NAGYRHAGA_MyCreationViewPageActivity.this.intent.getStringExtra("path"));
                if (file.delete()) {
                    MediaScannerConnection.scanFile(NAGYRHAGA_MyCreationViewPageActivity.this, new String[]{file.getAbsolutePath()}, new String[]{"image/*"}, null);
                    Toast.makeText(NAGYRHAGA_MyCreationViewPageActivity.this, "File Deleted Successfully...", Toast.LENGTH_SHORT).show();
                }
                NAGYRHAGA_MyCreationViewPageActivity.this.onBackPressed();
            }
        });
        imageView2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void init() {
        this.myCreationImage = (ImageView) findViewById(R.id.myCreationImage);
        this.back = (ImageView) findViewById(R.id.back);
        this.share = (ImageView) findViewById(R.id.share);
        this.delete = (ImageView) findViewById(R.id.delete);
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
