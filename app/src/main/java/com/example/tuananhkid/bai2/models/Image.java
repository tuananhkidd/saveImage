package com.example.tuananhkid.bai2.models;

/**
 * Created by TuanAnhKid on 3/28/2018.
 */

public class Image {
    private String url;
    private float alpha;

    public Image(String url, float alpha) {
        this.url = url;
        this.alpha = alpha;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
}
