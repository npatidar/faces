package com.faceoffaerie.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.faceoffaerie.R;
import com.faceoffaerie.bitmap.GetScreenInfo;
import com.faceoffaerie.bitmap.IsPhoneOrTablet;
import com.faceoffaerie.contants.AppUtils;
import com.faceoffaerie.contants.Constants;
import com.faceoffaerie.interfaces.BackgroundMusicModel;
import com.faceoffaerie.services.BackgroundService;
import com.parse.ParseAnalytics;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SplashActivity extends AppCompatActivity{

    public static final String TAG = "SplashActivity";

    LinearLayout layout;
    int iClicks;
    Timer timer;
    GetScreenInfo screenInfo;
    boolean isTablet;
    Bitmap backgroundBitmap = null;
    ImageView splash_image_view;

    boolean isPaused = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        splash_image_view = (ImageView)findViewById(R.id.splash_image_view);
        //layout = (LinearLayout)findViewById(R.id.splash_screen);
        screenInfo = new GetScreenInfo(this);
        isTablet = IsPhoneOrTablet.isTablet(this);


        System.gc();
        if(isTablet) {
            try {

                splash_image_view.setImageResource(R.drawable.launch_screen_t);


            }catch (OutOfMemoryError e){
                //layout.setBackgroundResource(R.drawable.splash_screen_tablet);
                System.gc();
            }
//            layout.setBackgroundResource(R.drawable.splash_screen_tablet);
        }
        else {
            try {

                splash_image_view.setImageResource(R.drawable.launch_screen_m);

            }catch (OutOfMemoryError e){
                ///layout.setBackgroundResource(R.drawable.splash_screen_phone);
                System.gc();
            }
//            layout.setBackgroundResource(R.drawable.splash_screen_phone);
        }
        System.gc();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // task to be done every 1000 milliseconds
                        iClicks = iClicks + 1;
                        if (iClicks == 3) {
                            Intent intent = new Intent(SplashActivity.this,HomeActivity.class);
                            intent.putExtra("isStart", true);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            //overridePendingTransition(R.anim.fab_in, R.anim.fab_out);
                            Log.e("TAG","ending splash activity");
                            SplashActivity.this.finish();
                        }

                    }
                });

            }

        }, 0, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(backgroundBitmap != null){
            backgroundBitmap.recycle();
            backgroundBitmap = null;
            System.gc();
        }
    }

    @Override
    protected void onPause() {
        AppUtils.log(TAG, "onPause");
        if(!isPaused){
            BackgroundMusicModel.getInstance().changeState(true);
        }
        super.onPause();
    }

    @Override
    protected void onResume() {

        isPaused = true;
        Log.e("TAG", "on resume home ");
        if(BackgroundService.mediaPlayer != null){
            Log.e(TAG, "onResume home" + BackgroundService.mediaPlayer.isPlaying());
            if(!BackgroundService.mediaPlayer.isPlaying()){
                AppUtils.log(TAG, "onResume in side  if home");
                BackgroundMusicModel.getInstance().changeState(false);
            }
        }

        super.onResume();
    }

    @Override
    public void onBackPressed() {
        BackgroundMusicModel.getInstance().changeState(true);
        super.onBackPressed();
    }




}
