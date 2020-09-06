package com.photoeditor.blendphotoeditor.blendermixer;

import java.io.Serializable;

public class NAGYRHAGA_StickerPropertyModel implements Serializable {
    private static final long serialVersionUID = 3800737478616389410L;
    private float degree;
    private int horizonMirror;
    private int order;
    private float scaling;
    private long stickerId;
    private String stickerURL;
    private String text;
    private float xLocation;
    private float yLocation;

    public int getHorizonMirror() {
        return this.horizonMirror;
    }

    public void setHorizonMirror(int i) {
        this.horizonMirror = i;
    }

    public String getStickerURL() {
        return this.stickerURL;
    }

    public void setStickerURL(String str) {
        this.stickerURL = str;
    }

    public long getStickerId() {
        return this.stickerId;
    }

    public void setStickerId(long j) {
        this.stickerId = j;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String str) {
        this.text = str;
    }

    public float getxLocation() {
        return this.xLocation;
    }

    public void setxLocation(float f) {
        this.xLocation = f;
    }

    public float getyLocation() {
        return this.yLocation;
    }

    public void setyLocation(float f) {
        this.yLocation = f;
    }

    public float getDegree() {
        return this.degree;
    }

    public void setDegree(float f) {
        this.degree = f;
    }

    public float getScaling() {
        return this.scaling;
    }

    public void setScaling(float f) {
        this.scaling = f;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int i) {
        this.order = i;
    }
}
