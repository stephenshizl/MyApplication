package com.example.administrator.androidscroll.colorMatrix;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 *
 * Created by Administrator on 2015/12/7.
 */
public class ImageHelper {


    /**
     *  设置图像矩阵
     * @param bm  原图
     * @param hue
     * @param saturation
     * @param lum
     * @return
     */
    public static Bitmap handleImageEffect(Bitmap bm, float hue, float saturation, float lum){
        // Android 系统不允许直接修改原图，必须通过原图创建一个同样大少的Bitmap,并将原图绘制到改 Bitmap 中, 以副本型式修改图像
        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);   // 创建副本
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();

        // 调色调
        ColorMatrix hueMatrix = new ColorMatrix();
        hueMatrix.setRotate(0, hue);   // Red
        hueMatrix.setRotate(1, hue);   // Green
        hueMatrix.setRotate(2, hue);    // Blue

        // 调饱和度
        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(saturation);

        // 设置亮度
        ColorMatrix lumMatrix = new ColorMatrix();
        lumMatrix.setScale(lum, lum, lum, 1);

        // 混合
        ColorMatrix imageMatrix = new ColorMatrix();
        imageMatrix.postConcat(hueMatrix);
        imageMatrix.postConcat(saturationMatrix);
        imageMatrix.postConcat(lumMatrix);

        // 设置好处理的颜色矩阵后，通过使用Paint 类的 setColorFilter()方法，
        // 将通过imageMatrix 构造的 ColorMatrixColorFilter 对象传递进去，
        // 并通过这个画笔绘制原来的图像，从而将颜色矩阵作用到原图中。
        paint.setColorFilter(new ColorMatrixColorFilter(imageMatrix));
        canvas.drawBitmap(bm, 0,0, paint);
        return   bmp;
    }


    /**
     *  常用图像像素点处理效果，底片效果
     * @param bm
     * @return
     */
    public static Bitmap handleImageNegative(Bitmap bm){
        int color;
        int r, g, b, a;

        int width = bm.getWidth();
        int height = bm.getHeight();

        // 创建副本
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        int[] oldPx = new int[width * height];
        int[] newPx = new int[width * height];
        bm.getPixels(oldPx, 0, width, 0, 0, width, height);   // 获取像素点
        for (int i = 0; i < width * height; i++){
            // 获取每个像素点的AGRB值
            color  = oldPx[i];
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);
            a = Color.alpha(color);

            // 实现底片效果
            r = 255 - r;
            g = 255 - g;
            b = 255 - b;

            if (r > 255){
                r = 255;
            } else if (r < 0){
                r = 0;
            }
            if (g > 255){
                g = 255;
            } else if (g < 0){
                g = 0;
            }
            if (b > 255){
                b = 255;
            } else if (b < 0){
                b = 0;
            }

            // 将新的RGBA值合成新的像素点
            newPx[i] = Color.argb(a, r, g, b);
        }

        // 将处理之后的像素点数组set给Bitmap，从而达到图像处理的目的
        bmp.setPixels(newPx, 0, width, 0, 0, width, height);
        return bmp;
    }


    /**
     *  常用图像像素点处理效果，老照片效果
     * @param bm
     * @return
     */
    public static Bitmap handleImagePixeleOldPhoto(Bitmap bm){
        int color;
        int r, g, b, a, r1, g1, b1;

        int width = bm.getWidth();
        int height = bm.getHeight();

        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        int[] oldPx = new int[width * height];
        int[] newPx = new int[width * height];
        bm.getPixels(oldPx, 0, width, 0, 0, width, height);   // 获取像素点
        for (int i = 0; i < width * height; i++){
            // 获取每个像素点的AGRB值
            color  = oldPx[i];
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);
            a = Color.alpha(color);

            // 实现效果老照片
            r1 = (int) (0.393 * r + 0.769 * g + 0.189 * b);
            g1 = (int) (0.349 * r + 0.686 * g + 0.168 * b);
            b1 = (int) (0.272 * r + 0.534 * g + 0.131 * b);

            if (r1 > 255){
                r1 = 255;
            } else if (r1 < 0){
                r1 = 0;
            }
            if (g1 > 255){
                g1 = 255;
            } else if (g1 < 0){
                g1 = 0;
            }
            if (b1 > 255){
                b1 = 255;
            } else if (b1 < 0){
                b1 = 0;
            }

            // 将新的RGBA值合成新的像素点
            newPx[i] = Color.argb(a, r1, g1, b1);
        }

        // 将处理之后的像素点数组set给Bitmap，从而达到图像处理的目的
        bmp.setPixels(newPx, 0, width, 0, 0, width, height);
        return bmp;
    }


    /**
     *  常用图像像素点处理效果，浮雕效果
     * @param bm
     * @return
     */
    public static Bitmap handleImagePixelsRelief(Bitmap bm){
        int color = 0, colorBefore = 0;
        int r, g, b, a;
        int r1, g1, b1;

        int width = bm.getWidth();
        int height = bm.getHeight();

        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        int[] oldPx = new int[width * height];
        int[] newPx = new int[width * height];
        bm.getPixels(oldPx, 0, width, 0, 0, width, height);   // 获取像素点
        for (int i = 1; i < width * height; i++){    // 注意这里 i 从 1 开始
            // 获取每个像素点的AGRB值
            colorBefore = oldPx[i - 1];
            r = Color.red(colorBefore);
            g = Color.green(colorBefore);
            b = Color.blue(colorBefore);
            a = Color.alpha(colorBefore);

            color  = oldPx[i];
            r1 = Color.red(color);
            g1 = Color.green(color);
            b1 = Color.blue(color);

            // 实现浮雕效果
            r = r - r1 + 127;
            g = g - g1 + 127;
            b = b - b1 + 127;

            if (r > 255){
                r = 255;
            } else if (r < 0){
                r = 0;
            }
            if (g > 255){
                g = 255;
            } else if (g < 0){
                g = 0;
            }
            if (b > 255){
                b = 255;
            } else if (b < 0){
                b = 0;
            }

            // 将新的RGBA值合成新的像素点
            newPx[i] = Color.argb(a, r, g, b);
        }

        // 将处理之后的像素点数组set给Bitmap，从而达到图像处理的目的
        bmp.setPixels(newPx, 0, width, 0, 0, width, height);
        return bmp;
    }
}
