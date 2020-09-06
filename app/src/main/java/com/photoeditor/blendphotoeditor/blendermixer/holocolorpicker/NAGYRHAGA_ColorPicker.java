package com.photoeditor.blendphotoeditor.blendermixer.holocolorpicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;

import com.photoeditor.blendphotoeditor.blendermixer.R;

/*import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.InputDeviceCompat;*/
/*import com.naygrh.multiplephotoblendermixerphotoeditor.R;*/

public class NAGYRHAGA_ColorPicker extends View {
    private static final int[] COLORS = {SupportMenu.CATEGORY_MASK, -65281, -16776961, -16711681, -16711936, InputDeviceCompat.SOURCE_ANY, SupportMenu.CATEGORY_MASK};
    private static final String STATE_ANGLE = "angle";
    private static final String STATE_OLD_COLOR = "color";
    private static final String STATE_PARENT = "parent";
    private static final String STATE_SHOW_OLD_COLOR = "showColor";
    private float mAngle;
    private Paint mCenterHaloPaint;
    private int mCenterNewColor;
    private Paint mCenterNewPaint;
    private int mCenterOldColor;
    private Paint mCenterOldPaint;
    private RectF mCenterRectangle = new RectF();
    private int mColor;
    private int mColorCenterHaloRadius;
    private int mColorCenterRadius;
    private int mColorPointerHaloRadius;
    private int mColorPointerRadius;
    private Paint mColorWheelPaint;
    private int mColorWheelRadius;
    private RectF mColorWheelRectangle = new RectF();
    private int mColorWheelThickness;
    private float[] mHSV = new float[3];
    private NAGYRHAGA_OpacityBar mNAGYRHAGAOpacityBar = null;
    private NAGYRHAGA_SaturationBar mNAGYRHAGASaturationBar = null;
    private NAGYRHAGA_ValueBar mNAGYRHAGAValueBar = null;
    private Paint mPointerColor;
    private Paint mPointerHaloPaint;
    private int mPreferredColorCenterHaloRadius;
    private int mPreferredColorCenterRadius;
    private int mPreferredColorWheelRadius;
    private NAGYRHAGA_SVBar mSVbar = null;
    private boolean mShowCenterOldColor;
    private float mSlopX;
    private float mSlopY;
    private boolean mTouchAnywhereOnColorWheelEnabled = true;
    private float mTranslationOffset;
    private boolean mUserIsMovingPointer = false;
    private int oldChangedListenerColor;
    private int oldSelectedListenerColor;
    private OnColorChangedListener onColorChangedListener;
    private OnColorSelectedListener onColorSelectedListener;
    Bitmap opacity;

    public interface OnColorChangedListener {
        void onColorChanged(int i);
    }

    public interface OnColorSelectedListener {
        void onColorSelected(int i);
    }

    public NAGYRHAGA_ColorPicker(Context context) {
        super(context);
        init(null, 0);
    }

    public NAGYRHAGA_ColorPicker(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet, 0);
    }

    public NAGYRHAGA_ColorPicker(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet, i);
    }

    public void setOnColorChangedListener(OnColorChangedListener onColorChangedListener2) {
        this.onColorChangedListener = onColorChangedListener2;
    }

    public OnColorChangedListener getOnColorChangedListener() {
        return this.onColorChangedListener;
    }

    public void setOnColorSelectedListener(OnColorSelectedListener onColorSelectedListener2) {
        this.onColorSelectedListener = onColorSelectedListener2;
    }

    public OnColorSelectedListener getOnColorSelectedListener() {
        return this.onColorSelectedListener;
    }

    @SuppressLint("ResourceType")
    private void init(AttributeSet attributeSet, int i) {
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.ColorPicker, i, 0);
        Resources resources = getContext().getResources();
        this.mColorWheelThickness = obtainStyledAttributes.getDimensionPixelSize(5, resources.getDimensionPixelSize(R.dimen.color_wheel_thickness));
        this.mColorWheelRadius = obtainStyledAttributes.getDimensionPixelSize(4, resources.getDimensionPixelSize(R.dimen.color_wheel_radius));
        this.mPreferredColorWheelRadius = this.mColorWheelRadius;
        this.mColorCenterRadius = obtainStyledAttributes.getDimensionPixelSize(1, resources.getDimensionPixelSize(R.dimen.color_center_radius));
        this.mPreferredColorCenterRadius = this.mColorCenterRadius;
        this.mColorCenterHaloRadius = obtainStyledAttributes.getDimensionPixelSize(0, resources.getDimensionPixelSize(R.dimen.color_center_halo_radius));
        this.mPreferredColorCenterHaloRadius = this.mColorCenterHaloRadius;
        this.mColorPointerRadius = obtainStyledAttributes.getDimensionPixelSize(3, resources.getDimensionPixelSize(R.dimen.color_pointer_radius));
        this.mColorPointerHaloRadius = obtainStyledAttributes.getDimensionPixelSize(2, resources.getDimensionPixelSize(R.dimen.color_pointer_halo_radius));
        obtainStyledAttributes.recycle();
        this.mAngle = -1.5707964f;
        SweepGradient sweepGradient = new SweepGradient(0.0f, 0.0f, COLORS, null);
        this.mColorWheelPaint = new Paint(1);
        this.mColorWheelPaint.setShader(sweepGradient);
        this.mColorWheelPaint.setStyle(Style.STROKE);
        this.mColorWheelPaint.setStrokeWidth((float) this.mColorWheelThickness);
        this.mPointerHaloPaint = new Paint(1);
        this.mPointerHaloPaint.setColor(-16777216);
        this.mPointerHaloPaint.setAlpha(80);
        this.mPointerColor = new Paint(1);
        this.mPointerColor.setColor(calculateColor(this.mAngle));
        this.mCenterNewPaint = new Paint(1);
        this.mCenterNewPaint.setColor(calculateColor(this.mAngle));
        this.mCenterNewPaint.setStyle(Style.FILL);
        this.mCenterOldPaint = new Paint(1);
        this.mCenterOldPaint.setColor(calculateColor(this.mAngle));
        this.mCenterOldPaint.setStyle(Style.FILL);
        this.mCenterHaloPaint = new Paint(1);
        this.mCenterHaloPaint.setColor(-16777216);
        this.mCenterHaloPaint.setAlpha(0);
        this.mCenterNewColor = calculateColor(this.mAngle);
        this.mCenterOldColor = calculateColor(this.mAngle);
        this.mShowCenterOldColor = true;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        this.opacity = BitmapFactory.decodeResource(getResources(), R.drawable.transparent);
        this.opacity = getCircleBitmap(this.opacity);
        this.opacity = Bitmap.createScaledBitmap(this.opacity, (int) this.mCenterRectangle.height(), (int) this.mCenterRectangle.height(), false);
        canvas.translate(this.mTranslationOffset, this.mTranslationOffset);
        canvas.drawOval(this.mColorWheelRectangle, this.mColorWheelPaint);
        float[] calculatePointerPosition = calculatePointerPosition(this.mAngle);
        canvas.drawCircle(calculatePointerPosition[0], calculatePointerPosition[1], (float) this.mColorPointerHaloRadius, this.mPointerHaloPaint);
        canvas.drawCircle(calculatePointerPosition[0], calculatePointerPosition[1], (float) this.mColorPointerRadius, this.mPointerColor);
        canvas.drawCircle(0.0f, 0.0f, (float) this.mColorCenterHaloRadius, this.mCenterHaloPaint);
        if (this.mShowCenterOldColor) {
            canvas.drawBitmap(this.opacity, (-this.mCenterRectangle.height()) / 2.0f, (-this.mCenterRectangle.height()) / 2.0f, null);
            canvas.drawArc(this.mCenterRectangle, 90.0f, 180.0f, true, this.mCenterOldPaint);
            canvas.drawArc(this.mCenterRectangle, 270.0f, 180.0f, true, this.mCenterNewPaint);
            return;
        }
        canvas.drawBitmap(this.opacity, (-this.mCenterRectangle.height()) / 2.0f, (-this.mCenterRectangle.height()) / 2.0f, null);
        canvas.drawArc(this.mCenterRectangle, 0.0f, 360.0f, true, this.mCenterNewPaint);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3 = (this.mPreferredColorWheelRadius + this.mColorPointerHaloRadius) * 2;
        int mode = MeasureSpec.getMode(i);
        int size = MeasureSpec.getSize(i);
        int mode2 = MeasureSpec.getMode(i2);
        int size2 = MeasureSpec.getSize(i2);
        if (mode != 1073741824) {
            size = mode == Integer.MIN_VALUE ? Math.min(i3, size) : i3;
        }
        if (mode2 == 1073741824) {
            i3 = size2;
        } else if (mode2 == Integer.MIN_VALUE) {
            i3 = Math.min(i3, size2);
        }
        int min = Math.min(size, i3);
        setMeasuredDimension(min, min);
        this.mTranslationOffset = ((float) min) * 0.5f;
        this.mColorWheelRadius = ((min / 2) - this.mColorWheelThickness) - this.mColorPointerHaloRadius;
        this.mColorWheelRectangle.set((float) (-this.mColorWheelRadius), (float) (-this.mColorWheelRadius), (float) this.mColorWheelRadius, (float) this.mColorWheelRadius);
        this.mColorCenterRadius = (int) (((float) this.mPreferredColorCenterRadius) * (((float) this.mColorWheelRadius) / ((float) this.mPreferredColorWheelRadius)));
        this.mColorCenterHaloRadius = (int) (((float) this.mPreferredColorCenterHaloRadius) * (((float) this.mColorWheelRadius) / ((float) this.mPreferredColorWheelRadius)));
        this.mCenterRectangle.set((float) (-this.mColorCenterRadius), (float) (-this.mColorCenterRadius), (float) this.mColorCenterRadius, (float) this.mColorCenterRadius);
    }

    private int ave(int i, int i2, float f) {
        return i + Math.round(f * ((float) (i2 - i)));
    }

    private int calculateColor(float f) {
        float f2 = (float) (((double) f) / 6.283185307179586d);
        if (f2 < 0.0f) {
            f2 += 1.0f;
        }
        if (f2 <= 0.0f) {
            this.mColor = COLORS[0];
            return COLORS[0];
        } else if (f2 >= 1.0f) {
            this.mColor = COLORS[COLORS.length - 1];
            return COLORS[COLORS.length - 1];
        } else {
            float length = f2 * ((float) (COLORS.length - 1));
            int i = (int) length;
            float f3 = length - ((float) i);
            int i2 = COLORS[i];
            int i3 = COLORS[i + 1];
            int ave = ave(Color.alpha(i2), Color.alpha(i3), f3);
            int ave2 = ave(Color.red(i2), Color.red(i3), f3);
            int ave3 = ave(Color.green(i2), Color.green(i3), f3);
            int ave4 = ave(Color.blue(i2), Color.blue(i3), f3);
            this.mColor = Color.argb(ave, ave2, ave3, ave4);
            return Color.argb(ave, ave2, ave3, ave4);
        }
    }

    public int getColor() {
        return this.mCenterNewColor;
    }

    public void setColor(int i) {
        this.mAngle = colorToAngle(i);
        this.mPointerColor.setColor(calculateColor(this.mAngle));
        if (this.mNAGYRHAGAOpacityBar != null) {
            this.mNAGYRHAGAOpacityBar.setColor(this.mColor);
            this.mNAGYRHAGAOpacityBar.setOpacity(Color.alpha(i));
        }
        if (this.mSVbar != null) {
            Color.colorToHSV(i, this.mHSV);
            this.mSVbar.setColor(this.mColor);
            if (this.mHSV[1] < this.mHSV[2]) {
                this.mSVbar.setSaturation(this.mHSV[1]);
            } else if (this.mHSV[1] > this.mHSV[2]) {
                this.mSVbar.setValue(this.mHSV[2]);
            }
        }
        if (this.mNAGYRHAGASaturationBar != null) {
            Color.colorToHSV(i, this.mHSV);
            this.mNAGYRHAGASaturationBar.setColor(this.mColor);
            this.mNAGYRHAGASaturationBar.setSaturation(this.mHSV[1]);
        }
        if (this.mNAGYRHAGAValueBar != null && this.mNAGYRHAGASaturationBar == null) {
            Color.colorToHSV(i, this.mHSV);
            this.mNAGYRHAGAValueBar.setColor(this.mColor);
            this.mNAGYRHAGAValueBar.setValue(this.mHSV[2]);
        } else if (this.mNAGYRHAGAValueBar != null) {
            Color.colorToHSV(i, this.mHSV);
            this.mNAGYRHAGAValueBar.setValue(this.mHSV[2]);
        }
        setNewCenterColor(i);
    }

    private float colorToAngle(int i) {
        float[] fArr = new float[3];
        Color.colorToHSV(i, fArr);
        return (float) Math.toRadians((double) (-fArr[0]));
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        getParent().requestDisallowInterceptTouchEvent(true);
        float x = motionEvent.getX() - this.mTranslationOffset;
        float y = motionEvent.getY() - this.mTranslationOffset;
        switch (motionEvent.getAction()) {
            case 0:
                float[] calculatePointerPosition = calculatePointerPosition(this.mAngle);
                if (x < calculatePointerPosition[0] - ((float) this.mColorPointerHaloRadius) || x > calculatePointerPosition[0] + ((float) this.mColorPointerHaloRadius) || y < calculatePointerPosition[1] - ((float) this.mColorPointerHaloRadius) || y > calculatePointerPosition[1] + ((float) this.mColorPointerHaloRadius)) {
                    if (x >= ((float) (-this.mColorCenterRadius)) && x <= ((float) this.mColorCenterRadius) && y >= ((float) (-this.mColorCenterRadius)) && y <= ((float) this.mColorCenterRadius) && this.mShowCenterOldColor) {
                        this.mCenterHaloPaint.setAlpha(80);
                        setColor(getOldCenterColor());
                        invalidate();
                        break;
                    } else {
                        double d = (double) ((x * x) + (y * y));
                        if (Math.sqrt(d) <= ((double) (this.mColorWheelRadius + this.mColorPointerHaloRadius)) && Math.sqrt(d) >= ((double) (this.mColorWheelRadius - this.mColorPointerHaloRadius)) && this.mTouchAnywhereOnColorWheelEnabled) {
                            this.mUserIsMovingPointer = true;
                            invalidate();
                            break;
                        } else {
                            getParent().requestDisallowInterceptTouchEvent(false);
                            return false;
                        }
                    }
                } else {
                    this.mSlopX = x - calculatePointerPosition[0];
                    this.mSlopY = y - calculatePointerPosition[1];
                    this.mUserIsMovingPointer = true;
                    invalidate();
                    break;
                }
            case 1:
                this.mUserIsMovingPointer = false;
                this.mCenterHaloPaint.setAlpha(0);
                if (!(this.onColorSelectedListener == null || this.mCenterNewColor == this.oldSelectedListenerColor)) {
                    this.onColorSelectedListener.onColorSelected(this.mCenterNewColor);
                    this.oldSelectedListenerColor = this.mCenterNewColor;
                }
                invalidate();
                break;
            case 2:
                if (this.mUserIsMovingPointer) {
                    this.mAngle = (float) Math.atan2((double) (y - this.mSlopY), (double) (x - this.mSlopX));
                    this.mPointerColor.setColor(calculateColor(this.mAngle));
                    int calculateColor = calculateColor(this.mAngle);
                    this.mCenterNewColor = calculateColor;
                    setNewCenterColor(calculateColor);
                    if (this.mNAGYRHAGAOpacityBar != null) {
                        this.mNAGYRHAGAOpacityBar.setColor(this.mColor);
                    }
                    if (this.mNAGYRHAGAValueBar != null) {
                        this.mNAGYRHAGAValueBar.setColor(this.mColor);
                    }
                    if (this.mNAGYRHAGASaturationBar != null) {
                        this.mNAGYRHAGASaturationBar.setColor(this.mColor);
                    }
                    if (this.mSVbar != null) {
                        this.mSVbar.setColor(this.mColor);
                    }
                    invalidate();
                    break;
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                }
            case 3:
                if (!(this.onColorSelectedListener == null || this.mCenterNewColor == this.oldSelectedListenerColor)) {
                    this.onColorSelectedListener.onColorSelected(this.mCenterNewColor);
                    this.oldSelectedListenerColor = this.mCenterNewColor;
                    break;
                }
        }
        return true;
    }

    private float[] calculatePointerPosition(float f) {
        double d = (double) f;
        return new float[]{(float) (((double) this.mColorWheelRadius) * Math.cos(d)), (float) (((double) this.mColorWheelRadius) * Math.sin(d))};
    }

    public void addSVBar(NAGYRHAGA_SVBar nAGYRHAGA_SVBar) {
        this.mSVbar = nAGYRHAGA_SVBar;
        this.mSVbar.setColorPicker(this);
        this.mSVbar.setColor(this.mColor);
    }

    public void addOpacityBar(NAGYRHAGA_OpacityBar nAGYRHAGA_OpacityBar) {
        this.mNAGYRHAGAOpacityBar = nAGYRHAGA_OpacityBar;
        this.mNAGYRHAGAOpacityBar.setColorPicker(this);
        this.mNAGYRHAGAOpacityBar.setColor(this.mColor);
    }

    public void addSaturationBar(NAGYRHAGA_SaturationBar nAGYRHAGA_SaturationBar) {
        this.mNAGYRHAGASaturationBar = nAGYRHAGA_SaturationBar;
        this.mNAGYRHAGASaturationBar.setColorPicker(this);
        this.mNAGYRHAGASaturationBar.setColor(this.mColor);
    }

    public void addValueBar(NAGYRHAGA_ValueBar nAGYRHAGA_ValueBar) {
        this.mNAGYRHAGAValueBar = nAGYRHAGA_ValueBar;
        this.mNAGYRHAGAValueBar.setColorPicker(this);
        this.mNAGYRHAGAValueBar.setColor(this.mColor);
    }

    public void setNewCenterColor(int i) {
        this.mCenterNewColor = i;
        this.mCenterNewPaint.setColor(i);
        if (this.mCenterOldColor == 0) {
            this.mCenterOldColor = i;
            this.mCenterOldPaint.setColor(i);
        }
        if (!(this.onColorChangedListener == null || i == this.oldChangedListenerColor)) {
            this.onColorChangedListener.onColorChanged(i);
            this.oldChangedListenerColor = i;
        }
        invalidate();
    }

    public void setOldCenterColor(int i) {
        this.mCenterOldColor = i;
        this.mCenterOldPaint.setColor(i);
        invalidate();
    }

    public int getOldCenterColor() {
        return this.mCenterOldColor;
    }

    public void setShowOldCenterColor(boolean z) {
        this.mShowCenterOldColor = z;
        invalidate();
    }

    public boolean getShowOldCenterColor() {
        return this.mShowCenterOldColor;
    }

    public void changeOpacityBarColor(int i) {
        if (this.mNAGYRHAGAOpacityBar != null) {
            this.mNAGYRHAGAOpacityBar.setColor(i);
        }
    }

    public void changeSaturationBarColor(int i) {
        if (this.mNAGYRHAGASaturationBar != null) {
            this.mNAGYRHAGASaturationBar.setColor(i);
        }
    }

    public void changeValueBarColor(int i) {
        if (this.mNAGYRHAGAValueBar != null) {
            this.mNAGYRHAGAValueBar.setColor(i);
        }
    }

    public boolean hasOpacityBar() {
        return this.mNAGYRHAGAOpacityBar != null;
    }

    public boolean hasValueBar() {
        return this.mNAGYRHAGAValueBar != null;
    }

    public boolean hasSaturationBar() {
        return this.mNAGYRHAGASaturationBar != null;
    }

    public boolean hasSVBar() {
        return this.mSVbar != null;
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        Parcelable onSaveInstanceState = super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE_PARENT, onSaveInstanceState);
        bundle.putFloat(STATE_ANGLE, this.mAngle);
        bundle.putInt(STATE_OLD_COLOR, this.mCenterOldColor);
        bundle.putBoolean(STATE_SHOW_OLD_COLOR, this.mShowCenterOldColor);
        return bundle;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        Bundle bundle = (Bundle) parcelable;
        super.onRestoreInstanceState(bundle.getParcelable(STATE_PARENT));
        this.mAngle = bundle.getFloat(STATE_ANGLE);
        setOldCenterColor(bundle.getInt(STATE_OLD_COLOR));
        this.mShowCenterOldColor = bundle.getBoolean(STATE_SHOW_OLD_COLOR);
        int calculateColor = calculateColor(this.mAngle);
        this.mPointerColor.setColor(calculateColor);
        setNewCenterColor(calculateColor);
    }

    public void setTouchAnywhereOnColorWheelEnabled(boolean z) {
        this.mTouchAnywhereOnColorWheelEnabled = z;
    }

    public boolean getTouchAnywhereOnColorWheel() {
        return this.mTouchAnywhereOnColorWheelEnabled;
    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(SupportMenu.CATEGORY_MASK);
        canvas.drawOval(rectF, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        bitmap.recycle();
        return createBitmap;
    }
}
