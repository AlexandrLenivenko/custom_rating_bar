package com.example.thumbview.custom_view;


import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.thumbview.R;

public class ThumbRatingBar extends View {

    private Paint backgroundPaint;
    private int ratingColor;
    private float rating;
    private Bitmap bitmapThumb;
    private Bitmap bitmapStar;
    private int starHeight;
    private int starWidth;
    private Paint startPaint;
    private int ratingRound;
    private int startPoint;


    public ThumbRatingBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ThumbRatingBar,
                0, 0);

        try {
            ratingColor = a.getColor(R.styleable.ThumbRatingBar_color, Color.BLUE);
            rating = a.getFloat(R.styleable.ThumbRatingBar_rating, 5);

        } finally {
            a.recycle();
        }


        init();
    }

    private void init() {
        backgroundPaint = new Paint();
        startPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        Resources res = getResources();
        bitmapThumb = BitmapFactory.decodeResource(res, R.drawable.ic_thumb);
        bitmapStar = BitmapFactory.decodeResource(res, R.drawable.ic_star);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        starHeight = bitmapStar.getHeight();
        starWidth = bitmapStar.getWidth();
        setMeasuredDimension(widthMeasureSpec, starHeight + bitmapThumb.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        startPoint = (getWidth() - starWidth * 10) / 2;

        for (int i = startPoint, j = 1; i < startPoint + 10 * starWidth; i += starWidth, j++) {
            if (j == ratingRound) {
                canvas.drawBitmap(bitmapStar, i, 0, startPaint);
                canvas.drawBitmap(bitmapThumb, i, starHeight, startPaint);
                setColorFilter(Color.GRAY, startPaint);

            } else {
                canvas.drawBitmap(bitmapStar, i, 25, startPaint);
            }

        }

        setColorFilter(ratingColor, startPaint);
    }

    private void setColorFilter(int ratingColor, Paint paint) {
        LightingColorFilter filter = new LightingColorFilter(ratingColor, 0);
        paint.setColorFilter(filter);
    }

    public int getRatingColor() {
        invalidate();
        return ratingColor;
    }

    public void setRatingColor(int ratingColor) {
        setColorFilter(ratingColor, startPaint);
        this.ratingColor = ratingColor;
        invalidate();
        requestLayout();
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
        ratingRound = Math.round(rating);
        invalidate();
        requestLayout();
    }
}

