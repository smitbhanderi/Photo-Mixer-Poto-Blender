package com.photoeditor.blendphotoeditor.blendermixer.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.FrameLayout;

import com.photoeditor.blendphotoeditor.blendermixer.R;
/*import com.naygrh.multiplephotoblendermixerphotoeditor.R;*/

public class NAGYRHAGA_FadingEdgeLayout extends FrameLayout {
    private static final int DEFAULT_GRADIENT_SIZE_DP = 80;
    private static final int DIRTY_FLAG_BOTTOM = 2;
    private static final int DIRTY_FLAG_LEFT = 4;
    private static final int DIRTY_FLAG_RIGHT = 8;
    private static final int DIRTY_FLAG_TOP = 1;
    private static final int[] FADE_COLORS = {0, -16777216};
    private static final int[] FADE_COLORS_REVERSE = {-16777216, 0};
    public static final int FADE_EDGE_BOTTOM = 2;
    public static final int FADE_EDGE_LEFT = 4;
    public static final int FADE_EDGE_RIGHT = 8;
    public static final int FADE_EDGE_TOP = 1;
    private boolean fadeBottom;
    private boolean fadeLeft;
    private boolean fadeRight;
    private boolean fadeTop;
    private int gradientDirtyFlags;
    private Paint gradientPaintBottom;
    private Paint gradientPaintLeft;
    private Paint gradientPaintRight;
    private Paint gradientPaintTop;
    private Rect gradientRectBottom;
    private Rect gradientRectLeft;
    private Rect gradientRectRight;
    private Rect gradientRectTop;
    private int gradientSizeBottom;
    private int gradientSizeLeft;
    private int gradientSizeRight;
    private int gradientSizeTop;
    public int mF = 0;

    public NAGYRHAGA_FadingEdgeLayout(Context context) {
        super(context);
        init(null, 0);
    }

    public NAGYRHAGA_FadingEdgeLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet, 0);
    }

    public NAGYRHAGA_FadingEdgeLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet, 0);
    }

    @SuppressLint("ResourceType")
    private void init(AttributeSet attributeSet, int i) {
        int applyDimension = (int) TypedValue.applyDimension(1, 80.0f, getResources().getDisplayMetrics());
        if (attributeSet != null) {
            boolean z = false;
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.NAGYRHAGA_FadingEdgeLayout, i, 0);
            int i2 = obtainStyledAttributes.getInt(0, 0);
            this.fadeTop = (i2 & 1) == 1;
            this.fadeBottom = (i2 & 2) == 2;
            this.fadeLeft = (i2 & 4) == 4;
            if ((i2 & 8) == 8) {
                z = true;
            }
            this.fadeRight = z;
            this.gradientSizeTop = obtainStyledAttributes.getDimensionPixelSize(4, applyDimension);
            this.gradientSizeBottom = obtainStyledAttributes.getDimensionPixelSize(1, applyDimension);
            this.gradientSizeLeft = obtainStyledAttributes.getDimensionPixelSize(2, applyDimension);
            this.gradientSizeRight = obtainStyledAttributes.getDimensionPixelSize(3, applyDimension);
            if (this.fadeTop && this.gradientSizeTop > 0) {
                this.gradientDirtyFlags |= 1;
            }
            if (this.fadeLeft && this.gradientSizeLeft > 0) {
                this.gradientDirtyFlags |= 4;
            }
            if (this.fadeBottom && this.gradientSizeBottom > 0) {
                this.gradientDirtyFlags |= 2;
            }
            if (this.fadeRight && this.gradientSizeRight > 0) {
                this.gradientDirtyFlags |= 8;
            }
            obtainStyledAttributes.recycle();
        } else {
            this.gradientSizeRight = applyDimension;
            this.gradientSizeLeft = applyDimension;
            this.gradientSizeBottom = applyDimension;
            this.gradientSizeTop = applyDimension;
        }
        PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(Mode.DST_IN);
        this.gradientPaintTop = new Paint(1);
        this.gradientPaintTop.setXfermode(porterDuffXfermode);
        this.gradientPaintBottom = new Paint(1);
        this.gradientPaintBottom.setXfermode(porterDuffXfermode);
        this.gradientPaintLeft = new Paint(1);
        this.gradientPaintLeft.setXfermode(porterDuffXfermode);
        this.gradientPaintRight = new Paint(1);
        this.gradientPaintRight.setXfermode(porterDuffXfermode);
        this.gradientRectTop = new Rect();
        this.gradientRectLeft = new Rect();
        this.gradientRectBottom = new Rect();
        this.gradientRectRight = new Rect();
    }

    public void setFadeSizes(int i, int i2, int i3, int i4) {
        this.mF = i;
        if (this.gradientSizeTop != i) {
            this.gradientSizeTop = i;
            this.gradientDirtyFlags |= 1;
        }
        if (this.gradientSizeLeft != i2) {
            this.gradientSizeLeft = i2;
            this.gradientDirtyFlags |= 4;
        }
        if (this.gradientSizeBottom != i3) {
            this.gradientSizeBottom = i3;
            this.gradientDirtyFlags |= 2;
        }
        if (this.gradientSizeRight != i4) {
            this.gradientSizeRight = i4;
            this.gradientDirtyFlags |= 8;
        }
        if (this.gradientDirtyFlags != 0) {
            invalidate();
        }
    }

    public int getFade() {
        return this.mF;
    }

    public void setFadeEdges(boolean z, boolean z2, boolean z3, boolean z4) {
        if (this.fadeTop != z) {
            this.fadeTop = z;
            this.gradientDirtyFlags |= 1;
        }
        if (this.fadeLeft != z2) {
            this.fadeLeft = z2;
            this.gradientDirtyFlags |= 4;
        }
        if (this.fadeBottom != z3) {
            this.fadeBottom = z3;
            this.gradientDirtyFlags |= 2;
        }
        if (this.fadeRight != z4) {
            this.fadeRight = z4;
            this.gradientDirtyFlags |= 8;
        }
        if (this.gradientDirtyFlags != 0) {
            invalidate();
        }
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        if (getPaddingLeft() != i) {
            this.gradientDirtyFlags |= 4;
        }
        if (getPaddingTop() != i2) {
            this.gradientDirtyFlags |= 1;
        }
        if (getPaddingRight() != i3) {
            this.gradientDirtyFlags |= 8;
        }
        if (getPaddingBottom() != i4) {
            this.gradientDirtyFlags |= 2;
        }
        super.setPadding(i, i2, i3, i4);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3) {
            this.gradientDirtyFlags |= 4;
            this.gradientDirtyFlags |= 8;
        }
        if (i2 != i4) {
            this.gradientDirtyFlags |= 1;
            this.gradientDirtyFlags |= 2;
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        boolean z = this.fadeTop || this.fadeBottom || this.fadeLeft || this.fadeRight;
        if (getVisibility() == GONE || width == 0 || height == 0 || !z) {
            super.dispatchDraw(canvas);
            return;
        }
        if ((this.gradientDirtyFlags & 1) == 1) {
            this.gradientDirtyFlags &= -2;
            initTopGradient();
        }
        if ((this.gradientDirtyFlags & 4) == 4) {
            this.gradientDirtyFlags &= -5;
            initLeftGradient();
        }
        if ((this.gradientDirtyFlags & 2) == 2) {
            this.gradientDirtyFlags &= -3;
            initBottomGradient();
        }
        if ((this.gradientDirtyFlags & 8) == 8) {
            this.gradientDirtyFlags &= -9;
            initRightGradient();
        }
        @SuppressLint("WrongConstant") int saveLayer = canvas.saveLayer(0.0f, 0.0f, (float) getWidth(), (float) getHeight(), null, 31);
        super.dispatchDraw(canvas);
        if (this.fadeTop && this.gradientSizeTop > 0) {
            canvas.drawRect(this.gradientRectTop, this.gradientPaintTop);
        }
        if (this.fadeBottom && this.gradientSizeBottom > 0) {
            canvas.drawRect(this.gradientRectBottom, this.gradientPaintBottom);
        }
        if (this.fadeLeft && this.gradientSizeLeft > 0) {
            canvas.drawRect(this.gradientRectLeft, this.gradientPaintLeft);
        }
        if (this.fadeRight && this.gradientSizeRight > 0) {
            canvas.drawRect(this.gradientRectRight, this.gradientPaintRight);
        }
        canvas.restoreToCount(saveLayer);
    }

    private void initTopGradient() {
        int min = Math.min(this.gradientSizeTop, (getHeight() - getPaddingTop()) - getPaddingBottom());
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int i = min + paddingTop;
        this.gradientRectTop.set(paddingLeft, paddingTop, getWidth() - getPaddingRight(), i);
        float f = (float) paddingLeft;
        LinearGradient linearGradient = new LinearGradient(f, (float) paddingTop, f, (float) i, FADE_COLORS, null, TileMode.CLAMP);
        this.gradientPaintTop.setShader(linearGradient);
    }

    private void initLeftGradient() {
        int min = Math.min(this.gradientSizeLeft, (getWidth() - getPaddingLeft()) - getPaddingRight());
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int i = min + paddingLeft;
        this.gradientRectLeft.set(paddingLeft, paddingTop, i, getHeight() - getPaddingBottom());
        float f = (float) paddingTop;
        LinearGradient linearGradient = new LinearGradient((float) paddingLeft, f, (float) i, f, FADE_COLORS, null, TileMode.CLAMP);
        this.gradientPaintLeft.setShader(linearGradient);
    }

    private void initBottomGradient() {
        int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
        int min = Math.min(this.gradientSizeBottom, height);
        int paddingLeft = getPaddingLeft();
        int paddingTop = (getPaddingTop() + height) - min;
        int i = min + paddingTop;
        this.gradientRectBottom.set(paddingLeft, paddingTop, getWidth() - getPaddingRight(), i);
        float f = (float) paddingLeft;
        LinearGradient linearGradient = new LinearGradient(f, (float) paddingTop, f, (float) i, FADE_COLORS_REVERSE, null, TileMode.CLAMP);
        this.gradientPaintBottom.setShader(linearGradient);
    }

    private void initRightGradient() {
        int width = (getWidth() - getPaddingLeft()) - getPaddingRight();
        int min = Math.min(this.gradientSizeRight, width);
        int paddingLeft = (getPaddingLeft() + width) - min;
        int paddingTop = getPaddingTop();
        int i = min + paddingLeft;
        this.gradientRectRight.set(paddingLeft, paddingTop, i, getHeight() - getPaddingBottom());
        float f = (float) paddingTop;
        LinearGradient linearGradient = new LinearGradient((float) paddingLeft, f, (float) i, f, FADE_COLORS_REVERSE, null, TileMode.CLAMP);
        this.gradientPaintRight.setShader(linearGradient);
    }
}
