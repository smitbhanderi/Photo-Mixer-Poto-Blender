package com.photoeditor.blendphotoeditor.blendermixer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.photoeditor.blendphotoeditor.blendermixer.Adapter.NAGYRHAGA_FadingEdgeLayout;
import com.photoeditor.blendphotoeditor.blendermixer.Adapter.NAGYRHAGA_Image_Frame_Adapter;
import com.photoeditor.blendphotoeditor.blendermixer.touch.NAGYRHAGA_MultiTouchListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/*import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;*/

public class NAGYRHAGA_FirstEditPageActivity extends AppCompatActivity {
    public static int PICK_FOR_CROP_BACK_REQUEST = 400;
    public static int PICK_FOR_CROP_REQUEST = 300;
    public static int PICK_IMAGE_FOR_BACK_REQUEST = 200;
    public static int PICK_IMAGE_REQUEST = 100;
    public static Activity activity;
    public static Bitmap bitmap;
    public static Bitmap bm;
    AdView mAdView;
    public static View cView;
    private final String TAG = NAGYRHAGA_FirstEditPageActivity.class.getSimpleName();
    private InterstitialAd mInterstitialAd;
    public static SeekBar fadeOutSeek;
    public static SeekBar fadeinSeek;
    public static Uri selectedUri;
    String[] AllFrames;
    NAGYRHAGA_Image_Frame_Adapter NAGYRHAGAImage_Frame_Adapter;
    RelativeLayout addImage;
    ImageView back;
    ImageView bg;
    ImageView blend;
    Bitmap frameBitmap;
    int h;
    ImageView imgFrame;
    LayoutManager layoutManager;
    RelativeLayout main;
    RecyclerView myFrameList;
    String myOne = "0";
    LinearLayout one;
    ImageView photo;
    RelativeLayout save;
    ImageView saveImage;
    TextView title;
    LinearLayout two;
    int w;

    /* access modifiers changed from: protected */
    @SuppressLint("WrongConstant")
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.nagyrhaga_activity_first_edit_page);
        getWindow().addFlags(1024);
        this.w = getResources().getDisplayMetrics().widthPixels;
        this.h = getResources().getDisplayMetrics().heightPixels;
        activity = this;
        init();
        /*finter();*/
        click();

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString (R.string.inter_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        this.one.setVisibility(8);
        this.two.setVisibility(8);
        visibilityView();
        try {
            this.AllFrames = getAssets().list("All Bag");
            this.NAGYRHAGAImage_Frame_Adapter = new NAGYRHAGA_Image_Frame_Adapter(this, this.AllFrames);
            this.myFrameList.setAdapter(this.NAGYRHAGAImage_Frame_Adapter);
            this.layoutManager = new LinearLayoutManager (this, 0, false);
            this.myFrameList.setLayoutManager(this.layoutManager);
        } catch (Exception unused) {
        }
        setDesign();
        try {
            AssetManager assets = getAssets();
            StringBuilder sb = new StringBuilder();
            sb.append("All Bag/");
            sb.append(this.AllFrames[1]);
            this.frameBitmap = BitmapFactory.decodeStream(assets.open(sb.toString()));
            this.imgFrame.setImageBitmap(this.frameBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.blend.setImageResource(R.drawable.blend_unpress);
        this.bg.setImageResource(R.drawable.bg_press);
        this.photo.setImageResource(R.drawable.photo_unpress);
        fadeinSeek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (NAGYRHAGA_FirstEditPageActivity.cView != null) {
                    ((NAGYRHAGA_FadingEdgeLayout) NAGYRHAGA_FirstEditPageActivity.cView).setAlpha(((float) i) / 100.0f);
                } else {
                    Toast.makeText(NAGYRHAGA_FirstEditPageActivity.this, "Select Image For Blending...", 0).show();
                }
            }
        });
        fadeOutSeek.setMax(this.w / 2);
        fadeOutSeek.setProgress(this.w / 4);
        fadeOutSeek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (NAGYRHAGA_FirstEditPageActivity.cView != null) {
                    NAGYRHAGA_FadingEdgeLayout nAGYRHAGA_FadingEdgeLayout = (NAGYRHAGA_FadingEdgeLayout) NAGYRHAGA_FirstEditPageActivity.cView;
                    nAGYRHAGA_FadingEdgeLayout.setFadeEdges(true, true, true, true);
                    nAGYRHAGA_FadingEdgeLayout.setFadeSizes(i, i, i, i);
                    return;
                }
                Toast.makeText(NAGYRHAGA_FirstEditPageActivity.this, "Select Image For Blending...", 0).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setFrame(int i) {
        if (i == 0) {
            Intent intent = new Intent("android.intent.action.PICK");
            intent.setTypeAndNormalize("Image/*");
            startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_FOR_BACK_REQUEST);
            return;
        }
        try {
            loadImage();
            AssetManager assets = getAssets();
            StringBuilder sb = new StringBuilder();
            sb.append("All Bag/");
            sb.append(this.AllFrames[i]);
            this.frameBitmap = BitmapFactory.decodeStream(assets.open(sb.toString()));
            this.imgFrame.setImageBitmap(this.frameBitmap);
        } catch (Exception unused) {
        }
    }

    private void click() {
        this.back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NAGYRHAGA_FirstEditPageActivity.this.onBackPressed();
            }
        });
        this.saveImage.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NAGYRHAGA_FirstEditPageActivity.this.save.setDrawingCacheEnabled(false);
                NAGYRHAGA_FirstEditPageActivity.this.save.setDrawingCacheEnabled(true);
                NAGYRHAGA_FirstEditPageActivity.this.save.buildDrawingCache();
                NAGYRHAGA_FirstEditPageActivity.bm = NAGYRHAGA_FirstEditPageActivity.this.save.getDrawingCache();
                NAGYRHAGA_FirstEditPageActivity.this.startActivity(new Intent(NAGYRHAGA_FirstEditPageActivity.this, NAGYRHAGA_SecondEditPageActivity.class));
            }
        });
        this.blend.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NAGYRHAGA_FirstEditPageActivity.this.myOne = "1";
                NAGYRHAGA_FirstEditPageActivity.this.visibilityView();
                NAGYRHAGA_FirstEditPageActivity.this.blend.setImageResource(R.drawable.blend_press);
                NAGYRHAGA_FirstEditPageActivity.this.bg.setImageResource(R.drawable.bg_unpress);
                NAGYRHAGA_FirstEditPageActivity.this.photo.setImageResource(R.drawable.photo_unpress);
            }
        });
        this.bg.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NAGYRHAGA_FirstEditPageActivity.this.myOne = "0";
                NAGYRHAGA_FirstEditPageActivity.this.visibilityView();
                NAGYRHAGA_FirstEditPageActivity.this.bg.setImageResource(R.drawable.bg_press);
                NAGYRHAGA_FirstEditPageActivity.this.photo.setImageResource(R.drawable.photo_unpress);
                NAGYRHAGA_FirstEditPageActivity.this.blend.setImageResource(R.drawable.blend_unpress);
            }
        });
        this.photo.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View view) {
                NAGYRHAGA_FirstEditPageActivity.this.myOne = "0";
                NAGYRHAGA_FirstEditPageActivity.this.visibilityView();
                NAGYRHAGA_FirstEditPageActivity.this.photo.setImageResource(R.drawable.photo_press);
                NAGYRHAGA_FirstEditPageActivity.this.bg.setImageResource(R.drawable.bg_unpress);
                NAGYRHAGA_FirstEditPageActivity.this.blend.setImageResource(R.drawable.blend_unpress);
                Intent intent = new Intent("android.intent.action.PICK");
                intent.setTypeAndNormalize("Image/*");
                NAGYRHAGA_FirstEditPageActivity.this.startActivityForResult(Intent.createChooser(intent, "Select Image"), NAGYRHAGA_FirstEditPageActivity.PICK_IMAGE_REQUEST);
            }
        });
        loadImage();
    }

    private void loadImage() {
        this.main.post(new Runnable() {
            public void run() {
                try {
                    Bitmap bitmap = ((BitmapDrawable) NAGYRHAGA_FirstEditPageActivity.this.imgFrame.getDrawable()).getBitmap();
                    bitmap.compress(CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                    Bitmap bitmapResize = NAGYRHAGA_FirstEditPageActivity.this.bitmapResize(bitmap, NAGYRHAGA_FirstEditPageActivity.this.main);
                    LayoutParams layoutParams = new LayoutParams(bitmapResize.getWidth(), bitmapResize.getHeight());
                    layoutParams.setMargins(0, (NAGYRHAGA_FirstEditPageActivity.this.w * 30) / 1080, 0, 0);
                    NAGYRHAGA_FirstEditPageActivity.this.save.setLayoutParams(layoutParams);
                } catch (Exception e) {
                    e.toString();
                }
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public Bitmap bitmapResize(Bitmap bitmap2, View view) {
        int width = view.getWidth();
        int height = view.getHeight();
        int width2 = bitmap2.getWidth();
        int height2 = bitmap2.getHeight();
        if (width2 >= height2) {
            int i = (height2 * width) / width2;
            if (i > height) {
                width = (width * height) / i;
            } else {
                height = i;
            }
        } else {
            int i2 = (width2 * height) / height2;
            if (i2 > width) {
                height = (height * width) / i2;
            } else {
                width = i2;
            }
        }
        return Bitmap.createScaledBitmap(bitmap2, width, height, true);
    }

    /* access modifiers changed from: private */
    @SuppressLint("WrongConstant")
    public void visibilityView() {
        if (this.myOne.equals("0")) {
            this.one.setVisibility(0);
            this.two.setVisibility(8);
        } else if (this.myOne.equals("1")) {
            this.two.setVisibility(0);
            this.one.setVisibility(8);
        }
    }

    private void init() {
        this.back = (ImageView) findViewById(R.id.back);
        this.saveImage = (ImageView) findViewById(R.id.saveImage);
        this.title = (TextView) findViewById(R.id.title);
        this.photo = (ImageView) findViewById(R.id.photo);
        this.bg = (ImageView) findViewById(R.id.bg);
        this.blend = (ImageView) findViewById(R.id.blend);
        this.addImage = (RelativeLayout) findViewById(R.id.addImage);
        this.myFrameList = (RecyclerView) findViewById(R.id.myFrameList);
        this.imgFrame = (ImageView) findViewById(R.id.imgFrame);
        this.one = (LinearLayout) findViewById(R.id.one);
        this.two = (LinearLayout) findViewById(R.id.two);
        this.save = (RelativeLayout) findViewById(R.id.save);
        this.main = (RelativeLayout) findViewById(R.id.main);
        fadeinSeek = (SeekBar) findViewById(R.id.fadeinSeek);
        fadeOutSeek = (SeekBar) findViewById(R.id.fadeOutSeek);
    }

    private void setDesign() {
        LayoutParams layoutParams = new LayoutParams((this.w * 90) / 1080, (this.w * 90) / 1080);
        layoutParams.addRule(13);
        this.back.setLayoutParams(layoutParams);
        this.saveImage.setLayoutParams(layoutParams);
        LayoutParams layoutParams2 = new LayoutParams((this.w * 180) / 1080, (this.w * 180) / 1080);
        layoutParams2.addRule(13);
        this.photo.setLayoutParams(layoutParams2);
        this.bg.setLayoutParams(layoutParams2);
        this.blend.setLayoutParams(layoutParams2);
        LayoutParams layoutParams3 = new LayoutParams((this.w * 1080) / 1080, (this.w * 220) / 1080);
        layoutParams3.addRule(13);
        this.two.setLayoutParams(layoutParams3);
        this.one.setLayoutParams(layoutParams3);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.myone);
        LayoutParams layoutParams4 = new LayoutParams((this.w * 1080) / 1080, (this.w * 180) / 1080);
        layoutParams4.addRule(12);
        linearLayout.setLayoutParams(layoutParams4);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, @Nullable Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 != -1) {
            return;
        }
        if (i == PICK_IMAGE_REQUEST) {
            selectedUri = intent.getData();
            String path = getPath(selectedUri);
            if (selectedUri == null) {
                Toast.makeText(activity, R.string.toast_cannot_retrieve_selected_image, Toast.LENGTH_SHORT).show();
            } else if (path != null) {
                startActivityForResult(new Intent(this, NAGYRHAGA_CropImagePageActivity.class), PICK_FOR_CROP_REQUEST);
            } else {
                Toast.makeText(activity, R.string.toast_cannot_retrieve_selected_image, Toast.LENGTH_SHORT).show();
            }
        } else if (i == PICK_IMAGE_FOR_BACK_REQUEST) {
            selectedUri = intent.getData();
            String path2 = getPath(selectedUri);
            if (selectedUri == null) {
                Toast.makeText(activity, R.string.toast_cannot_retrieve_selected_image, Toast.LENGTH_SHORT).show();
            } else if (path2 != null) {
                startActivityForResult(new Intent(this, NAGYRHAGA_CropImagePageActivity.class), PICK_FOR_CROP_BACK_REQUEST);
            } else {
                Toast.makeText(activity, R.string.toast_cannot_retrieve_selected_image, Toast.LENGTH_SHORT).show();
            }
        } else if (i == PICK_FOR_CROP_REQUEST) {
            NAGYRHAGA_FadingEdgeLayout nAGYRHAGA_FadingEdgeLayout = new NAGYRHAGA_FadingEdgeLayout(this);
            this.addImage.addView(nAGYRHAGA_FadingEdgeLayout);
            nAGYRHAGA_FadingEdgeLayout.setLayoutParams(new LayoutParams(-1, -1));
            ImageView imageView = new ImageView(this);
            imageView.setImageBitmap(bitmapResize(bitmap, this.main));
            nAGYRHAGA_FadingEdgeLayout.addView(imageView);
            imageView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
            nAGYRHAGA_FadingEdgeLayout.setFadeEdges(false, false, false, false);
            nAGYRHAGA_FadingEdgeLayout.setFadeSizes(0, 0, 0, 0);
            nAGYRHAGA_FadingEdgeLayout.setOnTouchListener(new NAGYRHAGA_MultiTouchListener ());
            nAGYRHAGA_FadingEdgeLayout.setAlpha(0.5f);
            nAGYRHAGA_FadingEdgeLayout.setFadeEdges(true, true, true, true);
            nAGYRHAGA_FadingEdgeLayout.setFadeSizes(this.w / 4, this.w / 4, this.w / 4, this.w / 4);
            cView = nAGYRHAGA_FadingEdgeLayout;
            this.myOne = "1";
            visibilityView();
            this.blend.setImageResource(R.drawable.blend_press);
            this.photo.setImageResource(R.drawable.photo_unpress);
        } else if (i == PICK_FOR_CROP_BACK_REQUEST) {
            this.imgFrame.setImageBitmap(bitmapResize(bitmap, this.main));
        }
    }

    public String getPath(Uri uri) {
        Cursor query = getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
        if (query == null) {
            return null;
        }
        int columnIndexOrThrow = query.getColumnIndexOrThrow("_data");
        query.moveToFirst();
        return query.getString(columnIndexOrThrow);
    }

/*
    public void finter(){
        AudienceNetworkAds.initialize(this);
        interstitialAd = new InterstitialAd(this, getString (R.string.f_inter));
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
