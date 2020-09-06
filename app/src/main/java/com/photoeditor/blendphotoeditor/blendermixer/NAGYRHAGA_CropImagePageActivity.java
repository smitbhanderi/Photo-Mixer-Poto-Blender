package com.photoeditor.blendphotoeditor.blendermixer;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.theartofdev.edmodo.cropper.CropImageView;

/*import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;*/

public class NAGYRHAGA_CropImagePageActivity extends AppCompatActivity {
    ImageView back;
    CropImageView cropImageView;
    int h;
    ImageView saveImage;
    int w;
    AdView mAdView;
    private final String TAG = NAGYRHAGA_CropImagePageActivity.class.getSimpleName();
    private InterstitialAd mInterstitialAd;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.nagyrhaga_activity_crop_image_page);
        getWindow().addFlags(1024);
        this.w = getResources().getDisplayMetrics().widthPixels;
        this.h = getResources().getDisplayMetrics().heightPixels;
        Init();
        Clicks();
        /*finter();*/
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString (R.string.inter_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        setDesign();
        this.cropImageView.setImageUriAsync(NAGYRHAGA_FirstEditPageActivity.selectedUri);
    }

    private void Clicks() {
        this.back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    NAGYRHAGA_CropImagePageActivity.this.onBackPressed();
                }

            }
        });
        this.saveImage.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NAGYRHAGA_FirstEditPageActivity.bitmap = NAGYRHAGA_CropImagePageActivity.this.cropImageView.getCroppedImage();
                NAGYRHAGA_CropImagePageActivity.this.setResult(-1);
                NAGYRHAGA_CropImagePageActivity.this.finish();
            }
        });
    }

    private void Init() {
        this.saveImage = (ImageView) findViewById(R.id.saveImage);
        this.back = (ImageView) findViewById(R.id.back);
        this.cropImageView = (CropImageView) findViewById(R.id.cropImageView);
        this.cropImageView.setAspectRatio(1, 1);
    }

    private void setDesign() {
        LayoutParams layoutParams = new LayoutParams((this.w * 90) / 1080, (this.w * 90) / 1080);
        layoutParams.addRule(13);
        this.back.setLayoutParams(layoutParams);
        this.saveImage.setLayoutParams(layoutParams);
        this.cropImageView.setLayoutParams(new LayoutParams((this.w * 1080) / 1080, (this.w * 1080) / 1080));
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
