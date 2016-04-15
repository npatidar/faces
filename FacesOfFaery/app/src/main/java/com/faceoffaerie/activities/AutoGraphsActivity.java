package com.faceoffaerie.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.faceoffaerie.R;
import com.faceoffaerie.bitmap.GetScreenInfo;
import com.faceoffaerie.bitmap.IsPhoneOrTablet;
import com.faceoffaerie.contants.Constants;
import com.faceoffaerie.interfaces.BackgroundMusicModel;
import com.faceoffaerie.objects.DrawingView;
import com.faceoffaerie.services.BackgroundService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class AutoGraphsActivity extends Activity implements OnClickListener{


    LinearLayout contentLinearLayout;
    ImageView titleImageView;
    ImageView autoGraphImageView;
    ScrollView faeryScrollView;
    ImageButton homeButton;
    ImageView avatarImageView;
    ImageView logoImageView;
    DrawingView drawImageView;
    TextView swipeTextView;
    GetScreenInfo getScreenInfo;
    Bitmap backgroundBitmap = null;
    ImageView background;
    ImageView backgroundimage_ovel_autograph;
    ImageView faery_saved_image;
    ImageView homeButtonsave;
    ImageView optionsImageView;
    ImageView auto_face_logo;
    ImageView auto_faces_faery_logo;
    TextView title;
    TextView textview;
    AlertDialog.Builder Alert;
    LinearLayout menuLinearLayout;
    ImageView facebookMenuImageView;
    ImageView twitterMenuImageView;
    ImageView emailMenuImageView;
    ImageView messageMenuImageView;
    ImageView cancelMenuImageView;
    ImageView printImageView;
    ImageView homeMenuImageView;
    ImageView infoMenuImageView;
    EditText editText;
    int i = 0;
   // ImageView optionsImageView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getScreenInfo= new GetScreenInfo(this);
        boolean isTablet = IsPhoneOrTablet.isTablet(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(isTablet){

            Log.e("Istablet", "tablet");
            setContentView(R.layout.activity_autographs_t);
            background=(ImageView)findViewById(R.id.autograph_background);
            contentLinearLayout = (LinearLayout)findViewById(R.id.contentLinearLayout);
            try
            {
                background.setImageResource(R.drawable.infoscreen_background_tablet);
            }catch (OutOfMemoryError e)
            {
                System.gc();
            }
        }
        else
        {
            setContentView(R.layout.activity_autographs);
            background=(ImageView)findViewById(R.id.autograph_background);
            contentLinearLayout = (LinearLayout)findViewById(R.id.contentLinearLayout);
            try
            {
            background.setImageResource(R.drawable.infoscreen_background_m);
            }
            catch (OutOfMemoryError e)
            {
                System.gc();
            }
        }
        System.gc();

        initView();
        setListener();
        initData();
    }

    public void initView()
    {
        //ButterKnife.inject(this);
        contentLinearLayout=(LinearLayout)findViewById(R.id.contentLinearLayout);
        titleImageView=(ImageView)findViewById(R.id.titleImageView);
        autoGraphImageView=(ImageView)findViewById(R.id.autoGraphImageView);
        faeryScrollView=(ScrollView)findViewById(R.id.faeryScrollView);
        homeButton=(ImageButton)findViewById(R.id.homeButton);
        avatarImageView=(ImageView)findViewById(R.id.avatarImageView);
        logoImageView=(ImageView)findViewById(R.id.logoImageView);
        drawImageView=(DrawingView)findViewById(R.id.drawImageView);
        swipeTextView=(TextView)findViewById(R.id.swipeTextView);
        faery_saved_image=(ImageView)findViewById(R.id.faery_saved_image);
        auto_face_logo=(ImageView)findViewById(R.id.auto_face_logo);
        auto_faces_faery_logo=(ImageView)findViewById(R.id.auto_faces_faery_logo);
        optionsImageView=(ImageView)findViewById(R.id.optionsImageView);
        homeButtonsave=(ImageView)findViewById(R.id.homeButtonsave);
        menuLinearLayout=(LinearLayout)findViewById(R.id.menuLinearLayout);
        facebookMenuImageView=(ImageView)findViewById(R.id.facebookMenuImageView);
        twitterMenuImageView=(ImageView)findViewById(R.id.twitterMenuImageView);
        emailMenuImageView=(ImageView)findViewById(R.id.emailMenuImageView);
        messageMenuImageView=(ImageView)findViewById(R.id.messageMenuImageView);
        cancelMenuImageView=(ImageView)findViewById(R.id.printImageView);
        printImageView=(ImageView)findViewById(R.id.saveMenuImageView);
        homeMenuImageView=(ImageView)findViewById(R.id.homeMenuImageView);;
        backgroundimage_ovel_autograph=(ImageView)findViewById(R.id.backgroundimage_ovel_autograph);
       // optionsImageView=(ImageView)findViewById(R.id.optionsImageView);
    }

    public void setListener() {

        homeButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        AutoGraphsActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                backgroundimage_ovel_autograph.setVisibility(View.VISIBLE);
                                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in1);
                                backgroundimage_ovel_autograph.startAnimation(animation);
                            }
                        });

                        break;
                    }
                    case MotionEvent.ACTION_UP: {

                        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out1);
                        backgroundimage_ovel_autograph.startAnimation(animation);
                        backgroundimage_ovel_autograph.setVisibility(View.INVISIBLE);
                        break;
                    }
                }
                return false;

            }
        });

        homeButton.setOnClickListener(this);
        avatarImageView.setOnClickListener(this);

//        avatarImageView.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                i=i+1;
//                Handler handler = new Handler() {
//                    @Override
//                    public void close() {
//
//                    }
//
//                    @Override
//                    public void flush() {
//
//                    }
//
//                    @Override
//                    public void publish(LogRecord record) {
//
//                    }
//                };
//                Runnable R = new Runnable() {
//
//                    @Override
//                    public void run() {
//                        i = 0;
//                    }
//                };
//
//                if (i == 1) {
//                    //Single click
//                    //handler.postDelayed(r, 250);
//                } else if (i == 2) {
//                    //Double click
//                    i = 0;
//                }
//
//
//            }
//        });
        homeButtonsave.setOnClickListener(this);
        auto_faces_faery_logo.setOnClickListener(this);
        faery_saved_image.setOnClickListener(this);
        optionsImageView.setOnClickListener(this);

       // optionsImageView.setOnClickListener(this);
    }

//    public void shareToFacebook(Context context, String text, String imageFile) {
//
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(imageFile, "drawable", getPackageName()));
//        File root = android.os.Environment.getExternalStorageDirectory();
//        File dir = new File(root.getAbsolutePath() + "/path");
//        OutputStream out = null;
//        dir.mkdir();
//        File file = new File(dir, "LatestShare.png");
//        try {
//            out = new FileOutputStream(file);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//            out.flush();
//            out.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String path = file.getPath();
//        Uri bmpUri = Uri.parse("file://" + path);
//        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
//        shareIntent.setType("text/plain");
//        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.app_name));
//        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
//        shareIntent.setType("image/*");
//        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
//
//        PackageManager pm = context.getPackageManager();
//        List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
//        for (final ResolveInfo app : activityList)
//        {
//            if ((app.activityInfo.name).contains("facebook"))
//            {
//                final ActivityInfo activity = app.activityInfo;
//                final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
//                shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//                shareIntent.setComponent(name);
//                shareIntent.setType("image/*");
//                context.startActivity(shareIntent);
//                return;
//            }
//        }
//    }
//
//    public void shareToTwitter(Context context, String text, String imageFile) {
//
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(imageFile, "drawable", getPackageName()));
//        File root = android.os.Environment.getExternalStorageDirectory();
//        File dir = new File(root.getAbsolutePath() + "/path");
//        OutputStream out = null;
//        dir.mkdir();
//        File file = new File(dir, "LatestShare.png");
//        try {
//            out = new FileOutputStream(file);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//            out.flush();
//            out.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String path = file.getPath();
//        Uri bmpUri = Uri.parse("file://" + path);
//        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
//        shareIntent.setType("text/plain");
//        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.app_name));
//        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
//        shareIntent.setType("image/*");
//        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
//
//        PackageManager pm = context.getPackageManager();
//        List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
//        for (final ResolveInfo app : activityList)
//        {
//            if ((app.activityInfo.name).contains("com.twitter.android")){
//                final ActivityInfo activity = app.activityInfo;
//                final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
//                shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//                shareIntent.setComponent(name);
//                shareIntent.setType("image/*");
//                context.startActivity(shareIntent);
//                break;
//            }
//        }
//    }
//    public void shareToEmail(Context context, String subject, String text, String imageFile) {
//
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(imageFile, "drawable", getPackageName()));
//        File root = android.os.Environment.getExternalStorageDirectory();
//        File dir = new File(root.getAbsolutePath() + "/path");
//        OutputStream out = null;
//        dir.mkdir();
//        File file = new File(dir, "LatestShare.png");
//        try {
//            out = new FileOutputStream(file);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//            out.flush();
//            out.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String path = file.getPath();
//        Uri bmpUri = Uri.parse("file://" + path);
//        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//        //shareIntent.setType("text/plain");
//        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
//        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
//        shareIntent.setType("image/*");
//        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
//
//        PackageManager pm = context.getPackageManager();
//        List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
//        for (final ResolveInfo app : activityList)
//        {
//            if ((app.activityInfo.name).toString().contains("android.gm"))
//            {
//                final ActivityInfo activity = app.activityInfo;
//                final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
//                shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//                shareIntent.setComponent(name);
//                shareIntent.setType("image/*");
//                startActivity(shareIntent);
//                break;
//            }
//        }
//    }
//
//    public void shareToSMS(Context context, String text, String imageFile) {
//
//        /* Corrected Code
//        * for Sharing Image Through SMS
//        * */
//        //faerieImageView.setImageResource(getResources().getIdentifier(imageFile, "drawable", getPackageName()));
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(imageFile, "drawable", getPackageName()));//put here your image id
//        File root = android.os.Environment.getExternalStorageDirectory();
//        File dir = new File(root.getAbsolutePath() + "/path");
//        OutputStream out = null;
//        dir.mkdir();
//        File file = new File(dir, "LatestShare.png");
//        try {
//            out = new FileOutputStream(file);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//            out.flush();
//            out.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String path = file.getPath();
//        Uri bmpUri = Uri.parse("file://" + path);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) // At least KitKat
//        {
//            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(this); // Need to change the build to API 19
//
//            Intent sendIntent = new Intent(Intent.ACTION_SEND);
//            sendIntent.setType("image/*");
//            sendIntent.putExtra("sms_body", text);
//            sendIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
//            if (defaultSmsPackageName != null)// Can be null in case that there is no default, then the user would be able to choose
//            // any app that support this intent.
//            {
//                sendIntent.setPackage(defaultSmsPackageName);
//            }
//            startActivity(sendIntent);
//
//        }
//        else // For early versions, do what worked for you before.
//        {
//            Intent sendIntent = new Intent(Intent.ACTION_SEND);
//            sendIntent.putExtra("sms_body", text);
//            sendIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
//            sendIntent.setType("image/*");
//            startActivity(sendIntent);
//        }
//    }


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
    public TextView setMessage(String message){
        final TextView messageText = new TextView(this);
        messageText.setText(message);
        messageText.setTextColor(Color.BLACK);
        messageText.setGravity(Gravity.CENTER);
        messageText.setTextSize(15);
        return messageText;
    }


    public void hideDividerLine(){
        final AlertDialog dialog = Alert.show();
        int divierId = dialog.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
        View divider = dialog.findViewById(divierId);
        if(divider != null)
            divider.setBackgroundColor(Color.TRANSPARENT);
        Button dsaButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        dsaButton.setTextColor(Color.BLUE);
        dsaButton.setTextSize(20);
        dsaButton.setTypeface(null, Typeface.BOLD);
        Button okButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        okButton.setTextColor(Color.BLUE);
        okButton.setTextSize(20);
        okButton.setTypeface(null, Typeface.BOLD);

    }

    public void initData() {

    }

    public void hideMenuLayout() {
        menuLinearLayout.setAnimation(null);
        TranslateAnimation animation = new TranslateAnimation(0.0f, 0.0f, 0.0f, Constants.getDensity(AutoGraphsActivity.this) * 355);
        animation.setDuration(500);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) menuLinearLayout.getLayoutParams();
                params.setMargins(0, 0, 0, (int) (0 - Constants.getDensity(AutoGraphsActivity.this) * 355));
                menuLinearLayout.setLayoutParams(params);
                menuLinearLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        menuLinearLayout.startAnimation(animation);
    }
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.homeButton: {
                finish();
            }
            break;
            case R.id.avatarImageView: {
                showPassword();
            }
            break;
            case R.id.auto_faces_faery_logo:{
                showPasswordAgain();
            }
            break;
            case R.id.optionsImageView:{
                showPasswordAgain();
            }
            break;
            case R.id.faery_saved_image:{
                title = setTitle(getString(R.string.Auto_alert_title_one));
                textview = setMessage("You Saccessfull saved your \n autogarph.\n Good Job!");
                Alert = new AlertDialog.Builder(new ContextThemeWrapper(AutoGraphsActivity.this, android.R.style.Theme_Holo_Light));
                Alert.setCustomTitle(title);
                Alert.setView(textview);

                Alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
                showfaeryBoard();
                hideDividerLine();

            }
            case R.id.cancelMenuImageView: {
                hideMenuLayout();
            }
            break;
            case R.id.homeMenuImageView: {
                finish();
            }
            break;
            case R.id.infoMenuImageView: {
                Intent intent = new Intent(this, InfoActivity.class);
                intent.putExtra("fromFaeryChoose", true);
                startActivity(intent);
                hideMenuLayout();
            }
            break;
//            case R.id.facebookMenuImageView: {
//                String text = String.format("This is the message the faery \"%s\" has for me.\n %s\n\n You can get the new World of Froud app at:\n https://itunes.apple.com/us/app/faces-of-faerie/id874271272?ls=1&mt=8", youChooseList.get(selectedIndex).name, youChooseList.get(selectedIndex).reading);
//                shareToFacebook(AutoGraphsActivity.this, text, shareImageFileName());
//                hideMenuLayout();
//            }
//            break;
//            case R.id.twitterMenuImageView: {
//                String text = "Faces of Faerie!\n You can get the new World of Froud app at:\n https://itunes.apple.com/us/app/faces-of-faerie/id874271272?ls=1&mt=8\"";
//                shareToTwitter(AutoGraphsActivity.this, text, shareImageFileName());
//                hideMenuLayout();
//            }
//            break;
//            case R.id.emailMenuImageView: {
//                String subject = String.format("Check out my Faery for Today: %s", youChooseList.get(selectedIndex).name);
//                String text = String.format("This is the message the faery \"%s\" has for me.\n %s\n\n You can get the new World of Froud app at:\n https://itunes.apple.com/us/app/faces-of-faerie/id874271272?ls=1&mt=8", youChooseList.get(selectedIndex).name, youChooseList.get(selectedIndex).reading);
//                shareToEmail(AutoGraphsActivity.this, subject, text, shareImageFileName());
//                hideMenuLayout();
//            }
//            break;
//            case R.id.messageMenuImageView: {
//                String text = String.format("This is the message the faery \"%s\" has for me.\n %s\n\n You can get the new World of Froud app at:\n https://itunes.apple.com/us/app/faces-of-faerie/id874271272?ls=1&mt=8", youChooseList.get(selectedIndex).name, youChooseList.get(selectedIndex).reading);
//                shareToSMS(AutoGraphsActivity.this, text, shareImageFileName());
//                hideMenuLayout();
//            }
//            case R.id.printImageView: {
//                String text = String.format("This is the message the faery \"%s\" has for me.\n %s\n\n You can get the new World of Froud app at:\n https://itunes.apple.com/us/app/faces-of-faerie/id874271272?ls=1&mt=8", youChooseList.get(selectedIndex).name, youChooseList.get(selectedIndex).reading);
//                shareToSMS(AutoGraphsActivity.this, text, shareImageFileName());
//                hideMenuLayout();
//            }

            case R.id.homeButtonsave: {

                    title = setTitle(getString(R.string.Auto_alert_title));
                    textview = setMessage("Would you like to saveyour autograph \n before your leave?");
                    Alert = new AlertDialog.Builder(new ContextThemeWrapper(AutoGraphsActivity.this, android.R.style.Theme_Holo_Light));
                    Alert.setCustomTitle(title);
                    Alert.setView(textview);
                    Alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //saveFaeryToDB(faerieChooseList.get(selectedIndex), 1);


                            title = setTitle(getString(R.string.Auto_alert_title_one));
                            textview = setMessage("You Saccessfull saved your \n autogarph.\n Good Job!");


                            Alert = new AlertDialog.Builder(new ContextThemeWrapper(AutoGraphsActivity.this, android.R.style.Theme_Holo_Light));
                            Alert.setCustomTitle(title);
                            Alert.setView(textview);

                            Alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).create();
                            showfaeryBoard();
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
//            case R.id.optionsImageView:
//            {
//
//            }
//            break;
        }
    public void showPassword() {


        title = setTitle(getString(R.string.Auto_alert));
        Alert = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light));
        Alert.setCustomTitle(title);
        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        Alert.setView(input);
        input.setHint("Password");
        input.setTransformationMethod(PasswordTransformationMethod.getInstance());
        Alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String password = input.getText().toString();
                if ("elfie".equals(password)) {
                    showDrawBoard();
                } else {

                }
            }
        });

        Alert.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }); hideDividerLine();



    }
    public void showPasswordAgain() {


        title = setTitle(getString(R.string.Auto_alert));
        Alert = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light));
        Alert.setCustomTitle(title);
        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        Alert.setView(input);
        input.setHint("Password");
        input.setTransformationMethod(PasswordTransformationMethod.getInstance());
        Alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String password = input.getText().toString();
                if ("elfie".equals(password)) {
                    showDrawBoardAgain();
                } else {

                }
            }
        });

        Alert.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }); hideDividerLine();

    }

    public void showDrawBoard() {

        Animation fade_OutAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        contentLinearLayout.startAnimation(fade_OutAnimation);
        homeButton.startAnimation(fade_OutAnimation);
        avatarImageView.startAnimation(fade_OutAnimation);
        contentLinearLayout.setVisibility(View.INVISIBLE);
        homeButton.setVisibility(View.INVISIBLE);
        avatarImageView.setVisibility(View.INVISIBLE);
        fade_OutAnimation.setDuration(1500);
        Animation fade_InAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        swipeTextView.startAnimation(fade_InAnimation);
        drawImageView.startAnimation(fade_InAnimation);
        homeButtonsave.startAnimation(fade_InAnimation);
        faery_saved_image.startAnimation(fade_InAnimation);
        fade_InAnimation.setStartOffset(1500);
        swipeTextView.setVisibility(View.VISIBLE);
        drawImageView.setVisibility(View.VISIBLE);
        homeButtonsave.setVisibility(View.VISIBLE);
        faery_saved_image.setVisibility(View.VISIBLE);


    }

    public void showDrawBoardAgain() {

        Animation fade_OutAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        auto_face_logo.startAnimation(fade_OutAnimation);
        auto_faces_faery_logo.startAnimation(fade_OutAnimation);
        optionsImageView.startAnimation(fade_OutAnimation);
        fade_OutAnimation.setStartOffset(1500);
        auto_face_logo.setVisibility(View.INVISIBLE);
        auto_faces_faery_logo.setVisibility(View.INVISIBLE);
        optionsImageView.setVisibility(View.INVISIBLE);
        Animation fade_InAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        swipeTextView.startAnimation(fade_InAnimation);
        drawImageView.startAnimation(fade_InAnimation);
        homeButtonsave.startAnimation(fade_InAnimation);
        faery_saved_image.startAnimation(fade_InAnimation);
        fade_InAnimation.setStartOffset(1500);
        swipeTextView.setVisibility(View.VISIBLE);
        drawImageView.setVisibility(View.VISIBLE);
        homeButtonsave.setVisibility(View.VISIBLE);
        faery_saved_image.setVisibility(View.VISIBLE);


    }

   public  void showfaeryBoard()
   {

       Animation fade_OutAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
       swipeTextView.startAnimation(fade_OutAnimation);
       homeButtonsave.startAnimation(fade_OutAnimation);
       faery_saved_image.startAnimation(fade_OutAnimation);
       fade_OutAnimation.setDuration(1500);
       swipeTextView.setVisibility(View.INVISIBLE);
       homeButtonsave.setVisibility(View.INVISIBLE);
       faery_saved_image.setVisibility(View.INVISIBLE);
       Animation fade_InAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
       auto_face_logo.startAnimation(fade_InAnimation);
       auto_faces_faery_logo.startAnimation(fade_InAnimation);
       optionsImageView.startAnimation(fade_InAnimation);
       fade_InAnimation.setStartOffset(1500);
       auto_face_logo.setVisibility(View.VISIBLE);
       auto_faces_faery_logo.setVisibility(View.VISIBLE);
       optionsImageView.setVisibility(View.VISIBLE);



   }

    public void onResume() {
        if(BackgroundService.mediaPlayer != null)
            if(!BackgroundService.mediaPlayer.isPlaying()){
                BackgroundMusicModel.getInstance().changeState(false);
            }
        super.onResume();
    }

    @Override
    public void onPause() {
        if(!this.isFinishing())
        {
            BackgroundMusicModel.getInstance().changeState(true);
        }
        super.onPause();

    }
}
