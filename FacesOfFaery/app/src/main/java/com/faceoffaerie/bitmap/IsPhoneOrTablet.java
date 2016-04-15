package com.faceoffaerie.bitmap;

import android.app.Application;
import android.content.Context;

import com.faceoffaerie.R;

/**
 * Created by Genesis-10 on 3/10/2016.
 */
public class IsPhoneOrTablet extends Application {

    public  IsPhoneOrTablet(Context context)
    {

    }
    public static boolean isTablet(Context context){

//        boolean xLarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
//        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        boolean tablet = context.getResources().getBoolean(R.bool.isTablet);
//        GetScreenInfo screenInfo = new GetScreenInfo(context);
//        boolean size = screenInfo.getScreenSize() >= 7;
        return tablet;
    }
}
