package com.photoeditor.blendphotoeditor.blendermixer.touch;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.photoeditor.blendphotoeditor.blendermixer.Adapter.NAGYRHAGA_FadingEdgeLayout;
import com.photoeditor.blendphotoeditor.blendermixer.NAGYRHAGA_FirstEditPageActivity;
/*import com.naygrh.multiplephotoblendermixerphotoeditor.Activity.NAGYRHAGA_FirstEditPageActivity;
import com.naygrh.multiplephotoblendermixerphotoeditor.Adapter.NAGYRHAGA_FadingEdgeLayout;
import com.naygrh.multiplephotoblendermixerphotoeditor.touch.NAGYRHAGA_ScaleGestureDetector.SimpleOnScaleGestureListener;*/

public class NAGYRHAGA_MultiTouchListener implements OnTouchListener {
    private static final int INVALID_POINTER_ID = -1;
    public boolean isRotateEnabled;
    public boolean isScaleEnabled;
    public boolean isTranslateEnabled;
    private int mActivePointerId;
    private NAGYRHAGA_ScaleGestureDetector mNAGYRHAGAScaleGestureDetector;
    private float mPrevX;
    private float mPrevY;
    public float maximumScale;
    public float minimumScale;
    private Rect rect;
    OnRotateListner rotateListner;

    public interface OnRotateListner {
        float getRotation(float f);
    }

    private class ScaleGestureListener extends NAGYRHAGA_ScaleGestureDetector.SimpleOnScaleGestureListener {
        private float mPivotX;
        private float mPivotY;
        private NAGYRHAGA_Vector2D mPrevSpanVector;

        private ScaleGestureListener() {
            this.mPrevSpanVector = new NAGYRHAGA_Vector2D(0.0f, 0.0f);
        }

        public boolean onScaleBegin(View view, NAGYRHAGA_ScaleGestureDetector nAGYRHAGA_ScaleGestureDetector) {
            this.mPivotX = nAGYRHAGA_ScaleGestureDetector.getFocusX();
            this.mPivotY = nAGYRHAGA_ScaleGestureDetector.getFocusY();
            this.mPrevSpanVector.set(nAGYRHAGA_ScaleGestureDetector.getCurrentSpanVector());
            return true;
        }

        public boolean onScale(View view, NAGYRHAGA_ScaleGestureDetector nAGYRHAGA_ScaleGestureDetector) {
            TransformInfo transformInfo = new TransformInfo();
            transformInfo.deltaScale = NAGYRHAGA_MultiTouchListener.this.isScaleEnabled ? nAGYRHAGA_ScaleGestureDetector.getScaleFactor() : 1.0f;
            float f = 0.0f;
            transformInfo.deltaAngle = NAGYRHAGA_MultiTouchListener.this.isRotateEnabled ? NAGYRHAGA_Vector2D.getAngle(this.mPrevSpanVector, nAGYRHAGA_ScaleGestureDetector.getCurrentSpanVector()) : 0.0f;
            transformInfo.deltaX = NAGYRHAGA_MultiTouchListener.this.isTranslateEnabled ? nAGYRHAGA_ScaleGestureDetector.getFocusX() - this.mPivotX : 0.0f;
            if (NAGYRHAGA_MultiTouchListener.this.isTranslateEnabled) {
                f = nAGYRHAGA_ScaleGestureDetector.getFocusY() - this.mPivotY;
            }
            transformInfo.deltaY = f;
            transformInfo.pivotX = this.mPivotX;
            transformInfo.pivotY = this.mPivotY;
            transformInfo.minimumScale = NAGYRHAGA_MultiTouchListener.this.minimumScale;
            transformInfo.maximumScale = NAGYRHAGA_MultiTouchListener.this.maximumScale;
            NAGYRHAGA_MultiTouchListener.this.move(view, transformInfo);
            return false;
        }
    }

    private class TransformInfo {
        public float deltaAngle;
        public float deltaScale;
        public float deltaX;
        public float deltaY;
        public float maximumScale;
        public float minimumScale;
        public float pivotX;
        public float pivotY;

        private TransformInfo() {
        }
    }

    private static float adjustAngle(float f) {
        return f > 180.0f ? f - 360.0f : f < -180.0f ? f + 360.0f : f;
    }

    public NAGYRHAGA_MultiTouchListener(boolean z, boolean z2, boolean z3) {
        this.isRotateEnabled = true;
        this.isTranslateEnabled = true;
        this.isScaleEnabled = true;
        this.minimumScale = 0.5f;
        this.maximumScale = 10.0f;
        this.mActivePointerId = -1;
        this.isRotateEnabled = z;
        this.isScaleEnabled = z3;
        this.isTranslateEnabled = z2;
        this.mNAGYRHAGAScaleGestureDetector = new NAGYRHAGA_ScaleGestureDetector(new ScaleGestureListener());
    }

    public NAGYRHAGA_MultiTouchListener(boolean z, boolean z2, boolean z3, OnRotateListner onRotateListner) {
        this.isRotateEnabled = true;
        this.isTranslateEnabled = true;
        this.isScaleEnabled = true;
        this.minimumScale = 0.5f;
        this.maximumScale = 10.0f;
        this.mActivePointerId = -1;
        this.isRotateEnabled = z;
        this.isScaleEnabled = z3;
        this.isTranslateEnabled = z2;
        this.mNAGYRHAGAScaleGestureDetector = new NAGYRHAGA_ScaleGestureDetector(new ScaleGestureListener());
        this.rotateListner = onRotateListner;
    }

    public NAGYRHAGA_MultiTouchListener() {
        this.isRotateEnabled = true;
        this.isTranslateEnabled = true;
        this.isScaleEnabled = true;
        this.minimumScale = 0.5f;
        this.maximumScale = 10.0f;
        this.mActivePointerId = -1;
        this.mNAGYRHAGAScaleGestureDetector = new NAGYRHAGA_ScaleGestureDetector(new ScaleGestureListener());
    }

    /* access modifiers changed from: private */
    public void move(View view, TransformInfo transformInfo) {
        computeRenderOffset(view, transformInfo.pivotX, transformInfo.pivotY);
        adjustTranslation(view, transformInfo.deltaX, transformInfo.deltaY);
        float max = Math.max(transformInfo.minimumScale, Math.min(transformInfo.maximumScale, view.getScaleX() * transformInfo.deltaScale));
        view.setScaleX(max);
        view.setScaleY(max);
        view.setRotation(adjustAngle(view.getRotation() + transformInfo.deltaAngle));
    }

    private static void adjustTranslation(View view, float f, float f2) {
        float[] fArr = {f, f2};
        view.getMatrix().mapVectors(fArr);
        view.setTranslationX(view.getTranslationX() + fArr[0]);
        view.setTranslationY(view.getTranslationY() + fArr[1]);
    }

    private static void computeRenderOffset(View view, float f, float f2) {
        if (view.getPivotX() != f || view.getPivotY() != f2) {
            float[] fArr = {0.0f, 0.0f};
            view.getMatrix().mapPoints(fArr);
            view.setPivotX(f);
            view.setPivotY(f2);
            float[] fArr2 = {0.0f, 0.0f};
            view.getMatrix().mapPoints(fArr2);
            float f3 = fArr2[1] - fArr[1];
            view.setTranslationX(view.getTranslationX() - (fArr2[0] - fArr[0]));
            view.setTranslationY(view.getTranslationY() - f3);
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        NAGYRHAGA_FirstEditPageActivity.cView = view;
        NAGYRHAGA_FadingEdgeLayout nAGYRHAGA_FadingEdgeLayout = (NAGYRHAGA_FadingEdgeLayout) NAGYRHAGA_FirstEditPageActivity.cView;
        NAGYRHAGA_FirstEditPageActivity.fadeinSeek.setProgress((int) (nAGYRHAGA_FadingEdgeLayout.getAlpha() * 100.0f));
        NAGYRHAGA_FirstEditPageActivity.fadeOutSeek.setProgress(nAGYRHAGA_FadingEdgeLayout.getFade());
        this.mNAGYRHAGAScaleGestureDetector.onTouchEvent(view, motionEvent);
        if (this.isTranslateEnabled) {
            int action = motionEvent.getAction();
            int actionMasked = motionEvent.getActionMasked() & action;
            int i = 0;
            if (actionMasked != 6) {
                switch (actionMasked) {
                    case 0:
                        this.mPrevX = motionEvent.getX();
                        this.mPrevY = motionEvent.getY();
                        this.rect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
                        this.mActivePointerId = motionEvent.getPointerId(0);
                        break;
                    case 1:
                        this.mActivePointerId = -1;
                        break;
                    case 2:
                        int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                        if (findPointerIndex != -1) {
                            float x = motionEvent.getX(findPointerIndex);
                            float y = motionEvent.getY(findPointerIndex);
                            if (!this.mNAGYRHAGAScaleGestureDetector.isInProgress()) {
                                adjustTranslation(view, x - this.mPrevX, y - this.mPrevY);
                                break;
                            }
                        }
                        break;
                    case 3:
                        this.mActivePointerId = -1;
                        break;
                }
            } else {
                int i2 = (65280 & action) >> 8;
                if (motionEvent.getPointerId(i2) == this.mActivePointerId) {
                    if (i2 == 0) {
                        i = 1;
                    }
                    this.mPrevX = motionEvent.getX(i);
                    this.mPrevY = motionEvent.getY(i);
                    this.mActivePointerId = motionEvent.getPointerId(i);
                }
            }
        }
        return true;
    }
}
