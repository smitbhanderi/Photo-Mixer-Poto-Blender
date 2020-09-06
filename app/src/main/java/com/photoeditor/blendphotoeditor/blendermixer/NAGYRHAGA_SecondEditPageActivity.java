package com.photoeditor.blendphotoeditor.blendermixer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.photoeditor.blendphotoeditor.blendermixer.Adapter.NAGYRHAGA_Image_Filter_Adapter;
import com.photoeditor.blendphotoeditor.blendermixer.Adapter.NAGYRHAGA_Image_Overlay_Adapter;
import com.uvstudio.him.photofilterlibrary.PhotoFilter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/*import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;*/

/*import android.support.annotation.Nullable;
import android.support.media.ExifInterface;
import android.support.v7.widget.LinearLayoutManager;*/
/*import com.naygrh.multiplephotoblendermixerphotoeditor.NAGYRHAGA_StickerView.OperationListener;*/

public class NAGYRHAGA_SecondEditPageActivity extends AppCompatActivity {
    /* access modifiers changed from: private */
    public static Activity activity;
    public static Bitmap bm;
    Intent intent; private final String TAG = NAGYRHAGA_SecondEditPageActivity.class.getSimpleName();
    public static Bitmap myStricker;
    String[] AllOverLay;
    AdView mAdView;
    private InterstitialAd mInterstitialAd;
    int GETSTRICKER = 120;
    int GETTEXT = 140;
    NAGYRHAGA_Image_Filter_Adapter NAGYRHAGAImage_Filter_Adapter;
    NAGYRHAGA_Image_Overlay_Adapter NAGYRHAGAImage_Overlay_Adapter;
    String addone;
    ImageView back;
    ImageView filter;
    Bitmap finalBitmap;
    FrameLayout frame;
    String getVisibleData = "0";
    int h;
    Bitmap iBitmap;
    ImageView imgFrame;
    LayoutManager layoutManager;
    NAGYRHAGA_StickerView mCurrentView;
    /* access modifiers changed from: private */
    public ProgressDialog mProgressDialog;
    ArrayList<View> mViews = new ArrayList<>();
    RelativeLayout main;
    ArrayList<Bitmap> myFilter = new ArrayList<>();
    RelativeLayout one;
    ImageView overlay;
    PhotoFilter photoFilter;
    ImageView popText;
    RelativeLayout save;
    ImageView saveImage;
    ImageView secondImage;
    ImageView sticker;
    ImageView text;
    private RecyclerView thumbListView;
    RelativeLayout two;
    int w;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.nagyrhaga_activity_second_edit_page);
        getWindow().addFlags(1024);
        this.w = getResources().getDisplayMetrics().widthPixels;
        this.h = getResources().getDisplayMetrics().heightPixels;
        this.mProgressDialog = new ProgressDialog(this);
        this.mProgressDialog.setCancelable(false);
        this.mProgressDialog.setMessage("Saving...");
        init();
        clicks();
       /* finter();*/
        setDesign();
        loadImage();
        this.iBitmap = NAGYRHAGA_FirstEditPageActivity.bm;
        this.photoFilter = new PhotoFilter();
        activity = this;
        this.imgFrame.setImageBitmap(NAGYRHAGA_FirstEditPageActivity.bm);
        callOverLayData();
        getVisibility(this.getVisibleData);
        this.addone = "1";
        buttonVisibility(this.addone);


        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString (R.string.inter_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    /* access modifiers changed from: private */
    public void buttonVisibility(String str) {
        this.overlay.setImageResource(R.drawable.overlay_unpress);
        this.filter.setImageResource(R.drawable.filter_unpress);
        this.text.setImageResource(R.drawable.text_unpress);
        this.sticker.setImageResource(R.drawable.sticker_unpress);
        if (str.equals("1")) {
            this.overlay.setImageResource(R.drawable.overlay_press);
        } else if (str.equals(ExifInterface.GPS_MEASUREMENT_2D)) {
            this.filter.setImageResource(R.drawable.filter_press);
        } else if (str.equals(ExifInterface.GPS_MEASUREMENT_3D)) {
            this.text.setImageResource(R.drawable.text_press);
        } else if (str.equals("4")) {
            this.sticker.setImageResource(R.drawable.sticker_press);
        }
    }

    /* access modifiers changed from: private */
    @SuppressLint("WrongConstant")
    public void getVisibility(String str) {
        this.one.setVisibility(8);
        this.two.setVisibility(8);
        if (str.equals("0")) {
            this.one.setVisibility(0);
        } else if (str.equals("1")) {
            this.one.setVisibility(0);
        } else if (str.equals(ExifInterface.GPS_MEASUREMENT_2D)) {
            this.two.setVisibility(0);
        }
    }

    private void addStickerView() {
        final NAGYRHAGA_StickerView nAGYRHAGA_StickerView = new NAGYRHAGA_StickerView(this);
        nAGYRHAGA_StickerView.setBitmap(myStricker);
        nAGYRHAGA_StickerView.setOperationListener(new NAGYRHAGA_StickerView.OperationListener () {
            public void onDeleteClick() {
                NAGYRHAGA_SecondEditPageActivity.this.mViews.remove(nAGYRHAGA_StickerView);
                NAGYRHAGA_SecondEditPageActivity.this.frame.removeView(nAGYRHAGA_StickerView);
            }

            public void onEdit(NAGYRHAGA_StickerView nAGYRHAGA_StickerView) {
                NAGYRHAGA_SecondEditPageActivity.this.mCurrentView.setInEdit(false);
                NAGYRHAGA_SecondEditPageActivity.this.mCurrentView = nAGYRHAGA_StickerView;
                NAGYRHAGA_SecondEditPageActivity.this.mCurrentView.setInEdit(true);
            }

            public void onTop(NAGYRHAGA_StickerView nAGYRHAGA_StickerView) {
                int indexOf = NAGYRHAGA_SecondEditPageActivity.this.mViews.indexOf(nAGYRHAGA_StickerView);
                if (indexOf != NAGYRHAGA_SecondEditPageActivity.this.mViews.size() - 1) {
                    NAGYRHAGA_SecondEditPageActivity.this.mViews.add(NAGYRHAGA_SecondEditPageActivity.this.mViews.size(), (NAGYRHAGA_StickerView) NAGYRHAGA_SecondEditPageActivity.this.mViews.remove(indexOf));
                }
            }
        });
        this.frame.addView(nAGYRHAGA_StickerView, new LayoutParams(-1, -1));
        this.mViews.add(nAGYRHAGA_StickerView);
        setCurrentEdit(nAGYRHAGA_StickerView);
    }

    private void setCurrentEdit(NAGYRHAGA_StickerView nAGYRHAGA_StickerView) {
        if (this.mCurrentView != null) {
            this.mCurrentView.setInEdit(false);
        }
        this.mCurrentView = nAGYRHAGA_StickerView;
        nAGYRHAGA_StickerView.setInEdit(true);
    }

    /* access modifiers changed from: private */
    @SuppressLint("WrongConstant")
    public void callOverLayData() {
        try {
            this.AllOverLay = getAssets().list("All Over");
            this.NAGYRHAGAImage_Overlay_Adapter = new NAGYRHAGA_Image_Overlay_Adapter(this, this.AllOverLay, Bitmap.createScaledBitmap(NAGYRHAGA_FirstEditPageActivity.bm, (this.w * 200) / 1080, (this.w * 200) / 1080, true));
            this.thumbListView.setAdapter(this.NAGYRHAGAImage_Overlay_Adapter);
            this.layoutManager = new LinearLayoutManager (this, 0, false);
            this.thumbListView.setLayoutManager(this.layoutManager);
        } catch (Exception unused) {
        }
    }

    public void setFilter(int i) {
        switch (i) {
            case 0:
                this.imgFrame.setImageBitmap(NAGYRHAGA_FirstEditPageActivity.bm);
                return;
            case 1:
                this.imgFrame.setImageBitmap(this.photoFilter.one(this, this.iBitmap));
                return;
            case 2:
                this.imgFrame.setImageBitmap(this.photoFilter.two(this, this.iBitmap));
                return;
            case 3:
                this.imgFrame.setImageBitmap(this.photoFilter.three(this, this.iBitmap));
                return;
            case 4:
                this.imgFrame.setImageBitmap(this.photoFilter.four(this, this.iBitmap));
                return;
            case 5:
                this.imgFrame.setImageBitmap(this.photoFilter.five(this, this.iBitmap));
                return;
            case 6:
                this.imgFrame.setImageBitmap(this.photoFilter.six(this, this.iBitmap));
                return;
            case 7:
                this.imgFrame.setImageBitmap(this.photoFilter.seven(this, this.iBitmap));
                return;
            case 8:
                this.imgFrame.setImageBitmap(this.photoFilter.eight(this, this.iBitmap));
                return;
            case 9:
                this.imgFrame.setImageBitmap(this.photoFilter.nine(this, this.iBitmap));
                return;
            case 10:
                this.imgFrame.setImageBitmap(this.photoFilter.ten(this, this.iBitmap));
                return;
            case 11:
                this.imgFrame.setImageBitmap(this.photoFilter.eleven(this, this.iBitmap));
                return;
            case 12:
                this.imgFrame.setImageBitmap(this.photoFilter.twelve(this, this.iBitmap));
                return;
            case 13:
                this.imgFrame.setImageBitmap(this.photoFilter.thirteen(this, this.iBitmap));
                return;
            case 14:
                this.imgFrame.setImageBitmap(this.photoFilter.fourteen(this, this.iBitmap));
                return;
            case 15:
                this.imgFrame.setImageBitmap(this.photoFilter.fifteen(this, this.iBitmap));
                return;
            case 16:
                this.imgFrame.setImageBitmap(this.photoFilter.sixteen(this, this.iBitmap));
                return;
            default:
                return;
        }
    }

    @SuppressLint("WrongConstant")
    public void callFilterList(Bitmap bitmap) {
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, (this.w * 200) / 1080, (this.w * 200) / 1080, true);
        this.myFilter.clear();
        this.myFilter.add(createScaledBitmap);
        this.myFilter.add(this.photoFilter.one(this, createScaledBitmap));
        this.myFilter.add(this.photoFilter.two(this, createScaledBitmap));
        this.myFilter.add(this.photoFilter.three(this, createScaledBitmap));
        this.myFilter.add(this.photoFilter.four(this, createScaledBitmap));
        this.myFilter.add(this.photoFilter.five(this, createScaledBitmap));
        this.myFilter.add(this.photoFilter.six(this, createScaledBitmap));
        this.myFilter.add(this.photoFilter.seven(this, createScaledBitmap));
        this.myFilter.add(this.photoFilter.eight(this, createScaledBitmap));
        this.myFilter.add(this.photoFilter.nine(this, createScaledBitmap));
        this.myFilter.add(this.photoFilter.ten(this, createScaledBitmap));
        this.myFilter.add(this.photoFilter.eleven(this, createScaledBitmap));
        this.myFilter.add(this.photoFilter.twelve(this, createScaledBitmap));
        this.myFilter.add(this.photoFilter.thirteen(this, createScaledBitmap));
        this.myFilter.add(this.photoFilter.fourteen(this, createScaledBitmap));
        this.myFilter.add(this.photoFilter.fifteen(this, createScaledBitmap));
        this.myFilter.add(this.photoFilter.sixteen(this, createScaledBitmap));
        this.NAGYRHAGAImage_Filter_Adapter = new NAGYRHAGA_Image_Filter_Adapter(this, this.myFilter);
        this.thumbListView.setAdapter(this.NAGYRHAGAImage_Filter_Adapter);
        this.layoutManager = new LinearLayoutManager(this, 0, false);
        this.thumbListView.setLayoutManager(this.layoutManager);
    }

    private void setDesign() {
        LayoutParams layoutParams = new LayoutParams((this.w * 90) / 1080, (this.w * 90) / 1080);
        layoutParams.addRule(13);
        this.back.setLayoutParams(layoutParams);
        this.saveImage.setLayoutParams(layoutParams);
        LayoutParams layoutParams2 = new LayoutParams((this.w * 1080) / 1080, (this.w * 220) / 1080);
        layoutParams2.addRule(13);
        this.two.setLayoutParams(layoutParams2);
        this.one.setLayoutParams(layoutParams2);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.myone);
        LayoutParams layoutParams3 = new LayoutParams((this.w * 1080) / 1080, (this.w * 180) / 1080);
        layoutParams3.addRule(12);
        linearLayout.setLayoutParams(layoutParams3);
        LayoutParams layoutParams4 = new LayoutParams((this.w * 180) / 1080, (this.w * 180) / 1080);
        layoutParams4.addRule(13);
        this.overlay.setLayoutParams(layoutParams4);
        this.filter.setLayoutParams(layoutParams4);
        this.text.setLayoutParams(layoutParams4);
        this.sticker.setLayoutParams(layoutParams4);
    }

    private void clicks() {
        this.saveImage.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NAGYRHAGA_SecondEditPageActivity.this.mProgressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        if (NAGYRHAGA_SecondEditPageActivity.this.mCurrentView != null) {
                            NAGYRHAGA_SecondEditPageActivity.this.mCurrentView.setInEdit(false);
                        }
                        NAGYRHAGA_SecondEditPageActivity.this.save.setDrawingCacheEnabled(false);
                        NAGYRHAGA_SecondEditPageActivity.this.save.setDrawingCacheEnabled(true);
                        NAGYRHAGA_SecondEditPageActivity.this.save.buildDrawingCache();
                        NAGYRHAGA_SecondEditPageActivity.bm = NAGYRHAGA_SecondEditPageActivity.this.save.getDrawingCache();
                        Date time = Calendar.getInstance().getTime();
                        StringBuilder sb = new StringBuilder();
                        sb.append(time);
                        sb.append(".jpg");
                        String sb2 = sb.toString();
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(Environment.getExternalStorageDirectory());
                        sb3.append("/");
                        sb3.append(NAGYRHAGA_SecondEditPageActivity.this.getResources().getString(R.string.app_name));
                        sb3.append("/My Creation");
                        String sb4 = sb3.toString();
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append(sb4);
                        sb5.append(File.separator);
                        sb5.append(sb2);
                        String sb6 = sb5.toString();
                        File file = new File(sb4);
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        try {
                            FileOutputStream fileOutputStream = new FileOutputStream(sb6);
                            NAGYRHAGA_SecondEditPageActivity.bm.compress(CompressFormat.JPEG, 100, fileOutputStream);
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(NAGYRHAGA_SecondEditPageActivity.this, NAGYRHAGA_MyCreationViewPageActivity.class);
                        intent.putExtra("path", sb6);
                        NAGYRHAGA_SecondEditPageActivity.this.startActivity(intent);
                        NAGYRHAGA_SecondEditPageActivity.this.mProgressDialog.dismiss();
                        NAGYRHAGA_FirstEditPageActivity.activity.finish();
                        NAGYRHAGA_SecondEditPageActivity.activity.finish();
                        MediaScannerConnection.scanFile(NAGYRHAGA_SecondEditPageActivity.this, new String[]{file.getAbsolutePath()}, new String[]{"image/*"}, null);
                    }
                }, 2000);
            }
        });
        this.main.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {

                if (NAGYRHAGA_SecondEditPageActivity.this.mCurrentView != null) {
                    NAGYRHAGA_SecondEditPageActivity.this.mCurrentView.setInEdit(false);
                }
            }
        });
        this.back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    NAGYRHAGA_SecondEditPageActivity.this.onBackPressed();
                }

            }
        });
        this.overlay.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NAGYRHAGA_SecondEditPageActivity.this.callOverLayData();
                NAGYRHAGA_SecondEditPageActivity.this.getVisibleData = "0";
                NAGYRHAGA_SecondEditPageActivity.this.getVisibility(NAGYRHAGA_SecondEditPageActivity.this.getVisibleData);
                NAGYRHAGA_SecondEditPageActivity.this.addone = "1";
                NAGYRHAGA_SecondEditPageActivity.this.buttonVisibility(NAGYRHAGA_SecondEditPageActivity.this.addone);
            }
        });
        this.filter.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NAGYRHAGA_SecondEditPageActivity.this.callFilterList(NAGYRHAGA_FirstEditPageActivity.bm);
                NAGYRHAGA_SecondEditPageActivity.this.getVisibleData = "1";
                NAGYRHAGA_SecondEditPageActivity.this.getVisibility(NAGYRHAGA_SecondEditPageActivity.this.getVisibleData);
                NAGYRHAGA_SecondEditPageActivity.this.addone = ExifInterface.GPS_MEASUREMENT_2D;
                NAGYRHAGA_SecondEditPageActivity.this.buttonVisibility(NAGYRHAGA_SecondEditPageActivity.this.addone);
            }
        });
        this.text.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NAGYRHAGA_SecondEditPageActivity.this.startActivityForResult(new Intent(NAGYRHAGA_SecondEditPageActivity.this, NAGYRHAGA_AddTextPageActivity.class), NAGYRHAGA_SecondEditPageActivity.this.GETTEXT);
                NAGYRHAGA_SecondEditPageActivity.this.addone = ExifInterface.GPS_MEASUREMENT_3D;
                NAGYRHAGA_SecondEditPageActivity.this.buttonVisibility(NAGYRHAGA_SecondEditPageActivity.this.addone);
            }
        });
        this.sticker.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NAGYRHAGA_SecondEditPageActivity.this.startActivityForResult(new Intent(NAGYRHAGA_SecondEditPageActivity.this, NAGYRHAGA_GetAllStickerActivity.class), NAGYRHAGA_SecondEditPageActivity.this.GETSTRICKER);
                NAGYRHAGA_SecondEditPageActivity.this.addone = "4";
                NAGYRHAGA_SecondEditPageActivity.this.buttonVisibility(NAGYRHAGA_SecondEditPageActivity.this.addone);
            }
        });
        this.popText.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            }
        });
    }

    private void init() {
        this.imgFrame = (ImageView) findViewById(R.id.imgFrame);
        this.saveImage = (ImageView) findViewById(R.id.saveImage);
        this.back = (ImageView) findViewById(R.id.back);
        this.thumbListView = (RecyclerView) findViewById(R.id.myFrameList);
        this.save = (RelativeLayout) findViewById(R.id.save);
        this.main = (RelativeLayout) findViewById(R.id.main);
        this.overlay = (ImageView) findViewById(R.id.overlay);
        this.filter = (ImageView) findViewById(R.id.filter);
        this.one = (RelativeLayout) findViewById(R.id.one);
        this.two = (RelativeLayout) findViewById(R.id.two);
        this.text = (ImageView) findViewById(R.id.text);
        this.sticker = (ImageView) findViewById(R.id.sticker);
        this.popText = (ImageView) findViewById(R.id.popText);
        this.secondImage = (ImageView) findViewById(R.id.secondImage);
        this.frame = (FrameLayout) findViewById(R.id.frame);
    }

    public void setFrame(int i) {
        if (i == 0) {
            loadImage();
            this.secondImage.setImageDrawable(null);
            return;
        }
        try {
            loadImage();
            AssetManager assets = getAssets();
            StringBuilder sb = new StringBuilder();
            sb.append("All Over/");
            sb.append(this.AllOverLay[i]);
            this.finalBitmap = BitmapFactory.decodeStream(assets.open(sb.toString()));
            this.secondImage.setImageBitmap(this.finalBitmap);
        } catch (Exception unused) {
        }
    }

    private void loadImage() {
        this.main.post(new Runnable() {
            public void run() {
                try {
                    Bitmap bitmap = ((BitmapDrawable) NAGYRHAGA_SecondEditPageActivity.this.imgFrame.getDrawable()).getBitmap();
                    bitmap.compress(CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                    LayoutParams layoutParams = new LayoutParams(bitmap.getWidth(), bitmap.getHeight());
                    layoutParams.setMargins(0, (NAGYRHAGA_SecondEditPageActivity.this.w * 30) / 1080, 0, 0);
                    NAGYRHAGA_SecondEditPageActivity.this.save.setLayoutParams(layoutParams);
                } catch (Exception e) {
                    e.toString();
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, @Nullable Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == this.GETSTRICKER && i2 == -1) {
            addStickerView();
            loadImage();
        } else if (i == this.GETTEXT && i2 == -1) {
            addStickerView();
            loadImage();
        }
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
