package com.photoeditor.blendphotoeditor.blendermixer.touch;

import android.annotation.SuppressLint;
import android.graphics.PointF;

@SuppressLint({"ParcelCreator"})
public class NAGYRHAGA_Vector2D extends PointF {
    public NAGYRHAGA_Vector2D(float f, float f2) {
        super(f, f2);
    }

    public static float getAngle(NAGYRHAGA_Vector2D nAGYRHAGA_Vector2D, NAGYRHAGA_Vector2D nAGYRHAGA_Vector2D2) {
        nAGYRHAGA_Vector2D.normalize();
        nAGYRHAGA_Vector2D2.normalize();
        return (float) ((Math.atan2((double) nAGYRHAGA_Vector2D2.y, (double) nAGYRHAGA_Vector2D2.x) - Math.atan2((double) nAGYRHAGA_Vector2D.y, (double) nAGYRHAGA_Vector2D.x)) * 57.29577951308232d);
    }

    public void normalize() {
        float sqrt = (float) Math.sqrt((double) ((this.x * this.x) + (this.y * this.y)));
        this.x /= sqrt;
        this.y /= sqrt;
    }
}
