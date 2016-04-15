package com.faceoffaerie.activities;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Telephony;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.faceoffaerie.R;
import com.faceoffaerie.bitmap.GetScreenInfo;
import com.faceoffaerie.bitmap.IsPhoneOrTablet;
import com.faceoffaerie.contants.Constants;
import com.faceoffaerie.contants.PlistInfo;
import com.faceoffaerie.db.Dao;
import com.faceoffaerie.fragments.MenuLayoutFragment;
import com.faceoffaerie.interfaces.BackgroundMusicModel;
import com.faceoffaerie.parser.ParsePlistParser;
import com.faceoffaerie.services.BackgroundService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class FaeryChooseActivity extends Activity implements OnClickListener{

    RelativeLayout rootRelativeLayout;
    TextView TitleTextView;
    ImageButton homeButton;
    ImageView symbolImageView;
    RelativeLayout faerieRelativeLayout;
    ImageView faerieImageView;
    TextView faerieNameTextView;
    TextView faerieReadingTextView;
    LinearLayout menuLinearLayout;
    ImageView facebookMenuImageView;
    ImageView twitterMenuImageView;
    ImageView emailMenuImageView;
    ImageView messageMenuImageView;
    ImageView cancelMenuImageView;
    ImageView reconnectMenuImageView;
    ImageView saveMenuImageView;
    ImageView homeMenuImageView;
    ImageView infoMenuImageView;
    ImageView faery_background;
    GetScreenInfo getScreenInfo;
    TextView title;
    TextView titleAgain;
    TextView textview;
    TextView textviewAgain;
    AlertDialog.Builder Alert;
    boolean flag_clicked=true;
    private int slideIndex = 0;
    Timer timer;
    int iClicks = 30;
    public final static int ANIMATION_DURATION = 1000;

    public FrameLayout frameLayout;
    public ObjectAnimator objectAnimator;

    Bitmap backgroundBitmap = null;


    private ArrayList<PlistInfo> faerieChooseList;
    private int selectedIndex = 0;
    private Handler mHandler = null;
    private boolean flag = true;
    private int index = 0;
    private int intervalTime = 80;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    MenuLayoutFragment menuLayoutFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getScreenInfo= new GetScreenInfo(this);
        boolean isTablet = IsPhoneOrTablet.isTablet(this);

       // BackgroundMusicModel.getInstance().changeState(true);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        if (isTablet) {

            Log.e("Istablet", "tablet");
            setContentView(R.layout.activity_faery_choose_t);
            faery_background=(ImageView)findViewById(R.id.faery_background);
            symbolImageView = (ImageView)findViewById(R.id.symbolImageView);
            rootRelativeLayout = (RelativeLayout)findViewById(R.id.rootRelativeLayout);
            try
            {
                //getResources().getIdentifier("faerychoose_background_image_m", "drawable", getPackageName())
                faery_background.setImageResource(R.drawable.faerychoose_background_image_t);


            }catch (OutOfMemoryError e)
            {
                System.gc();
            }
        }
        else
        {
            setContentView(R.layout.activity_faery_choose);
            faery_background=(ImageView)findViewById(R.id.faery_background);
            symbolImageView = (ImageView)findViewById(R.id.symbolImageView);
            rootRelativeLayout= (RelativeLayout)findViewById(R.id.rootRelativeLayout);
            try
            {
                faery_background.setImageResource(R.drawable.faerychoose_background_image_m);
            }
            catch (OutOfMemoryError e)
            {
                System.gc();
            }
        }
        System.gc();

//        fragmentManager = getFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//        menuLayoutFragment = new MenuLayoutFragment();
//        Log.e("fragment","fragment");
//        fragmentTransaction.add(R.id.menuFrameLayout, menuLayoutFragment, "musicVolumeFragment");
//        fragmentTransaction.commit();
//        frameLayout =(FrameLayout) findViewById(R.id.menuFrameLayout);
        initView();
        setListener();
        initData();


        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                super.handleMessage(msg);

                switch (msg.what) {
                    case Constants.MSG_SUCCESS: {
                        int i = msg.arg1;
                        try {
                            symbolImageView.setBackgroundResource(Constants.animArray[i]);
                        }
                        catch (OutOfMemoryError o)
                        {
                            System.gc();
                        }
                    }
                    break;
                }
            }

        };
    }

    public void initView() {
       // ButterKnife.inject(this);
       rootRelativeLayout=(RelativeLayout)findViewById(R.id.rootRelativeLayout);
        TitleTextView=(TextView)findViewById(R.id.TitleTextView);
        homeButton=(ImageButton)findViewById(R.id.homeButton);
        symbolImageView=(ImageView)findViewById(R.id.symbolImageView);
        faerieRelativeLayout=(RelativeLayout)findViewById(R.id.faerieRelativeLayout);
        faerieImageView=(ImageView)findViewById(R.id.faerieImageView);
        faerieNameTextView=(TextView)findViewById(R.id.faerieNameTextView);
        faerieReadingTextView=(TextView)findViewById(R.id.faerieReadingTextView);
        menuLinearLayout=(LinearLayout)findViewById(R.id.menuLinearLayout);
        facebookMenuImageView=(ImageView)findViewById(R.id.facebookMenuImageView);
        twitterMenuImageView=(ImageView)findViewById(R.id.twitterMenuImageView);
        emailMenuImageView=(ImageView)findViewById(R.id.emailMenuImageView);
        messageMenuImageView=(ImageView)findViewById(R.id.messageMenuImageView);
        cancelMenuImageView=(ImageView)findViewById(R.id.cancelMenuImageView);
        reconnectMenuImageView=(ImageView)findViewById(R.id.reconnectMenuImageView);
        saveMenuImageView=(ImageView)findViewById(R.id.saveMenuImageView);
        homeMenuImageView=(ImageView)findViewById(R.id.homeMenuImageView);
        infoMenuImageView=(ImageView)findViewById(R.id.infoMenuImageView);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "BarbedorSCTMed.ttf");
        TitleTextView.setTypeface(typeface);
        faerieNameTextView.setTypeface(typeface);
        faerieReadingTextView.setTypeface(typeface);
        faerieReadingTextView.setTextSize(30);

         /*  SPANNABLE TEXT VIEW */

        Spannable spanText = new SpannableString(TitleTextView.getText());
        spanText.setSpan(new RelativeSizeSpan(1.25f), 0, 1, 0);
        TitleTextView.setText(spanText);

        faerieRelativeLayout.setVisibility(View.GONE);
        menuLinearLayout.setVisibility(View.GONE);
        faerieNameTextView.setVisibility(View.GONE);
        faerieReadingTextView.setVisibility(View.GONE);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) symbolImageView.getLayoutParams();
        params.width = Constants.getWidth(this);
        params.height = params.width;
        symbolImageView.setLayoutParams(params);
    }
    public void showAnimation() {
        flag = true;
        intervalTime = 80;
        AnimationTask animationTask = new AnimationTask();
        animationTask.execute();
    }
    public void setListener() {
        symbolImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                BackgroundMusicModel.getInstance().changeState(true);
                flag_clicked = true;

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    intervalTime = 30;
                    Constants.playmusic(FaeryChooseActivity.this,R.raw.test_chimes_one);


                    iClicks = 30;
                    //TitleTextView.setVisibility(View.VISIBLE);
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // task to be done every 1000 milliseconds
                                    Log.e("TAG","value of iClick" +iClicks);
                                    iClicks = iClicks + 30;
                                    if (iClicks == 120) {

                                        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
                                        TitleTextView.startAnimation(animation);
                                        TitleTextView.setVisibility(View.INVISIBLE);
                                    }

                                }
                            });

                        }

                    }, 0, 1000);

//              mHandler= new Handler()
//              {
//                  public void run() {
//                      for (int iClicks = 30; iClicks <= 120; iClicks++) {
//                          try {
//                              Thread.sleep(1000);
//                          } catch (InterruptedException e) {
//                              e.printStackTrace();
//                          }
//                          mHandler.post(new Runnable() {
//                              @Override
//                              public void run()
//                              {
//                                  Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
//                                  TitleTextView.startAnimation(animation);
//                                  TitleTextView.setVisibility(View.INVISIBLE);
//                              }
//                          });
//                      }
//                  }
//              };


                    Log.e("TAG", "MotionEvent.ACTION_DOWN");
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //Constants.pausemusic();
                    flag = false;
                    timer.cancel();
                    Log.e("TAG", "MotionEvent.ACTION_UP");
                    return false;
                }
                return false;
            }
        });


        homeButton.setOnClickListener(this);

    }
    public void showFaerieChoose(int index) {
        //BackgroundMusicModel.getInstance().changeState(true);
        Constants.pausemusic();
        symbolImageView.setVisibility(View.GONE);
        homeButton.setVisibility(View.GONE);
        faerieRelativeLayout.setVisibility(View.VISIBLE);
        final  PlistInfo selectedInfo = faerieChooseList.get(index);
        faerieNameTextView.setText(selectedInfo.name);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setStartOffset(4000);
        alphaAnimation.setDuration(2000);
        faerieNameTextView.startAnimation(alphaAnimation);
        faerieNameTextView.setVisibility(View.VISIBLE);

        faerieReadingTextView.setText(selectedInfo.reading);
        alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setStartOffset(2000);
        alphaAnimation.setDuration(4000);
        faerieReadingTextView.startAnimation(alphaAnimation);
        faerieReadingTextView.setVisibility(View.VISIBLE);
        faerieImageView.setVisibility(View.VISIBLE);

        Log.e("TAG", "image name" + faerieChooseList.get(index).image);
        InputStream ims = null;
        try {
            ims = getAssets().open(String.format("faerie%d.png", index + 1));
//            Drawable d = Drawable.createFromStream(ims, null);
//            faerieImageView.setImageDrawable(d);


        } catch (Exception e)
        {

        }
        finally {
            if (ims != null) {
                try {
                    ims.close();
                } catch (IOException e) {
                    // NOOP
                }
            }
        }
        alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                faerieRelativeLayout.setBackgroundColor(Color.WHITE);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                String fileName = selectedInfo.name;
                if (fileName.contains(" "))
                    fileName = fileName.replaceAll(" ", "_");
                if (fileName.contains("'"))
                    fileName = fileName.replaceAll("'", "");
                fileName = fileName.toLowerCase();
                Log.e("Tag","music is crash" +fileName);
                Constants.playAudio(FaeryChooseActivity.this, getResources().getIdentifier(fileName, "raw", getPackageName()));

                String imageFile = selectedInfo.image;
                if(selectedInfo.image.isEmpty()){
                    Log.e("TAG","empty");
                }

                Log.e("show Image","show Image"+imageFile);
                Log.e("show Image", "Imageid" + getResources().getIdentifier("" + imageFile, "drawable", getPackageName()));
                faerieImageView.setImageResource(getResources().getIdentifier(imageFile, "drawable", getPackageName()));
                faerieRelativeLayout.setBackgroundColor(Color.BLACK);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        faerieRelativeLayout.startAnimation(alphaAnimation);
        faerieRelativeLayout.setVisibility(View.VISIBLE);


    }
    public void initData() {
        String xml = Constants.readFaerieChooseFromAssetsPlist(this);
        ParsePlistParser pp = new ParsePlistParser();
        faerieChooseList = pp.parsePlist(xml);
        showAnimation();
    }
    public void onDestroy() {
        super.onDestroy();

        flag = false;
        BitmapDrawable d = (BitmapDrawable) faerieImageView.getDrawable();
        if (d != null && d.getBitmap() != null)
            d.getBitmap().recycle();
    }

    @Override
    protected void onStop() {
        super.onStop();
       try {
           Constants.pauseAudio();

       }catch(Exception e){
           Log.e("Crashed","Crashed");
       }
    }

    @Override
    public void onBackPressed() {
       // Constants.pauseAudio();
        TitleTextView.setVisibility(View.VISIBLE);
        super.onBackPressed();
    }

    public void shareToFacebook(Context context, String text, String imageFile) {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(imageFile, "drawable", getPackageName()));
        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath() + "/path");
        OutputStream out = null;
        dir.mkdir();
        File file = new File(dir, "LatestShare.png");
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String path = file.getPath();
        Uri bmpUri = Uri.parse("file://" + path);
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.app_name));
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM,bmpUri);

        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
        for (final ResolveInfo app : activityList)
        {
            if ((app.activityInfo.name).contains("facebook"))
            {
                final ActivityInfo activity = app.activityInfo;
                final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
                shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                shareIntent.setComponent(name);
                shareIntent.setType("image/*");
                context.startActivity(shareIntent);
                return;
            }
        }
    }
    public void shareToTwitter(Context context, String text, String imageFile) {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(imageFile, "drawable", getPackageName()));
        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath() + "/path");
        OutputStream out = null;
        dir.mkdir();
        File file = new File(dir, "LatestShare.png");
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String path = file.getPath();
        Uri bmpUri = Uri.parse("file://" + path);
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.app_name));
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM,bmpUri);

        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
        for (final ResolveInfo app : activityList)
        {
            if ((app.activityInfo.name).contains("com.twitter.android")){
                final ActivityInfo activity = app.activityInfo;
                final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
                shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                shareIntent.setComponent(name);
                shareIntent.setType("image/*");
                context.startActivity(shareIntent);
                break;
            }
        }
    }


    public void shareToEmail(Context context, String subject, String text, String imageFile) {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(imageFile, "drawable", getPackageName()));
        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath() + "/path");
        OutputStream out = null;
        dir.mkdir();
        File file = new File(dir, "LatestShare.png");
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String path = file.getPath();
        Uri bmpUri = Uri.parse("file://" + path);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        //shareIntent.setType("text/plain");
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);

        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
        for (final ResolveInfo app : activityList)
        {
            if ((app.activityInfo.name).toString().contains("android.gm"))
            {
                final ActivityInfo activity = app.activityInfo;
                final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
                shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                shareIntent.setComponent(name);
                shareIntent.setType("image/*");
                startActivity(shareIntent);
                break;
            }
        }
    }
    public void shareToSMS(Context context, String text, String imageFile) {


        //faerieImageView.setImageResource(getResources().getIdentifier(imageFile, "drawable", getPackageName()));
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(imageFile, "drawable", getPackageName()));//put here your image id
        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath() + "/path");
        OutputStream out = null;
        dir.mkdir();
        File file = new File(dir, "LatestShare.png");
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String path = file.getPath();
        Uri bmpUri = Uri.parse("file://" + path);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) // At least KitKat
        {
            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(this); // Need to change the build to API 19

            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("image/*");
            sendIntent.putExtra("sms_body", text);
            sendIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            if (defaultSmsPackageName != null)// Can be null in case that there is no default, then the user would be able to choose
            // any app that support this intent.
            {
                sendIntent.setPackage(defaultSmsPackageName);
            }
            startActivity(sendIntent);

        }
        else // For early versions, do what worked for you before.
        {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra("sms_body", text);
            sendIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            sendIntent.setType("image/*");
            startActivity(sendIntent);
        }
    }
         /* This Method is used to set Title of the pop-ups used throughout the module */

    public TextView setTitle(String titleString){
        TextView title = new TextView(this);
        title.setText(titleString);
        title.setTextSize(22);
        title.setTypeface(null, Typeface.BOLD);
        title.setTextColor(Color.BLACK);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, (int) (5 * getScreenInfo.getDensity()), (int) (5 * getScreenInfo.getDensity()), (int) (5 * getScreenInfo.getDensity()));
        return title;
    }

    /* This Method is used to set Message of the pop-ups used throughout the module */
    public TextView setMessage(String message){
        final TextView messageText = new TextView(this);
        messageText.setText(message);
        messageText.setTextColor(Color.BLACK);
        messageText.setGravity(Gravity.CENTER);
        messageText.setTextSize(15);
        return messageText;
    }
    /* This Method is used to hide the divider line from the pop-ups and add color to buttons */
    public void hideDividerLine(){
        final AlertDialog dialog = Alert.show();
        int divierId = dialog.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
        View divider = dialog.findViewById(divierId);
        if(divider != null)
        divider.setBackgroundColor(Color.TRANSPARENT);
        Button dsaButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        dsaButton.setTextColor(Color.BLUE);
        Button okButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        okButton.setTextColor(Color.BLUE);

    }
//
//    public void itemClick(int id){
//        switch (id) {
//
//            if(id == R.id.faerieImageView)
//            {
//                if(slideIndex == 0){
//                    setSlideIndex(1);
//                    FaeryChooseActivity.animationMusicPlayerVolume_Up();
//                }
//                else if(slideIndex == 1)
//                {
//                    setSlideIndex(0);
//                    FaeryChooseActivity.animationMusicPlayerVolume_Down();
//                }
//            }
//        }
//    }
//
//    public int getSlideIndex()
//    {
//        return slideIndex;
//    }
//
//    public void setSlideIndex(int slideIndex)
//    {
//        this.slideIndex = slideIndex;
//    }


//    //Slide_Up Animation
//
//    public void animationMusicPlayerVolume_Up(){
//
//        objectAnimator = ObjectAnimator.ofFloat(frameLayout, "translationY", -355 * getScreenInfo.getDensity());
//        objectAnimator.setDuration(ANIMATION_DURATION);
//        objectAnimator.start();
//    }
//
//    //Slide_Down Animation
//
//    public void animationMusicPlayerVolume_Down(){
//        objectAnimator = ObjectAnimator.ofFloat(frameLayout, "translationY", 0);
//        objectAnimator.setDuration(ANIMATION_DURATION);
//        objectAnimator.start();
//    }
//

   public void onClick(View v) {


        Log.e("Onclick", "Onclick");
        int viewId = v.getId();
        switch (viewId) {

            case R.id.homeButton:
                Log.e("Hbutton","Hbutton");
            {
                TitleTextView.setVisibility(View.VISIBLE);
                finish();
            }
            break;  case R.id.faerieImageView: {
                if (menuLinearLayout.getVisibility() == View.VISIBLE) {
                    //animationMusicPlayerVolume_Down();
                     hideMenuLayout();
                } else {
                    //animationMusicPlayerVolume_Up();
                    showMenuLayout();
                }
            }
            break;

            case R.id.cancelMenuImageView: {
                TitleTextView.setVisibility(View.VISIBLE);
               // animationMusicPlayerVolume_Down();
                hideMenuLayout();
            }
            break;
            case R.id.homeMenuImageView: {
                TitleTextView.setVisibility(View.VISIBLE);
                //onResume();
                //Constants.pauseAudio();
                finish();
            }
            break;
            case R.id.infoMenuImageView: {
                TitleTextView.setVisibility(View.VISIBLE);
                Intent intent = new Intent(this, InfoActivity.class);
                intent.putExtra("fromFaeryChoose", true);
                startActivity(intent);
                //animationMusicPlayerVolume_Down();
                 hideMenuLayout();
            }
            break;
            case R.id.reconnectMenuImageView: {

               // BackgroundMusicModel.getInstance().changeState(false);
//                Constants.pauseAudio();
                selectedIndex = 0;
                BitmapDrawable d = (BitmapDrawable) faerieImageView.getDrawable();
                if (d != null && d.getBitmap() != null)
                    d.getBitmap().recycle();
                faerieImageView.setImageBitmap(null);
                faerieRelativeLayout.setVisibility(View.GONE);
                symbolImageView.setVisibility(View.VISIBLE);
                homeButton.setVisibility(View.VISIBLE);
                TitleTextView.setVisibility(View.VISIBLE);
               // animationMusicPlayerVolume_Down();
                hideMenuLayout();
                showAnimation();
            }
            break;
            case R.id.saveMenuImageView: {


                int result = saveFaeryToDB(faerieChooseList.get(selectedIndex), 0);
                if (result == 0) {
                    hideMenuLayout();
                    title = setTitle(getString(R.string.alert_title_two));
                    textview = setMessage(String.format("\"%s\"\n To your saved faeries", faerieChooseList.get(selectedIndex).name));
                    Alert = new AlertDialog.Builder(this);
                    Alert.setCustomTitle(title);
                    Alert.setView(textview);
                    Alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create();
                    hideDividerLine();
                } else if (result == 1) {
                    title = setTitle(getString(R.string.alert_title_one));
                    textview = setMessage(String.format("You have already saved the faery \n \"%s\" for this reading.\nAre you sure you want to save it again?", faerieChooseList.get(selectedIndex).name));
                    Alert = new AlertDialog.Builder(FaeryChooseActivity.this);
                    Alert.setCustomTitle(title);
                    Alert.setView(textview);
                    Alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            hideMenuLayout();
                            saveFaeryToDB(faerieChooseList.get(selectedIndex), 1);
                            title = setTitle(getString(R.string.alert_title_two));
                            textview = setMessage(String.format("\"%s\"\n To your saved faeries",faerieChooseList.get(selectedIndex).name));


                            Alert = new AlertDialog.Builder(FaeryChooseActivity.this);
                            Alert.setCustomTitle(title);
                            Alert.setView(textview);
                            Alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).create();
                            hideDividerLine();
                        }
                    })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    // do nothing
                                }
                            });
                    hideDividerLine();

                }
            }
            break;
            case R.id.facebookMenuImageView: {
                //String text = "Faces of Faerie!\n You can get the new World of Froud app at:\n https://itunes.apple.com/us/app/faces-of-faerie/id874271272?ls=1&mt=8\"";
                String text = String.format("This is the message the faery \"%s\" has for me.\n %s\n\n You can get the new World of Froud app at:\n https://itunes.apple.com/us/app/faces-of-faerie/id874271272?ls=1&mt=8", faerieChooseList.get(selectedIndex).name, faerieChooseList.get(selectedIndex).reading);
                shareToFacebook(FaeryChooseActivity.this, text,shareImageFileName());
                hideMenuLayout();
                BackgroundMusicModel.getInstance().changeState(true);

            }
            break;
            case R.id.twitterMenuImageView: {
                String text = "Faces of Faerie!\n You can get the new World of Froud app at:\n https://itunes.apple.com/us/app/faces-of-faerie/id874271272?ls=1&mt=8\"";
                shareToTwitter(FaeryChooseActivity.this, text, shareImageFileName());
                hideMenuLayout();
                BackgroundMusicModel.getInstance().changeState(true);
            }
            break;
            case R.id.emailMenuImageView: {
                String subject = String.format("Check out my Faery for Today: %s", faerieChooseList.get(selectedIndex).name);
                String text = String.format("This is the message the faery \"%s\" has for me.\n %s\n\n You can get the new World of Froud app at:\n https://itunes.apple.com/us/app/faces-of-faerie/id874271272?ls=1&mt=8", faerieChooseList.get(selectedIndex).name, faerieChooseList.get(selectedIndex).reading);
                shareToEmail(FaeryChooseActivity.this, subject, text, shareImageFileName());
                hideMenuLayout();
                BackgroundMusicModel.getInstance().changeState(true);
            }
            break;
            case R.id.messageMenuImageView: {
                String text = String.format("This is the message the faery \"%s\" has for me.\n %s\n\n You can get the new World of Froud app at:\n https://itunes.apple.com/us/app/faces-of-faerie/id874271272?ls=1&mt=8", faerieChooseList.get(selectedIndex).name, faerieChooseList.get(selectedIndex).reading);
                shareToSMS(FaeryChooseActivity.this, text, shareImageFileName());
                hideMenuLayout();
                BackgroundMusicModel.getInstance().changeState(true);
            }
            break;

      }
   }



    /* This Method is implemented to share the imnage via SMS*/
    private String shareImageFileName(){
        final  PlistInfo selectedInfo = faerieChooseList.get(selectedIndex);
        String imageFile = selectedInfo.image;
        return imageFile;
    }
    private String prepareSdcardImage()
    {
        String faeriePath = String.format("faerie%d.png",  + 1);
        return "file://" + copyAssets(faeriePath);
    }

    /* This Method is used to save test, image in database file */
    public int saveFaeryToDB(PlistInfo info, int force) {
        Dao dao = new Dao(FaeryChooseActivity.this);
        dao.open();
        int result = dao.addFavourFunc(info, force);
        dao.close();
        return result;
    }

    /* This Method is used to open menu layout */
    public void showMenuLayout() {
        menuLinearLayout.setAnimation(null);
        TranslateAnimation animation = new TranslateAnimation(0.0f, 0.0f, 0.0f, 0.0f - Constants.getDensity(FaeryChooseActivity.this) * 355);
        animation.setDuration(500);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                menuLinearLayout.setAnimation(null);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) menuLinearLayout.getLayoutParams();
                params.setMargins(0, 0, 0, 0);
                menuLinearLayout.setLayoutParams(params);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        menuLinearLayout.startAnimation(animation);
        menuLinearLayout.setVisibility(View.VISIBLE);

    }

    /* This Method is used to close menu layout */
    public void hideMenuLayout() {
        menuLinearLayout.setAnimation(null);
        TranslateAnimation animation = new TranslateAnimation(0.0f, 0.0f, 0.0f, Constants.getDensity(FaeryChooseActivity.this) * 355);
        animation.setDuration(500);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) menuLinearLayout.getLayoutParams();
                params.setMargins(0, 0, 0, (int) (0 - Constants.getDensity(FaeryChooseActivity.this) * 355));
                menuLinearLayout.setLayoutParams(params);
                menuLinearLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        menuLinearLayout.startAnimation(animation);
    }
    private String copyAssets(String assetFileName) {
        AssetManager assetManager = getAssets();
        File outFile = null;
        if (assetFileName != null) {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open(assetFileName);
                File directory = new File(Environment.getExternalStorageDirectory() + File.separator + getResources().getString(R.string.app_name));
                if (!directory.exists())
                    directory.mkdir();
                outFile = new File(directory, assetFileName);
                out = new FileOutputStream(outFile);
                copyFile(in, out);
            } catch(IOException e) {
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
            }
        }
        if (outFile != null) {
            return outFile.getAbsolutePath();
        } else {
            return null;
        }
    }
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
    private class AnimationTask extends AsyncTask<String, Void, String> {
        private int count;
        @Override
        protected String doInBackground(String... params) {
            count = 0;
            while (flag) {
                if (intervalTime == 30)
                    count++;
                else
                    count = 0;

                if (count > 150)
                   flag = false;
                index++;
                if (index > 61)
                    index = 0;
                try {
                    Message msg = new Message();
                    msg.what = Constants.MSG_SUCCESS;
                    msg.arg1 = index;
                    mHandler.sendMessage(msg);
                    Thread.sleep(intervalTime);
                } catch (Exception e) {
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            if (faerieChooseList != null && faerieChooseList.size() > 0) {
                Random random = new Random(System.currentTimeMillis());
                selectedIndex = random.nextInt(faerieChooseList.size());
                selectedIndex = selectedIndex % faerieChooseList.size();
                Log.e("TAg","going to next screen");
                showFaerieChoose(selectedIndex);
            }
        }
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    /* This Method is used to pause backgroud music */
    @Override
    public void onPause() {
        if(!this.isFinishing()){
            BackgroundMusicModel.getInstance().changeState(true);

        }

        super.onPause();
    }

      /* This Method is used to resume backgroud music */

    @Override
    public void onResume() {
        if(BackgroundService.mediaPlayer != null)
            if(!BackgroundService.mediaPlayer.isPlaying() && !flag_clicked){
                BackgroundMusicModel.getInstance().changeState(false);
                //Constants.pauseAudio();
            }
        super.onResume();
    }
}
