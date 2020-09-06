package com.photoeditor.blendphotoeditor.blendermixer.holocolorpicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.photoeditor.blendphotoeditor.blendermixer.R;
/*import com.naygrh.multiplephotoblendermixerphotoeditor.R;*/

public class NAGYRHAGA_ValueBar extends View {
    private static final boolean ORIENTATION_DEFAULT = true;
    private static final boolean ORIENTATION_HORIZONTAL = true;
    private static final boolean ORIENTATION_VERTICAL = false;
    private static final String STATE_COLOR = "color";
    private static final String STATE_ORIENTATION = "orientation";
    private static final String STATE_PARENT = "parent";
    private static final String STATE_VALUE = "value";
    private int mBarLength;
    private Paint mBarPaint;
    private Paint mBarPointerHaloPaint;
    private int mBarPointerHaloRadius;
    private Paint mBarPointerPaint;
    private int mBarPointerPosition;
    private int mBarPointerRadius;
    private RectF mBarRect = new RectF();
    private int mBarThickness;
    private int mColor;
    private float[] mHSVColor = new float[3];
    private boolean mIsMovingPointer;
    private boolean mOrientation;
    private NAGYRHAGA_ColorPicker mPicker = null;
    private float mPosToSatFactor;
    private int mPreferredBarLength;
    private float mSatToPosFactor;
    private int oldChangedListenerValue;
    private OnValueChangedListener onValueChangedListener;
    private Shader shader;

    public interface OnValueChangedListener {
        void onValueChanged(int i);
    }

    public void setOnValueChangedListener(OnValueChangedListener onValueChangedListener2) {
        this.onValueChangedListener = onValueChangedListener2;
    }

    public OnValueChangedListener getOnValueChangedListener() {
        return this.onValueChangedListener;
    }

    public NAGYRHAGA_ValueBar(Context context) {
        super(context);
        init(null, 0);
    }

    public NAGYRHAGA_ValueBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet, 0);
    }

    public NAGYRHAGA_ValueBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet, i);
    }

    @SuppressLint("ResourceType")
    private void init(AttributeSet attributeSet, int i) {
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.ColorBars, i, 0);
        Resources resources = getContext().getResources();
        this.mBarThickness = obtainStyledAttributes.getDimensionPixelSize(4, resources.getDimensionPixelSize(R.dimen.bar_thickness));
        this.mBarLength = obtainStyledAttributes.getDimensionPixelSize(0, resources.getDimensionPixelSize(R.dimen.bar_length));
        this.mPreferredBarLength = this.mBarLength;
        this.mBarPointerRadius = obtainStyledAttributes.getDimensionPixelSize(3, resources.getDimensionPixelSize(R.dimen.bar_pointer_radius));
        this.mBarPointerHaloRadius = obtainStyledAttributes.getDimensionPixelSize(2, resources.getDimensionPixelSize(R.dimen.bar_pointer_halo_radius));
        this.mOrientation = obtainStyledAttributes.getBoolean(1, true);
        obtainStyledAttributes.recycle();
        this.mBarPaint = new Paint(1);
        this.mBarPaint.setShader(this.shader);
        this.mBarPointerPosition = this.mBarPointerHaloRadius;
        this.mBarPointerHaloPaint = new Paint(1);
        this.mBarPointerHaloPaint.setColor(-16777216);
        this.mBarPointerHaloPaint.setAlpha(80);
        this.mBarPointerPaint = new Paint(1);
        this.mBarPointerPaint.setColor(-8257792);
        this.mPosToSatFactor = 1.0f / ((float) this.mBarLength);
        this.mSatToPosFactor = ((float) this.mBarLength) / 1.0f;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3 = this.mPreferredBarLength + (this.mBarPointerHaloRadius * 2);
        if (!this.mOrientation) {
            i = i2;
        }
        int mode = MeasureSpec.getMode(i);
        int size = MeasureSpec.getSize(i);
        if (mode == 1073741824) {
            i3 = size;
        } else if (mode == Integer.MIN_VALUE) {
            i3 = Math.min(i3, size);
        }
        int i4 = this.mBarPointerHaloRadius * 2;
        this.mBarLength = i3 - i4;
        if (!this.mOrientation) {
            setMeasuredDimension(i4, this.mBarLength + i4);
        } else {
            setMeasuredDimension(this.mBarLength + i4, i4);
        }
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        super.onSizeChanged(i, i2, i3, i4);
        if (this.mOrientation) {
            i6 = this.mBarLength + this.mBarPointerHaloRadius;
            i5 = this.mBarThickness;
            this.mBarLength = i - (this.mBarPointerHaloRadius * 2);
            this.mBarRect.set((float) this.mBarPointerHaloRadius, (float) (this.mBarPointerHaloRadius - (this.mBarThickness / 2)), (float) (this.mBarLength + this.mBarPointerHaloRadius), (float) (this.mBarPointerHaloRadius + (this.mBarThickness / 2)));
        } else {
            i6 = this.mBarThickness;
            i5 = this.mBarLength + this.mBarPointerHaloRadius;
            this.mBarLength = i2 - (this.mBarPointerHaloRadius * 2);
            this.mBarRect.set((float) (this.mBarPointerHaloRadius - (this.mBarThickness / 2)), (float) this.mBarPointerHaloRadius, (float) (this.mBarPointerHaloRadius + (this.mBarThickness / 2)), (float) (this.mBarLength + this.mBarPointerHaloRadius));
        }
        if (!isInEditMode()) {
            LinearGradient linearGradient = new LinearGradient((float) this.mBarPointerHaloRadius, 0.0f, (float) i6, (float) i5, new int[]{Color.HSVToColor(255, this.mHSVColor), -16777216}, null, TileMode.CLAMP);
            this.shader = linearGradient;
        } else {
            LinearGradient linearGradient2 = new LinearGradient((float) this.mBarPointerHaloRadius, 0.0f, (float) i6, (float) i5, new int[]{-8257792, -16777216}, null, TileMode.CLAMP);
            this.shader = linearGradient2;
            Color.colorToHSV(-8257792, this.mHSVColor);
        }
        this.mBarPaint.setShader(this.shader);
        this.mPosToSatFactor = 1.0f / ((float) this.mBarLength);
        this.mSatToPosFactor = ((float) this.mBarLength) / 1.0f;
        float[] fArr = new float[3];
        Color.colorToHSV(this.mColor, fArr);
        if (!isInEditMode()) {
            this.mBarPointerPosition = Math.round((((float) this.mBarLength) - (this.mSatToPosFactor * fArr[2])) + ((float) this.mBarPointerHaloRadius));
        } else {
            this.mBarPointerPosition = this.mBarPointerHaloRadius;
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int i;
        int i2;
        canvas.drawRect(this.mBarRect, this.mBarPaint);
        if (this.mOrientation) {
            i2 = this.mBarPointerPosition;
            i = this.mBarPointerHaloRadius;
        } else {
            i2 = this.mBarPointerHaloRadius;
            i = this.mBarPointerPosition;
        }
        float f = (float) i2;
        float f2 = (float) i;
        canvas.drawCircle(f, f2, (float) this.mBarPointerHaloRadius, this.mBarPointerHaloPaint);
        canvas.drawCircle(f, f2, (float) this.mBarPointerRadius, this.mBarPointerPaint);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        float f;
        getParent().requestDisallowInterceptTouchEvent(true);
        if (this.mOrientation) {
            f = motionEvent.getX();
        } else {
            f = motionEvent.getY();
        }
        switch (motionEvent.getAction()) {
            case 0:
                this.mIsMovingPointer = true;
                if (f >= ((float) this.mBarPointerHaloRadius) && f <= ((float) (this.mBarPointerHaloRadius + this.mBarLength))) {
                    this.mBarPointerPosition = Math.round(f);
                    calculateColor(Math.round(f));
                    this.mBarPointerPaint.setColor(this.mColor);
                    invalidate();
                    break;
                }
            case 1:
                this.mIsMovingPointer = false;
                break;
            case 2:
                if (this.mIsMovingPointer) {
                    if (f >= ((float) this.mBarPointerHaloRadius) && f <= ((float) (this.mBarPointerHaloRadius + this.mBarLength))) {
                        this.mBarPointerPosition = Math.round(f);
                        calculateColor(Math.round(f));
                        this.mBarPointerPaint.setColor(this.mColor);
                        if (this.mPicker != null) {
                            this.mPicker.setNewCenterColor(this.mColor);
                            this.mPicker.changeOpacityBarColor(this.mColor);
                        }
                        invalidate();
                    } else if (f < ((float) this.mBarPointerHaloRadius)) {
                        this.mBarPointerPosition = this.mBarPointerHaloRadius;
                        this.mColor = Color.HSVToColor(this.mHSVColor);
                        this.mBarPointerPaint.setColor(this.mColor);
                        if (this.mPicker != null) {
                            this.mPicker.setNewCenterColor(this.mColor);
                            this.mPicker.changeOpacityBarColor(this.mColor);
                        }
                        invalidate();
                    } else if (f > ((float) (this.mBarPointerHaloRadius + this.mBarLength))) {
                        this.mBarPointerPosition = this.mBarPointerHaloRadius + this.mBarLength;
                        this.mColor = -16777216;
                        this.mBarPointerPaint.setColor(this.mColor);
                        if (this.mPicker != null) {
                            this.mPicker.setNewCenterColor(this.mColor);
                            this.mPicker.changeOpacityBarColor(this.mColor);
                        }
                        invalidate();
                    }
                }
                if (!(this.onValueChangedListener == null || this.oldChangedListenerValue == this.mColor)) {
                    this.onValueChangedListener.onValueChanged(this.mColor);
                    this.oldChangedListenerValue = this.mColor;
                    break;
                }
                break;
        }
        return true;
    }

    public void setColor(int i) {
        int i2;
        int i3;
        if (this.mOrientation) {
            i3 = this.mBarLength + this.mBarPointerHaloRadius;
            i2 = this.mBarThickness;
        } else {
            i3 = this.mBarThickness;
            i2 = this.mBarLength + this.mBarPointerHaloRadius;
        }
        Color.colorToHSV(i, this.mHSVColor);
        LinearGradient linearGradient = new LinearGradient((float) this.mBarPointerHaloRadius, 0.0f, (float) i3, (float) i2, new int[]{i, -16777216}, null, TileMode.CLAMP);
        this.shader = linearGradient;
        this.mBarPaint.setShader(this.shader);
        calculateColor(this.mBarPointerPosition);
        this.mBarPointerPaint.setColor(this.mColor);
        if (this.mPicker != null) {
            this.mPicker.setNewCenterColor(this.mColor);
            if (this.mPicker.hasOpacityBar()) {
                this.mPicker.changeOpacityBarColor(this.mColor);
            }
        }
        invalidate();
    }

    public void setValue(float f) {
        this.mBarPointerPosition = Math.round((((float) this.mBarLength) - (this.mSatToPosFactor * f)) + ((float) this.mBarPointerHaloRadius));
        calculateColor(this.mBarPointerPosition);
        this.mBarPointerPaint.setColor(this.mColor);
        if (this.mPicker != null) {
            this.mPicker.setNewCenterColor(this.mColor);
            this.mPicker.changeOpacityBarColor(this.mColor);
        }
        invalidate();
    }

    private void calculateColor(int i) {
        int i2 = i - this.mBarPointerHaloRadius;
        if (i2 < 0) {
            i2 = 0;
        } else if (i2 > this.mBarLength) {
            i2 = this.mBarLength;
        }
        this.mColor = Color.HSVToColor(new float[]{this.mHSVColor[0], this.mHSVColor[1], 1.0f - (this.mPosToSatFactor * ((float) i2))});
    }

    public int getColor() {
        return this.mColor;
    }

    public void setColorPicker(NAGYRHAGA_ColorPicker nAGYRHAGA_ColorPicker) {
        this.mPicker = nAGYRHAGA_ColorPicker;
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        Parcelable onSaveInstanceState = super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE_PARENT, onSaveInstanceState);
        bundle.putFloatArray(STATE_COLOR, this.mHSVColor);
        float[] fArr = new float[3];
        Color.colorToHSV(this.mColor, fArr);
        bundle.putFloat(STATE_VALUE, fArr[2]);
        return bundle;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        Bundle bundle = (Bundle) parcelable;
        super.onRestoreInstanceState(bundle.getParcelable(STATE_PARENT));
        setColor(Color.HSVToColor(bundle.getFloatArray(STATE_COLOR)));
        setValue(bundle.getFloat(STATE_VALUE));
    }
}
