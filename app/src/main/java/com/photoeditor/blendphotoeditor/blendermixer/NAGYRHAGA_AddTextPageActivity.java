package com.photoeditor.blendphotoeditor.blendermixer;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.internal.view.SupportMenu;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.photoeditor.blendphotoeditor.blendermixer.Adapter.NAGYRHAGA_AllFontListAdapter;
import com.photoeditor.blendphotoeditor.blendermixer.Model.NAGYRHAGA_AppPreferences;
import com.photoeditor.blendphotoeditor.blendermixer.holocolorpicker.NAGYRHAGA_ColorPicker;
import com.photoeditor.blendphotoeditor.blendermixer.holocolorpicker.NAGYRHAGA_OpacityBar;
import com.photoeditor.blendphotoeditor.blendermixer.holocolorpicker.NAGYRHAGA_SVBar;

import java.util.ArrayList;

/*import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;*/

/*import android.support.v4.internal.view.SupportMenu;*/
/*
import com.naygrh.multiplephotoblendermixerphotoeditor.Adapter.NAGYRHAGA_AllFontListAdapter;
import com.naygrh.multiplephotoblendermixerphotoeditor.Model.NAGYRHAGA_AppPreferences;
import com.naygrh.multiplephotoblendermixerphotoeditor.holocolorpicker.NAGYRHAGA_ColorPicker;
import com.naygrh.multiplephotoblendermixerphotoeditor.holocolorpicker.NAGYRHAGA_OpacityBar;
import com.naygrh.multiplephotoblendermixerphotoeditor.holocolorpicker.NAGYRHAGA_SVBar;
*/

public class NAGYRHAGA_AddTextPageActivity extends AppCompatActivity {
    public static String MyFinalString;
    NAGYRHAGA_AppPreferences NAGYRHAGAAppPreferences;
    ArrayList<String> PathListView;
    NAGYRHAGA_AllFontListAdapter adapter;
    ImageView back;
    private UnifiedNativeAd nativeAd;
    private static final String ADMOB_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110";
    private final String TAG = NAGYRHAGA_AddTextPageActivity.class.getSimpleName();
    private InterstitialAd mInterstitialAd;
    AdView mAdView;
    private int currentBackgroundColor = -1;
    Typeface font;
    String[] fontlist;
    int h;
    Dialog mDialog;
    int mainColor = SupportMenu.CATEGORY_MASK;
    public ImageView myColor;
    public ImageView myFont;
    ListView myFontList;
    EditText myText;
    ImageView saveImage;
    int textSize = 100;
    int w;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.nagyrhaga_activity_add_text_page);
        changeBackgroundColor(this.currentBackgroundColor);
        /*this.NAGYRHAGAAppPreferences = new NAGYRHAGA_AppPreferences(this);*/
        getWindow().addFlags(1024);
        this.w = getResources().getDisplayMetrics().widthPixels;
        this.h = getResources().getDisplayMetrics().heightPixels;
        init();
        clicks();
        setDesign();
       /* finter();*/

        MobileAds.initialize(this, new OnInitializationCompleteListener () {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

        refreshAd();

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString (R.string.inter_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    private void changeBackgroundColor(int i) {
        this.currentBackgroundColor = i;
    }

    private void setDesign() {
        LayoutParams layoutParams = new LayoutParams((this.w * 90) / 1080, (this.w * 90) / 1080);
        layoutParams.addRule(13);
        this.back.setLayoutParams(layoutParams);
        this.saveImage.setLayoutParams(layoutParams);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams((this.w * 280) / 1080, (this.w * 120) / 1080);
        layoutParams2.gravity = 17;
        layoutParams2.setMargins((this.w * 150) / 1080, 0, 0, 0);
        this.myFont.setLayoutParams(layoutParams2);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams((this.w * 280) / 1080, (this.w * 120) / 1080);
        layoutParams3.gravity = 17;
        this.myColor.setLayoutParams(layoutParams3);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.setEditText);
        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams((this.w * 1080) / 1080, (this.w * 452) / 1080);
        layoutParams4.setMargins(0, (this.w * 50) / 1080, 0, 0);
        relativeLayout.setLayoutParams(layoutParams4);
    }

    private void clicks() {
        this.back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    NAGYRHAGA_AddTextPageActivity.this.finish();

                }
/*
                NAGYRHAGA_AddTextPageActivity.this.finish();
*/
            }
        });
        this.saveImage.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!NAGYRHAGA_AddTextPageActivity.this.myText.getText().toString().equals("")) {
                    Bitmap textAsBitmap = NAGYRHAGA_AddTextPageActivity.this.textAsBitmap(NAGYRHAGA_AddTextPageActivity.this.myText.getText().toString(), (float) NAGYRHAGA_AddTextPageActivity.this.textSize, NAGYRHAGA_AddTextPageActivity.this.myText.getCurrentTextColor());
                    if (textAsBitmap != null) {
                        NAGYRHAGA_SecondEditPageActivity.myStricker = textAsBitmap;
                        NAGYRHAGA_AddTextPageActivity.this.setResult(-1);
                        NAGYRHAGA_AddTextPageActivity.this.finish();
                        return;
                    }
                    return;
                }
                Toast.makeText(NAGYRHAGA_AddTextPageActivity.this, "Please Enter your Text...", Toast.LENGTH_SHORT).show();
            }
        });
        this.myFont.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    NAGYRHAGA_AddTextPageActivity.this.ShowMusicList();
                }

            }
        });
        this.myColor.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    NAGYRHAGA_AddTextPageActivity.this.color_dialog();
                }

            }
        });
    }

    public Bitmap textAsBitmap(String str, float f, int i) {
        Paint paint = new Paint(1);
        paint.setTextSize(f);
        paint.setColor(i);
        paint.setTypeface(this.font);
        paint.setTextAlign(Align.LEFT);
        float f2 = -paint.ascent();
        Bitmap createBitmap = Bitmap.createBitmap((int) (paint.measureText(str) + 0.5f), (int) (paint.descent() + f2 + 0.5f), Config.ARGB_8888);
        new Canvas(createBitmap).drawText(str, 0.0f, f2, paint);
        return createBitmap;
    }

    /* access modifiers changed from: 0000 */
    public void color_dialog() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.nagyrhaga_pop_color);
        ((LinearLayout) dialog.findViewById(R.id.mainLay)).setLayoutParams(new LayoutParams((this.w * 850) / 1080, (this.w * 1090) / 1080));
        try {
            ((TextView) dialog.findViewById(R.id.title)).setTypeface(this.font);
        } catch (Exception e) {
            e.toString();
        }
        ImageView imageView = (ImageView) dialog.findViewById(R.id.cancel);
        ImageView imageView2 = (ImageView) dialog.findViewById(R.id.ok);
        LayoutParams layoutParams = new LayoutParams((this.w * 280) / 1080, (this.w * 120) / 1080);
        layoutParams.addRule(14);
        imageView.setLayoutParams(layoutParams);
        imageView2.setLayoutParams(layoutParams);
        final NAGYRHAGA_ColorPicker nAGYRHAGA_ColorPicker = (NAGYRHAGA_ColorPicker) dialog.findViewById(R.id.picker);
        NAGYRHAGA_SVBar nAGYRHAGA_SVBar = (NAGYRHAGA_SVBar) dialog.findViewById(R.id.svbar);
        NAGYRHAGA_OpacityBar nAGYRHAGA_OpacityBar = (NAGYRHAGA_OpacityBar) dialog.findViewById(R.id.opacitybar);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams((this.w * 500) / 1080, (this.w * 500) / 1080);
        layoutParams2.gravity = 17;
        nAGYRHAGA_ColorPicker.setLayoutParams(layoutParams2);
        nAGYRHAGA_ColorPicker.addSVBar(nAGYRHAGA_SVBar);
        nAGYRHAGA_ColorPicker.addOpacityBar(nAGYRHAGA_OpacityBar);
        nAGYRHAGA_ColorPicker.setOldCenterColor(this.mainColor);
        nAGYRHAGA_ColorPicker.setColor(this.mainColor);
        nAGYRHAGA_SVBar.setColor(this.mainColor);
        nAGYRHAGA_OpacityBar.setColor(this.mainColor);
        imageView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        imageView2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NAGYRHAGA_AddTextPageActivity.this.mainColor = nAGYRHAGA_ColorPicker.getColor();
                NAGYRHAGA_AddTextPageActivity.this.myText.setTextColor(NAGYRHAGA_AddTextPageActivity.this.mainColor);
                NAGYRHAGA_AddTextPageActivity.this.myText.setHintTextColor(NAGYRHAGA_AddTextPageActivity.this.mainColor);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /* access modifiers changed from: private */
    public void ShowMusicList() {
        this.mDialog = new Dialog(this);
        this.mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(-1));
        this.mDialog.requestWindowFeature(1);
        this.mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.mDialog.setContentView(R.layout.nagyrhaga_font_list);
        this.myFontList = (ListView) this.mDialog.findViewById(R.id.myFontList);
        LinearLayout linearLayout = (LinearLayout) this.mDialog.findViewById(R.id.demoforme);
        try {
            this.fontlist = getAssets().list("fontstyle");
            this.adapter = new NAGYRHAGA_AllFontListAdapter(this, this.fontlist);
            this.myFontList.setAdapter(this.adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LayoutParams layoutParams = new LayoutParams((this.w * 914) / 1080, (this.h * 1699) / 1920);
        layoutParams.addRule(13);
        /*linearLayout.setLayoutParams(layoutParams);*/

/*
        this.myFontList.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                NAGYRHAGA_AddTextPageActivity.MyFinalString = (String) NAGYRHAGA_AddTextPageActivity.this.PathListView.get(i);
                NAGYRHAGA_AddTextPageActivity.this.mDialog.dismiss();
            }
        });
*/

        this.myFontList.setOnItemClickListener (new OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                NAGYRHAGA_AddTextPageActivity.MyFinalString = (String) NAGYRHAGA_AddTextPageActivity.this.PathListView.get(i);
                NAGYRHAGA_AddTextPageActivity.this.mDialog.dismiss();
            }
        });
        this.adapter.notifyDataSetChanged();
        this.mDialog.show();
    }

    private void init() {
        this.back = (ImageView) findViewById(R.id.back);
        this.saveImage = (ImageView) findViewById(R.id.saveImage);
        this.myFont = (ImageView) findViewById(R.id.myFont);
        this.myColor = (ImageView) findViewById(R.id.myColor);
        this.myText = (EditText) findViewById(R.id.myText);
    }

    public void output(String str) {
        this.mDialog.dismiss();
        try {
            this.font = Typeface.createFromAsset(getAssets(), str);
            this.myText.setTypeface(this.font);
        } catch (Exception e) {
            e.toString();
        }
    }
    private void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView) {

        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }
        adView.setNativeAd(nativeAd);

    }
    private void refreshAd() {
        AdLoader.Builder builder = new AdLoader.Builder(this, getString (R.string.nativeid));

        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                if (nativeAd != null) {
                    nativeAd.destroy();
                }
                nativeAd = unifiedNativeAd;
                FrameLayout frameLayout =
                        findViewById(R.id.fl_adplaceholder);
                UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater()
                        .inflate(R.layout.ad_unified, null);
                populateUnifiedNativeAdView(unifiedNativeAd, adView);
                frameLayout.removeAllViews();
                frameLayout.addView(adView);
            }

        });

        AdLoader adLoader = builder.withAdListener(new AdListener () {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(NAGYRHAGA_AddTextPageActivity.this, "Failed to load native ad: "
                        + errorCode, Toast.LENGTH_SHORT).show();
            }
        }).build();

        adLoader.loadAd(new AdRequest.Builder().build());


        NativeAdOptions adOptions=new NativeAdOptions.Builder ().build ();
        builder.withNativeAdOptions (adOptions);

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
