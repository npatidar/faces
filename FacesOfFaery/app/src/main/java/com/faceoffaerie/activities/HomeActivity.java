package com.faceoffaerie.activities;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.faceoffaerie.R;
import com.faceoffaerie.bitmap.GetScreenInfo;
import com.faceoffaerie.bitmap.IsPhoneOrTablet;
import com.faceoffaerie.contants.AppUtils;
import com.faceoffaerie.interfaces.BackgroundMusicModel;
import com.faceoffaerie.services.BackgroundService;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Genesis-10 on 3/29/2016.
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {


    public static final String TAG = "HomeActivity";

    GetScreenInfo getScreenInfo;
    ImageButton youChooseImageButton;
    ImageButton faeryChooseImageButton;
    ImageButton savedFaeriesButton;
    ImageButton autoGraphButton;
    ImageButton infoButton;
    ImageView logoImageView;
    ImageView circleImageView;
    ImageView circleImageViewyou;
    ImageView circleImageViewsaved;
    ImageView circleImageViewauto;
    ImageView circleImageViewinfo;
    ImageView home_image_mobile;
    ImageView home_image_tablet;

    TextView choose_tv;
    Timer timer;
    int iClicks = 0;
    boolean isStart = false;

    boolean isPaused = false;

    Bitmap backgroundBitmap = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TAG", "starting home activity activity");
        getScreenInfo= new GetScreenInfo(this);
        boolean isTablet = IsPhoneOrTablet.isTablet(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(isTablet){

            Log.e("Istablet", "tablet");
            setContentView(R.layout.activity_home_t);
            home_image_tablet= (ImageView)findViewById(R.id.home_image_tablet);
            try
            {
                home_image_tablet.setImageResource(R.drawable.faerychoose_background_image_t);

            }catch (OutOfMemoryError e)
            {
                System.gc();
            }
        }
        else
        {
            setContentView(R.layout.activity_home);
            home_image_mobile= (ImageView)findViewById(R.id.home_image_mobile);
            try
            {
                home_image_mobile.setImageResource(R.drawable.mainmenu_background);
            }
            catch (OutOfMemoryError e)
            {
                System.gc();
            }
        }
        System.gc();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            isStart = bundle.getBoolean("isStart");
        }
        youChooseImageButton = (ImageButton)findViewById(R.id.youChooseImageButton);
        faeryChooseImageButton= (ImageButton)findViewById(R.id.faeryChooseImageButton);
        savedFaeriesButton=(ImageButton)findViewById(R.id.savedFaeriesButton);
        autoGraphButton = (ImageButton)findViewById(R.id.autoGraphButton);
        infoButton = (ImageButton)findViewById(R.id.infoButton);
        logoImageView = (ImageView)findViewById(R.id.logoImageView);
        circleImageView=(ImageView)findViewById(R.id.backgroundimage_ovel);
        circleImageViewyou=(ImageView)findViewById(R.id.backgroundimage_ovel_you);
        circleImageViewsaved=(ImageView)findViewById(R.id.backgroundimage_ovel_saved);
        circleImageViewauto=(ImageView)findViewById(R.id.backgroundimage_ovel_Auto);
        circleImageViewinfo=(ImageView)findViewById(R.id.backgroundimage_ovel_info);
        choose_tv=(TextView)findViewById(R.id.choose_tv);
        initView();
        setListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.exit(0);
        if(backgroundBitmap != null)
        {
            backgroundBitmap.recycle();
            backgroundBitmap = null;
            System.gc();

        }
    }

    public void initView()
    {
        faeryChooseImageButton.setImageResource(R.drawable.faery_choose);
        youChooseImageButton.setImageResource(R.drawable.you_choose);
        savedFaeriesButton.setImageResource(R.drawable.savedfaeries_btn);
        autoGraphButton.setImageResource(R.drawable.autograph_btn);
        infoButton.setImageResource(R.drawable.info_btn);
        /* SPANNABLE IS USED TO CAPITALIZE THE FIRST LETTER OH THE TEXT WHO WILL CHOOSE FOR YOU TODAY? UPDATED ON FRI 4TH MARCH */

        Spannable chooseTextSpan = new SpannableString(choose_tv.getText());
        chooseTextSpan.setSpan(new RelativeSizeSpan(1.3f), 0, 1, 0);
        chooseTextSpan.setSpan(new RelativeSizeSpan(1.3f), 31, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "BarbedorSCTMed.ttf");
        choose_tv.setTypeface(typeface);
        choose_tv.setText(chooseTextSpan);
    }

    public void setListener() {


        faeryChooseImageButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        HomeActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                circleImageView.setVisibility(View.VISIBLE);
                                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in1);
                                circleImageView.startAnimation(animation);
                            }
                        });

                        break;
                    }
                    case MotionEvent.ACTION_UP: {

                        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out1);
                        circleImageView.startAnimation(animation);
                        circleImageView.setVisibility(View.INVISIBLE);
                        break;
                    }
                }
                return false;

            }
        });

        youChooseImageButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        HomeActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                circleImageViewyou.setVisibility(View.VISIBLE);
                                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in1);
                                circleImageViewyou.startAnimation(animation);
                            }
                        });

                        break;
                    }
                    case MotionEvent.ACTION_UP: {

                        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out1);
                        circleImageViewyou.startAnimation(animation);
                        circleImageViewyou.setVisibility(View.INVISIBLE);
                        break;
                    }
                }
                return false;

            }
        });

        savedFaeriesButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        HomeActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                circleImageViewsaved.setVisibility(View.VISIBLE);
                                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in1);
                                circleImageViewsaved.startAnimation(animation);
                            }
                        });

                        break;
                    }
                    case MotionEvent.ACTION_UP: {

                        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out1);
                        circleImageViewsaved.startAnimation(animation);
                        circleImageViewsaved.setVisibility(View.INVISIBLE);
                        break;
                    }
                }
                return false;

            }
        });

        autoGraphButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        HomeActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                circleImageViewauto.setVisibility(View.VISIBLE);
                                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in1);
                                circleImageViewauto.startAnimation(animation);
                            }
                        });

                        break;
                    }
                    case MotionEvent.ACTION_UP: {

                        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out1);
                        circleImageViewauto.startAnimation(animation);
                        circleImageViewauto.setVisibility(View.INVISIBLE);
                        break;
                    }
                }
                return false;

            }
        });


        infoButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        HomeActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                circleImageViewinfo.setVisibility(View.VISIBLE);
                                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in1);
                                circleImageViewinfo.startAnimation(animation);
                            }
                        });

                        break;
                    }
                    case MotionEvent.ACTION_UP: {

                        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
                        circleImageViewinfo.startAnimation(animation);
                        circleImageViewinfo.setVisibility(View.INVISIBLE);
                        break;
                    }
                }
                return false;

            }
        });



        savedFaeriesButton.setOnClickListener(this);
        autoGraphButton.setOnClickListener(this);
        infoButton.setOnClickListener(this);
        faeryChooseImageButton.setOnClickListener(this);
        youChooseImageButton.setOnClickListener(this);
    }


    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.savedFaeriesButton: {
                isPaused = true;
                startActivity(new Intent(this, SavedFaeriesActivity.class));
            }
            break;
            case R.id.autoGraphButton: {
                isPaused = true;
                startActivity(new Intent(this, AutoGraphsActivity.class));
            }
            break;
            case R.id.infoButton: {
                isPaused = true;
                Log.e("Info is call","Info is call");
                startActivity(new Intent(this, InfoActivity.class));
            }
            break;
            case R.id.faeryChooseImageButton: {
                isPaused = true;
                startActivity(new Intent(this, FaeryChooseActivity.class));
            }
            break;
            case R.id.youChooseImageButton: {
                isPaused = true;
                startActivity(new Intent(this, FaeryCameraActivity.class));
            }
            break;

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

        isPaused = false;
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
