package com.faceoffaerie.bitmap;

import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Genesis-10 on 3/10/2016.
 */
public class GetScreenInfo extends Application {


    Context context;
    WindowManager windowManager;
    Display display;
    Point size;
    private  int screenX;
    private  int screenY;
    DisplayMetrics displayMetrics;
    private float density;
    private int densityDpi;

    public  GetScreenInfo(Context context)
    {
        this.context = context;
        windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        screenX = size.x;
        screenY = size.y;
        displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        density = displayMetrics.density;
        densityDpi = displayMetrics.densityDpi;

    }
    public int getScreenX(){
        return screenX;
    }

    public int getScreenY(){
        return screenY;
    }

    public float getDensity(){
        return density;
    }

    public int getDensityDpi(){
        return densityDpi;
    }

    public double getXDpi(){
        return displayMetrics.xdpi;
    }

    public double getYDpi(){
        return displayMetrics.ydpi;
    }

    public double getScreenSize(){
        return Math.sqrt(Math.pow(getScreenX()/getXDpi()*(density - 0.5), 2) + Math.pow(getScreenY()/getYDpi()*(density - 0.5), 2));
    }
}
