package com.najand.simplecoloring.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import java.util.LinkedList;
import java.util.Queue;

public class CustomLinearFloodFill {

    protected Bitmap initialBitmap = null;
    protected int[] diff = new int[] { 0, 0, 0 };
    protected int width = 0;
    protected int height = 0;
    protected int[] pixels = null;
    protected int selectedColor = 0;
    protected int[] startColor = new int[] { 0, 0, 0 };
    protected boolean[] checkedPixels;
    protected Queue<Range> ranges;

    public CustomLinearFloodFill(Bitmap b) {
        copy(b);
    }

    public CustomLinearFloodFill(Bitmap img, int targetColor, int newColor) {
        getInitialPixels(img);

        setSelectedColor(newColor);
        setTargetColor(targetColor);
    }

    public void setTargetColor(int startColor) {
        this.startColor[0] = Color.red(startColor);
        this.startColor[1] = Color.green(startColor);
        this.startColor[2] = Color.blue(startColor);
    }

    public int getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(int value) {
        selectedColor = value;
    }


    public Bitmap getInitialBitmap() {
        return initialBitmap;
    }

    public void copy(Bitmap img) {
        width = img.getWidth();
        height = img.getHeight();

        initialBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(initialBitmap);
        canvas.drawBitmap(img, 0, 0, null);

        pixels = new int[width * height];

        initialBitmap.getPixels(pixels, 0, width, 1, 1, width - 1, height - 1);
    }

    public void getInitialPixels(Bitmap img) {
        width = img.getWidth();
        height = img.getHeight();
        initialBitmap = img;

        pixels = new int[width * height];

        initialBitmap.getPixels(pixels, 0, width, 1, 1, width - 1, height - 1);
    }

    protected void prepare() {
        checkedPixels = new boolean[pixels.length];
        ranges = new LinkedList<>();
    }

    public void floodFill(int x, int y) {

        prepare();
        Range range;

        if (startColor[0] == 0) {
            int startPixel = pixels[(width * y) + x];
            startColor[0] = (startPixel >> 16) & 0xff;
            startColor[1] = (startPixel >> 8) & 0xff;
            startColor[2] = startPixel & 0xff;
        }

        LinearFill(x, y);

        while (ranges.size() > 0) {
            range = ranges.remove();

            // check above and below each px in range
            int downPixelXid = (width * (range.y + 1)) + range.startX;
            int upPixelXid = (width * (range.y - 1)) + range.startX;
            int upY = range.y - 1;// so we can pass the y coord by ref
            int downY = range.y + 1;

            for (int i = range.startX; i <= range.endX; i++) {
                if (range.y > 0 && (!checkedPixels[upPixelXid]) && checkPx(upPixelXid))
                    LinearFill(i, upY);

                if (range.y < (height - 1) && (!checkedPixels[downPixelXid]) && checkPx(downPixelXid))
                    LinearFill(i, downY);

                downPixelXid++;
                upPixelXid++;
            }
        }

        initialBitmap.setPixels(pixels, 0, width, 1, 1, width - 1, height - 1);
    }

    protected void LinearFill(int x, int y) {
        int leftLoc = x;
        int xId = (width * y) + x;

        while (true) {
            pixels[xId] = selectedColor;
            checkedPixels[xId] = true;

            leftLoc--;
            xId--;

            if (leftLoc < 0 || (checkedPixels[xId]) || !checkPx(xId)) {
                break;
            }
        }

        leftLoc++;

        int rightLoc = x;

        xId = (width * y) + x;

        while (true) {
            pixels[xId] = selectedColor;

            checkedPixels[xId] = true;

            rightLoc++;
            xId++;

            if (rightLoc >= width || checkedPixels[xId] || !checkPx(xId)) {
                break;
            }
        }

        rightLoc--;

        Range r = new Range(leftLoc, rightLoc, y);

        ranges.offer(r);
    }

    protected boolean checkPx(int px) {
        int red = (pixels[px] >>> 16) & 0xff;
        int green = (pixels[px] >>> 8) & 0xff;
        int blue = pixels[px] & 0xff;

        return (red >= (startColor[0] - diff[0])
                && green >= (startColor[1] - diff[1])
                && blue >= (startColor[2] - diff[2]));
    }

    protected class Range {
        public int startX;
        public int endX;
        public int y;

        public Range(int startX, int endX, int y) {
            this.startX = startX;
            this.endX = endX;
            this.y = y;
        }
    }
}

