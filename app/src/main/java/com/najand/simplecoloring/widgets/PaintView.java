package com.najand.simplecoloring.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;


import com.najand.simplecoloring.commons.Common;
import com.najand.simplecoloring.utils.CustomLinearFloodFill;

import java.nio.ByteBuffer;

public class PaintView extends View {
    Bitmap bitmap;
    public PaintView(Context context) {
        super(context);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (bitmap == null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Bitmap bitmapSrc = BitmapFactory.decodeResource(getResources(), Common.PICTURE_SELECTED);
            bitmap = resizeBitmap(bitmapSrc,w,h);
        }
        for (int i = 0; i < bitmap.getWidth(); i++) {
            for (int j = 0; j < bitmap.getHeight(); j++) {
                int alpha = 255 - brightness(bitmap.getPixel(i, j));
                if (alpha > 150){
                    bitmap.setPixel(i,j,Color.BLACK);
                }else {
                    bitmap.setPixel(i,j, Color.WHITE);
                }
            }
        }
    }

    private Bitmap resizeBitmap(Bitmap src, int newW, int newH) {
        Bitmap resizedBitmap = Bitmap.createBitmap(newW, newH, Bitmap.Config.ARGB_8888);

        float scaledW = ((float) newW) / src.getWidth();
        float scaledH = ((float) newH) / src.getHeight();
        float middleX = newW / 2.0f;
        float middleY = newH / 2.0f;
        Matrix matrix = new Matrix();
        matrix.setScale(scaledW,scaledH,middleX,middleY);
        Canvas canvas = new Canvas(resizedBitmap);
        canvas.setMatrix(matrix);
        canvas.drawBitmap(src, middleX - (int)(src.getWidth() / 2), (int)(middleY - src.getHeight() / 2), new Paint(Paint.FILTER_BITMAP_FLAG));
        return resizedBitmap;
    }

    private int brightness(int color) {
        return (color >> 16) & 0xff;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap,0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        paint((int)event.getX(), (int)event.getY());
        return true;
    }

    private void paint(int x, int y) {
        int targetColor = bitmap.getPixel(x,y);

        if (targetColor != Color.BLACK){
//            FloodFill.floodFill(bitmap, new Point(x, y), targetColor, Common.COLOR_SELECTED);
            CustomLinearFloodFill floodFiller = new CustomLinearFloodFill(bitmap,targetColor,Common.COLOR_SELECTED);
            floodFiller.floodFill(x,y);
            invalidate();
        }

    }

}

