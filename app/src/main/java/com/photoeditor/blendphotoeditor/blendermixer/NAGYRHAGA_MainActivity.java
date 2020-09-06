package com.photoeditor.blendphotoeditor.blendermixer;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.VmPolicy.Builder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

/*import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAdListener;*/

public class NAGYRHAGA_MainActivity extends AppCompatActivity {
    ImageView MyCreation;
    int REQUEST_CODE_PERMISSION = 100;
    int h;
    AdView mAdView;
    private InterstitialAd mInterstitialAd;
    /*ImageView more;*/
    private final String TAG = NAGYRHAGA_MainActivity.class.getSimpleName();
    private com.facebook.ads.InterstitialAd interstitialAd;
    ImageView more1;
    LinearLayout one;
    String[] permission = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};
    ImageView policy;
    ImageView rate;
    LinearLayout relativeLayout;
    ImageView share;
    ImageView start;
    LinearLayout two;
    int w;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.nagyrhaga_activity_main);
        getWindow().addFlags(1024);
        this.w = getResources().getDisplayMetrics().widthPixels;
        this.h = getResources().getDisplayMetrics().heightPixels;
        AllowPermission();
        init();
        clicks();

        /*finter();*/
       /* setDesign();*/

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString (R.string.inter_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        this.share.setOnClickListener(new View.OnClickListener () {
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                StringBuilder sb = new StringBuilder();
                sb.append("Try this awesome application ");
                sb.append(NAGYRHAGA_MainActivity.this.getResources().getString(R.string.app_name));
                sb.append(" .click the link to download now http://play.google.com/store/apps/details?id=");
                sb.append(NAGYRHAGA_MainActivity.this.getPackageName());
                intent.putExtra("android.intent.extra.TEXT", sb.toString());
                NAGYRHAGA_MainActivity.this.startActivity(Intent.createChooser(intent, "Share using"));
            }
        });

       /* this.more.setOnClickListener(new View.OnClickListener () {
            public void onClick(View view) {
                NAGYRHAGA_MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(NAGYRHAGA_MainActivity.this.getResources().getString(R.string.more))));

                *//*NAGYRHAGA_MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(NAGYRHAGA_MainActivity.this.getResources().getString(R.string.more))));*//*
            }
        });

        this.rate.setOnClickListener(new View.OnClickListener () {
            public void onClick(View view) {
                StringBuilder sb = new StringBuilder();
                sb.append("market://details?id=");
                sb.append(NAGYRHAGA_MainActivity.this.getPackageName());
                NAGYRHAGA_MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sb.toString())));
            }
        });*/


    }

/*
    private void setDesign() {
        LayoutParams layoutParams = new LayoutParams((this.w * 400) / 1080, (this.w * 125) / 1080);
        layoutParams.gravity = 17;
        this.start.setLayoutParams(layoutParams);
        LayoutParams layoutParams2 = new LayoutParams((this.w * 400) / 1080, (this.w * 125) / 1080);
        layoutParams2.gravity = 17;
        layoutParams2.setMargins(0, (this.w * 50) / 1080, 0, 0);
        this.MyCreation.setLayoutParams(layoutParams2);
        LayoutParams layoutParams3 = new LayoutParams((this.w * 90) / 1080, (this.w * 90) / 1080);
        layoutParams3.gravity = 80;
        layoutParams3.setMargins((this.w * 30) / 1080, 0, 0, (this.w * 30) / 1080);
        this.more.setLayoutParams(layoutParams3);
       */
/* this.relativeLayout = (LinearLayout) findViewById(R.id.mainlayout);*//*

        RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(this.w * 1080, this.h * 1920);
        layoutParams4.addRule(13);
        this.relativeLayout.setLayoutParams(layoutParams4);
        LayoutParams layoutParams5 = new LayoutParams((this.w * 1080) / 1080, (this.w * 237) / 1080);
        layoutParams5.gravity = 80;
        this.two.setLayoutParams(layoutParams5);
        RelativeLayout.LayoutParams layoutParams6 = new RelativeLayout.LayoutParams((this.w * 227) / 1080, (this.h * 152) / 1080);
        this.more1.setLayoutParams(layoutParams6);
        this.rate.setLayoutParams(layoutParams6);
        this.share.setLayoutParams(layoutParams6);
        this.policy.setLayoutParams(layoutParams6);
    }
*/

    private void clicks() {
        this.start.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent=new Intent (NAGYRHAGA_MainActivity.this, NAGYRHAGA_FirstEditPageActivity.class);
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    startActivity (intent);
                }

/*
                NAGYRHAGA_MainActivity.this.startActivity(new Intent(NAGYRHAGA_MainActivity.this, NAGYRHAGA_FirstEditPageActivity.class));
*/
            }
        });
        this.MyCreation.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent=new Intent (NAGYRHAGA_MainActivity.this, NAGYRHAGA_MyCreationListPageActivity.class);
/*
                NAGYRHAGA_MainActivity.this.startActivity(new Intent(NAGYRHAGA_MainActivity.this, NAGYRHAGA_MyCreationListPageActivity.class));
*/
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    startActivity (intent);
                }
            }
        });
       /* this.more.setOnClickListener(new OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(View view) {
                NAGYRHAGA_MainActivity.this.two.setVisibility(0);
                NAGYRHAGA_MainActivity.this.one.setVisibility(8);
            }
        });
        this.relativeLayout.setOnClickListener(new OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(View view) {
                NAGYRHAGA_MainActivity.this.one.setVisibility(0);
                NAGYRHAGA_MainActivity.this.two.setVisibility(8);
            }
        });
        this.more1.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    NAGYRHAGA_MainActivity nAGYRHAGA_MainActivity = NAGYRHAGA_MainActivity.this;
                    StringBuilder sb = new StringBuilder();
                    sb.append("market://details?id=");
                    sb.append(NAGYRHAGA_MainActivity.this.getPackageName());
                    nAGYRHAGA_MainActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sb.toString())));
                } catch (ActivityNotFoundException unused) {
                    NAGYRHAGA_MainActivity nAGYRHAGA_MainActivity2 = NAGYRHAGA_MainActivity.this;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("https://play.google.com/store/apps/details?id=");
                    sb2.append(NAGYRHAGA_MainActivity.this.getPackageName());
                    nAGYRHAGA_MainActivity2.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sb2.toString())));
                }
            }
        });
        this.rate.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    NAGYRHAGA_MainActivity nAGYRHAGA_MainActivity = NAGYRHAGA_MainActivity.this;
                    StringBuilder sb = new StringBuilder();
                    sb.append("market://details?id=");
                    sb.append(NAGYRHAGA_MainActivity.this.getPackageName());
                    nAGYRHAGA_MainActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sb.toString())));
                } catch (ActivityNotFoundException unused) {
                    NAGYRHAGA_MainActivity nAGYRHAGA_MainActivity2 = NAGYRHAGA_MainActivity.this;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("https://play.google.com/store/apps/details?id=");
                    sb2.append(NAGYRHAGA_MainActivity.this.getPackageName());
                    nAGYRHAGA_MainActivity2.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sb2.toString())));
                }
            }
        });
        this.share.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                StringBuilder sb = new StringBuilder();
                sb.append("https://play.google.com/store/apps/details?id=");
                sb.append(NAGYRHAGA_MainActivity.this.getPackageName());
                String sb2 = sb.toString();
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.putExtra("android.intent.extra.TEXT", sb2);
                NAGYRHAGA_MainActivity.this.startActivity(Intent.createChooser(intent, "Share via"));
            }
        });
        this.policy.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NAGYRHAGA_MainActivity.this.startActivity(new Intent(NAGYRHAGA_MainActivity.this, NAGYRHAGA_Policy.class));
            }
        });
        this.more1.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NAGYRHAGA_MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(NAGYRHAGA_MainActivity.this.getResources().getString(R.string.more))));
            }
        });*/
    }

    private void init() {
        this.start = (ImageView) findViewById(R.id.start);
        this.MyCreation = (ImageView) findViewById(R.id.MyCreation);
        this.share=(ImageView)findViewById (R.id.share);/*
        this.more=(ImageView)findViewById (R.id.more);
        this.rate=(ImageView)findViewById (R.id.rate);*/

       /* this.one = (LinearLayout) findViewById(R.id.one);
        this.two = (LinearLayout) findViewById(R.id.two);
        this.relativeLayout = (LinearLayout) findViewById(R.id.mainlayout);
        this.share = (ImageView) findViewById(R.id.share);*/

    }

    private void AllowPermission() {
        try {
            Builder builder = new Builder();
            StrictMode.setVmPolicy(builder.build());
            if (VERSION.SDK_INT >= 18) {
                builder.detectFileUriExposure();
            }
        } catch (Exception e) {
            e.toString();
        }
        if (VERSION.SDK_INT < 23) {
            return;
        }
        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") != 0 || ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            requestPermissions(this.permission, this.REQUEST_CODE_PERMISSION);
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        AllowPermission();
    }

/*
    public void finter(){
        AudienceNetworkAds.initialize(this);
        interstitialAd = new com.facebook.ads.InterstitialAd (this, getString (R.string.f_inter));
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
