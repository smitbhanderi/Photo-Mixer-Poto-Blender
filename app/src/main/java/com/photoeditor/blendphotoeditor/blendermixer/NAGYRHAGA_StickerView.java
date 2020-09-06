package com.photoeditor.blendphotoeditor.blendermixer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.MotionEventCompat;

/*import android.support.v4.view.MotionEventCompat;*/

public class NAGYRHAGA_StickerView extends AppCompatImageView {
    private static final float BITMAP_SCALE = 0.7f;
    private static final String TAG = "StickerView";
    private float MAX_SCALE = 1.2f;
    private float MIN_SCALE = 0.5f;
    private Bitmap deleteBitmap;
    private int deleteBitmapHeight;
    private int deleteBitmapWidth;
    private DisplayMetrics dm;
    private Rect dst_delete;
    private Rect dst_flipV;
    private Rect dst_resize;
    private Rect dst_top;
    private Bitmap flipVBitmap;
    private int flipVBitmapHeight;
    private int flipVBitmapWidth;
    private double halfDiagonalLength;
    private boolean isHorizonMirror = false;
    private boolean isInEdit = true;
    private boolean isInResize = false;
    private boolean isInSide;
    private boolean isPointerDown = false;
    private float lastLength;
    private float lastRotateDegree;
    private float lastX;
    private float lastY;
    private Paint localPaint;
    private Bitmap mBitmap;
    private int mScreenHeight;
    private int mScreenwidth;
    private Matrix matrix = new Matrix();
    private PointF mid = new PointF();
    private float oldDis;
    private OperationListener operationListener;
    private float oringinWidth = 0.0f;
    private final float pointerLimitDis = 20.0f;
    private final float pointerZoomCoeff = 0.09f;
    private Bitmap resizeBitmap;
    private int resizeBitmapHeight;
    private int resizeBitmapWidth;
    private final long stickerId = 0;
    private Bitmap topBitmap;
    private int topBitmapHeight;
    private int topBitmapWidth;

    public interface OperationListener {
        void onDeleteClick();

        void onEdit(NAGYRHAGA_StickerView nAGYRHAGA_StickerView);

        void onTop(NAGYRHAGA_StickerView nAGYRHAGA_StickerView);
    }

    public NAGYRHAGA_StickerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public NAGYRHAGA_StickerView(Context context) {
        super(context);
        init();
    }

    public NAGYRHAGA_StickerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        this.dst_delete = new Rect();
        this.dst_resize = new Rect();
        this.dst_flipV = new Rect();
        this.dst_top = new Rect();
        this.localPaint = new Paint();
        this.localPaint.setColor(getResources().getColor(R.color.white));
        this.localPaint.setAntiAlias(true);
        this.localPaint.setDither(true);
        this.localPaint.setStyle(Style.STROKE);
        this.localPaint.setStrokeWidth(8.0f);
        this.dm = getResources().getDisplayMetrics();
        this.mScreenwidth = this.dm.widthPixels;
        this.mScreenHeight = this.dm.heightPixels;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        Canvas canvas2 = canvas;
        if (this.mBitmap != null) {
            float[] fArr = new float[9];
            this.matrix.getValues(fArr);
            float f = fArr[2] + (fArr[0] * 0.0f) + (fArr[1] * 0.0f);
            float f2 = (fArr[3] * 0.0f) + (fArr[4] * 0.0f) + fArr[5];
            float width = (fArr[0] * ((float) this.mBitmap.getWidth())) + (fArr[1] * 0.0f) + fArr[2];
            float width2 = (fArr[3] * ((float) this.mBitmap.getWidth())) + (fArr[4] * 0.0f) + fArr[5];
            float height = (fArr[0] * 0.0f) + (fArr[1] * ((float) this.mBitmap.getHeight())) + fArr[2];
            float height2 = (fArr[3] * 0.0f) + (fArr[4] * ((float) this.mBitmap.getHeight())) + fArr[5];
            float width3 = (fArr[0] * ((float) this.mBitmap.getWidth())) + (fArr[1] * ((float) this.mBitmap.getHeight())) + fArr[2];
            float width4 = (fArr[3] * ((float) this.mBitmap.getWidth())) + (fArr[4] * ((float) this.mBitmap.getHeight())) + fArr[5];
            canvas.save();
            canvas2.drawBitmap(this.mBitmap, this.matrix, null);
            this.dst_delete.left = (int) (width - ((float) (this.deleteBitmapWidth / 2)));
            this.dst_delete.right = (int) (((float) (this.deleteBitmapWidth / 2)) + width);
            this.dst_delete.top = (int) (width2 - ((float) (this.deleteBitmapHeight / 2)));
            this.dst_delete.bottom = (int) (((float) (this.deleteBitmapHeight / 2)) + width2);
            this.dst_resize.left = (int) (width3 - ((float) (this.resizeBitmapWidth / 2)));
            this.dst_resize.right = (int) (width3 + ((float) (this.resizeBitmapWidth / 2)));
            this.dst_resize.top = (int) (width4 - ((float) (this.resizeBitmapHeight / 2)));
            this.dst_resize.bottom = (int) (((float) (this.resizeBitmapHeight / 2)) + width4);
            this.dst_top.left = (int) (f - ((float) (this.flipVBitmapWidth / 2)));
            this.dst_top.right = (int) (((float) (this.flipVBitmapWidth / 2)) + f);
            this.dst_top.top = (int) (f2 - ((float) (this.flipVBitmapHeight / 2)));
            this.dst_top.bottom = (int) (((float) (this.flipVBitmapHeight / 2)) + f2);
            this.dst_flipV.left = (int) (height - ((float) (this.topBitmapWidth / 2)));
            this.dst_flipV.right = (int) (((float) (this.topBitmapWidth / 2)) + height);
            this.dst_flipV.top = (int) (height2 - ((float) (this.topBitmapHeight / 2)));
            this.dst_flipV.bottom = (int) (((float) (this.topBitmapHeight / 2)) + height2);
            if (this.isInEdit) {
                Canvas canvas3 = canvas2;
                canvas3.drawLine(f, f2, width, width2, this.localPaint);
                float f3 = width3;
                float f4 = width4;
                canvas3.drawLine(width, width2, f3, f4, this.localPaint);
                float f5 = height;
                float f6 = height2;
                canvas3.drawLine(f5, f6, f3, f4, this.localPaint);
                canvas3.drawLine(f5, f6, f, f2, this.localPaint);
                canvas2.drawBitmap(this.deleteBitmap, null, this.dst_delete, null);
                canvas2.drawBitmap(this.resizeBitmap, null, this.dst_resize, null);
                canvas2.drawBitmap(this.flipVBitmap, null, this.dst_flipV, null);
                canvas2.drawBitmap(this.topBitmap, null, this.dst_top, null);
            }
            canvas.restore();
        }
    }

    public void setImageResource(int i) {
        setBitmap(BitmapFactory.decodeResource(getResources(), i));
    }

    public void setBitmap(Bitmap bitmap) {
        this.matrix.reset();
        this.mBitmap = bitmap;
        setDiagonalLength();
        initBitmaps();
        int width = this.mBitmap.getWidth();
        int height = this.mBitmap.getHeight();
        this.oringinWidth = (float) width;
        float f = (this.MIN_SCALE + this.MAX_SCALE) / 2.0f;
        int i = width / 2;
        int i2 = height / 2;
        this.matrix.postScale(f, f, (float) i, (float) i2);
        this.matrix.postTranslate((float) ((this.mScreenwidth / 2) - i), (float) ((this.mScreenwidth / 2) - i2));
        invalidate();
    }

    private void setDiagonalLength() {
        this.halfDiagonalLength = Math.hypot((double) this.mBitmap.getWidth(), (double) this.mBitmap.getHeight()) / 2.0d;
    }

    private void initBitmaps() {
        if (this.mBitmap.getWidth() >= this.mBitmap.getHeight()) {
            float f = (float) (this.mScreenwidth / 8);
            if (((float) this.mBitmap.getWidth()) < f) {
                this.MIN_SCALE = 1.0f;
            } else {
                this.MIN_SCALE = (f * 1.0f) / ((float) this.mBitmap.getWidth());
            }
            if (this.mBitmap.getWidth() > this.mScreenwidth) {
                this.MAX_SCALE = 1.0f;
            } else {
                this.MAX_SCALE = (((float) this.mScreenwidth) * 1.0f) / ((float) this.mBitmap.getWidth());
            }
        } else {
            float f2 = (float) (this.mScreenwidth / 8);
            if (((float) this.mBitmap.getHeight()) < f2) {
                this.MIN_SCALE = 1.0f;
            } else {
                this.MIN_SCALE = (f2 * 1.0f) / ((float) this.mBitmap.getHeight());
            }
            if (this.mBitmap.getHeight() > this.mScreenwidth) {
                this.MAX_SCALE = 1.0f;
            } else {
                this.MAX_SCALE = (((float) this.mScreenwidth) * 1.0f) / ((float) this.mBitmap.getHeight());
            }
        }
        this.topBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.overlap_unpress);
        this.deleteBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cancel_unpress);
        this.flipVBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.vertical_unpress);
        this.resizeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.resize_unpress);
        this.deleteBitmapWidth = (int) (((float) this.deleteBitmap.getWidth()) * BITMAP_SCALE);
        this.deleteBitmapHeight = (int) (((float) this.deleteBitmap.getHeight()) * BITMAP_SCALE);
        this.resizeBitmapWidth = (int) (((float) this.resizeBitmap.getWidth()) * BITMAP_SCALE);
        this.resizeBitmapHeight = (int) (((float) this.resizeBitmap.getHeight()) * BITMAP_SCALE);
        this.flipVBitmapWidth = (int) (((float) this.flipVBitmap.getWidth()) * BITMAP_SCALE);
        this.flipVBitmapHeight = (int) (((float) this.flipVBitmap.getHeight()) * BITMAP_SCALE);
        this.topBitmapWidth = (int) (((float) this.topBitmap.getWidth()) * BITMAP_SCALE);
        this.topBitmapHeight = (int) (((float) this.topBitmap.getHeight()) * BITMAP_SCALE);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
        boolean z = true;
        if (actionMasked != 5) {
            switch (actionMasked) {
                case 0:
                    if (!isInButton(motionEvent, this.dst_delete)) {
                        if (!isInResize(motionEvent)) {
                            if (!isInButton(motionEvent, this.dst_flipV)) {
                                if (!isInButton(motionEvent, this.dst_top)) {
                                    if (!isInBitmap(motionEvent)) {
                                        z = false;
                                        break;
                                    } else {
                                        this.isInSide = true;
                                        this.lastX = motionEvent.getX(0);
                                        this.lastY = motionEvent.getY(0);
                                        break;
                                    }
                                } else {
                                    bringToFront();
                                    if (this.operationListener != null) {
                                        this.operationListener.onTop(this);
                                        break;
                                    }
                                }
                            } else {
                                PointF pointF = new PointF();
                                midDiagonalPoint(pointF);
                                this.matrix.postScale(-1.0f, 1.0f, pointF.x, pointF.y);
                                this.isHorizonMirror = !this.isHorizonMirror;
                                invalidate();
                                break;
                            }
                        } else {
                            this.isInResize = true;
                            this.lastRotateDegree = rotationToStartPoint(motionEvent);
                            midPointToStartPoint(motionEvent);
                            this.lastLength = diagonalLength(motionEvent);
                            break;
                        }
                    } else if (this.operationListener != null) {
                        this.operationListener.onDeleteClick();
                        break;
                    }
                    break;
                case 1:
                case 3:
                    this.isInResize = false;
                    this.isInSide = false;
                    this.isPointerDown = false;
                    break;
                case 2:
                    if (!this.isPointerDown) {
                        if (!this.isInResize) {
                            if (this.isInSide) {
                                float x = motionEvent.getX(0);
                                float y = motionEvent.getY(0);
                                this.matrix.postTranslate(x - this.lastX, y - this.lastY);
                                this.lastX = x;
                                this.lastY = y;
                                invalidate();
                                break;
                            }
                        } else {
                            this.matrix.postRotate((rotationToStartPoint(motionEvent) - this.lastRotateDegree) * 2.0f, this.mid.x, this.mid.y);
                            this.lastRotateDegree = rotationToStartPoint(motionEvent);
                            float diagonalLength = diagonalLength(motionEvent) / this.lastLength;
                            if ((((double) diagonalLength(motionEvent)) / this.halfDiagonalLength > ((double) this.MIN_SCALE) || diagonalLength >= 1.0f) && (((double) diagonalLength(motionEvent)) / this.halfDiagonalLength < ((double) this.MAX_SCALE) || diagonalLength <= 1.0f)) {
                                this.lastLength = diagonalLength(motionEvent);
                            } else {
                                if (!isInResize(motionEvent)) {
                                    this.isInResize = false;
                                }
                                diagonalLength = 1.0f;
                            }
                            this.matrix.postScale(diagonalLength, diagonalLength, this.mid.x, this.mid.y);
                            invalidate();
                            break;
                        }
                    } else {
                        float spacing = spacing(motionEvent);
                        float f = (spacing == 0.0f || spacing < 20.0f) ? 1.0f : (((spacing / this.oldDis) - 1.0f) * 0.09f) + 1.0f;
                        float abs = (((float) Math.abs(this.dst_flipV.left - this.dst_resize.left)) * f) / this.oringinWidth;
                        if ((abs > this.MIN_SCALE || f >= 1.0f) && (abs < this.MAX_SCALE || f <= 1.0f)) {
                            this.lastLength = diagonalLength(motionEvent);
                        } else {
                            f = 1.0f;
                        }
                        this.matrix.postScale(f, f, this.mid.x, this.mid.y);
                        invalidate();
                        break;
                    }
                    break;
            }
        } else {
            if (spacing(motionEvent) > 20.0f) {
                this.oldDis = spacing(motionEvent);
                this.isPointerDown = true;
                midPointToStartPoint(motionEvent);
            } else {
                this.isPointerDown = false;
            }
            this.isInSide = false;
            this.isInResize = false;
        }
        if (z && this.operationListener != null) {
            this.operationListener.onEdit(this);
        }
        return z;
    }

    public NAGYRHAGA_StickerPropertyModel calculate(NAGYRHAGA_StickerPropertyModel nAGYRHAGA_StickerPropertyModel) {
        float[] fArr = new float[9];
        this.matrix.getValues(fArr);
        float f = fArr[2];
        float f2 = fArr[5];
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("tx : ");
        sb.append(f);
        sb.append(" ty : ");
        sb.append(f2);
        Log.d(str, sb.toString());
        float f3 = fArr[0];
        float f4 = fArr[3];
        float sqrt = (float) Math.sqrt((double) ((f3 * f3) + (f4 * f4)));
        String str2 = TAG;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("rScale : ");
        sb2.append(sqrt);
        Log.d(str2, sb2.toString());
        float round = (float) Math.round(Math.atan2((double) fArr[1], (double) fArr[0]) * 57.29577951308232d);
        String str3 = TAG;
        StringBuilder sb3 = new StringBuilder();
        sb3.append("rAngle : ");
        sb3.append(round);
        Log.d(str3, sb3.toString());
        PointF pointF = new PointF();
        midDiagonalPoint(pointF);
        String str4 = TAG;
        StringBuilder sb4 = new StringBuilder();
        sb4.append(" width  : ");
        sb4.append(((float) this.mBitmap.getWidth()) * sqrt);
        sb4.append(" height ");
        sb4.append(((float) this.mBitmap.getHeight()) * sqrt);
        Log.d(str4, sb4.toString());
        float f5 = pointF.x;
        float f6 = pointF.y;
        String str5 = TAG;
        StringBuilder sb5 = new StringBuilder();
        sb5.append("midX : ");
        sb5.append(f5);
        sb5.append(" midY : ");
        sb5.append(f6);
        Log.d(str5, sb5.toString());
        nAGYRHAGA_StickerPropertyModel.setDegree((float) Math.toRadians((double) round));
        nAGYRHAGA_StickerPropertyModel.setScaling((((float) this.mBitmap.getWidth()) * sqrt) / ((float) this.mScreenwidth));
        nAGYRHAGA_StickerPropertyModel.setxLocation(f5 / ((float) this.mScreenwidth));
        nAGYRHAGA_StickerPropertyModel.setyLocation(f6 / ((float) this.mScreenwidth));
        nAGYRHAGA_StickerPropertyModel.setStickerId(this.stickerId);
        if (this.isHorizonMirror) {
            nAGYRHAGA_StickerPropertyModel.setHorizonMirror(1);
        } else {
            nAGYRHAGA_StickerPropertyModel.setHorizonMirror(2);
        }
        return nAGYRHAGA_StickerPropertyModel;
    }

    private boolean isInBitmap(MotionEvent motionEvent) {
        MotionEvent motionEvent2 = motionEvent;
        float[] fArr = new float[9];
        this.matrix.getValues(fArr);
        float f = (fArr[3] * 0.0f) + (fArr[4] * 0.0f) + fArr[5];
        float width = (fArr[3] * ((float) this.mBitmap.getWidth())) + (fArr[4] * 0.0f) + fArr[5];
        float height = (fArr[3] * 0.0f) + (fArr[4] * ((float) this.mBitmap.getHeight())) + fArr[5];
        float width2 = (fArr[0] * ((float) this.mBitmap.getWidth())) + (fArr[1] * ((float) this.mBitmap.getHeight())) + fArr[2];
        float width3 = (fArr[3] * ((float) this.mBitmap.getWidth())) + (fArr[4] * ((float) this.mBitmap.getHeight())) + fArr[5];
        return pointInRect(new float[]{(fArr[0] * 0.0f) + (fArr[1] * 0.0f) + fArr[2], (fArr[0] * ((float) this.mBitmap.getWidth())) + (fArr[1] * 0.0f) + fArr[2], width2, (fArr[0] * 0.0f) + (fArr[1] * ((float) this.mBitmap.getHeight())) + fArr[2]}, new float[]{f, width, width3, height}, motionEvent2.getX(0), motionEvent2.getY(0));
    }

    private boolean pointInRect(float[] fArr, float[] fArr2, float f, float f2) {
        double hypot = Math.hypot((double) (fArr[0] - fArr[1]), (double) (fArr2[0] - fArr2[1]));
        double hypot2 = Math.hypot((double) (fArr[1] - fArr[2]), (double) (fArr2[1] - fArr2[2]));
        double hypot3 = Math.hypot((double) (fArr[3] - fArr[2]), (double) (fArr2[3] - fArr2[2]));
        double d = hypot2;
        double hypot4 = Math.hypot((double) (fArr[0] - fArr[3]), (double) (fArr2[0] - fArr2[3]));
        double hypot5 = Math.hypot((double) (f - fArr[0]), (double) (f2 - fArr2[0]));
        double d2 = hypot;
        double hypot6 = Math.hypot((double) (f - fArr[1]), (double) (f2 - fArr2[1]));
        double d3 = hypot4;
        double hypot7 = Math.hypot((double) (f - fArr[2]), (double) (f2 - fArr2[2]));
        double hypot8 = Math.hypot((double) (f - fArr[3]), (double) (f2 - fArr2[3]));
        double d4 = ((d2 + hypot5) + hypot6) / 2.0d;
        double d5 = ((d + hypot6) + hypot7) / 2.0d;
        double d6 = ((hypot3 + hypot7) + hypot8) / 2.0d;
        double d7 = ((d3 + hypot8) + hypot5) / 2.0d;
        return Math.abs((d2 * d) - (((Math.sqrt((d4 - hypot6) * (((d4 - d2) * d4) * (d4 - hypot5))) + Math.sqrt((((d5 - d) * d5) * (d5 - hypot6)) * (d5 - hypot7))) + Math.sqrt((((d6 - hypot3) * d6) * (d6 - hypot7)) * (d6 - hypot8))) + Math.sqrt((((d7 - d3) * d7) * (d7 - hypot8)) * (d7 - hypot5)))) < 0.5d;
    }

    private boolean isInButton(MotionEvent motionEvent, Rect rect) {
        int i = rect.left;
        int i2 = rect.right;
        int i3 = rect.top;
        int i4 = rect.bottom;
        if (motionEvent.getX(0) < ((float) i) || motionEvent.getX(0) > ((float) i2) || motionEvent.getY(0) < ((float) i3) || motionEvent.getY(0) > ((float) i4)) {
            return false;
        }
        return true;
    }

    private boolean isInResize(MotionEvent motionEvent) {
        int i = this.dst_resize.top - 20;
        int i2 = this.dst_resize.right + 20;
        int i3 = this.dst_resize.bottom + 20;
        if (motionEvent.getX(0) < ((float) (this.dst_resize.left - 20)) || motionEvent.getX(0) > ((float) i2) || motionEvent.getY(0) < ((float) i) || motionEvent.getY(0) > ((float) i3)) {
            return false;
        }
        return true;
    }

    private void midPointToStartPoint(MotionEvent motionEvent) {
        float[] fArr = new float[9];
        this.matrix.getValues(fArr);
        this.mid.set(((((fArr[0] * 0.0f) + (fArr[1] * 0.0f)) + fArr[2]) + motionEvent.getX(0)) / 2.0f, ((((fArr[3] * 0.0f) + (fArr[4] * 0.0f)) + fArr[5]) + motionEvent.getY(0)) / 2.0f);
    }

    private void midDiagonalPoint(PointF pointF) {
        float[] fArr = new float[9];
        this.matrix.getValues(fArr);
        float width = (fArr[0] * ((float) this.mBitmap.getWidth())) + (fArr[1] * ((float) this.mBitmap.getHeight())) + fArr[2];
        pointF.set(((((fArr[0] * 0.0f) + (fArr[1] * 0.0f)) + fArr[2]) + width) / 2.0f, ((((fArr[3] * 0.0f) + (fArr[4] * 0.0f)) + fArr[5]) + (((fArr[3] * ((float) this.mBitmap.getWidth())) + (fArr[4] * ((float) this.mBitmap.getHeight()))) + fArr[5])) / 2.0f);
    }

    private float rotationToStartPoint(MotionEvent motionEvent) {
        float[] fArr = new float[9];
        this.matrix.getValues(fArr);
        return (float) Math.toDegrees(Math.atan2((double) (motionEvent.getY(0) - (((fArr[3] * 0.0f) + (fArr[4] * 0.0f)) + fArr[5])), (double) (motionEvent.getX(0) - (((fArr[0] * 0.0f) + (fArr[1] * 0.0f)) + fArr[2]))));
    }

    private float diagonalLength(MotionEvent motionEvent) {
        return (float) Math.hypot((double) (motionEvent.getX(0) - this.mid.x), (double) (motionEvent.getY(0) - this.mid.y));
    }

    private float spacing(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() != 2) {
            return 0.0f;
        }
        float x = motionEvent.getX(0) - motionEvent.getX(1);
        float y = motionEvent.getY(0) - motionEvent.getY(1);
        return (float) Math.sqrt((double) ((x * x) + (y * y)));
    }

    public void setOperationListener(OperationListener operationListener2) {
        this.operationListener = operationListener2;
    }

    public void setInEdit(boolean z) {
        this.isInEdit = z;
        invalidate();
    }
}
