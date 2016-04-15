package com.faceoffaerie.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.hardware.Camera;
import android.hardware.Camera.Face;
import android.hardware.Camera.Parameters;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Telephony;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
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
import com.faceoffaerie.interfaces.BackgroundMusicModel;
import com.faceoffaerie.objects.SquareCameraPreview;
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

import static com.faceoffaerie.R.color.light_blue;

public class FaeryCameraActivity extends Activity implements OnClickListener, SurfaceHolder.Callback, Camera.PictureCallback, Camera.FaceDetectionListener {

    RelativeLayout rootRelativelayout;
    ImageButton homeButton;
    ImageView autoImageView;
    ImageView takeImageView;
    ImageView switchImageview;
    SquareCameraPreview mPreviewView;
    LinearLayout takeLinearLayout;
    LinearLayout autoLinearLayout;
    LinearLayout switchLinearLayout;
    LinearLayout buttonsLinearLayout;
    Button retakeButton;
    Button connectButton;
    RelativeLayout faerieRelativeLayout;
    ImageView faerieImageView;
    TextView faerieNameTextView;
    TextView faerieReadingTextView;
    TextView textView;
    TextView title;
    LinearLayout menuLinearLayout;
    ImageView facebookMenuImageView;
    ImageView twitterMenuImageView;
    ImageView emailMenuImageView;
    ImageView messageMenuImageView;
    ImageView cancelMenuImageView;
    ImageView retakeMenuImageView;
    ImageView saveMenuImageView;
    ImageView homeMenuImageView;
    ImageView infoMenuImageView;
    GetScreenInfo getScreenInfo;
    Bitmap backgroundBitmap = null;
    ImageView background;
    private int mCameraID;
    private String mFlashMode;
    private Camera mCamera;
    private SurfaceHolder mSurfaceHolder;
    private Face recognizedFace = new Face();
    private ArrayList<PlistInfo> youChooseList;
    private int selectedIndex;
    AlertDialog.Builder alertDialogbuilder;
    boolean flag_clicked=true;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean showAlert;
    private static final String TAG = "FaeryCameraActivity";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getScreenInfo= new GetScreenInfo(this);
        boolean isTablet = IsPhoneOrTablet.isTablet(this);

        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
        showAlert = sharedPreferences.getBoolean("showAlert", true);
        youChooseShowGuidelineMessage();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /* UPDATED CODE
        *  Implemented for Tablet as well as for Mobile Devices
        * */

        if(isTablet){

            Log.e("Istablet", "tablet");
            setContentView(R.layout.activity_faery_camera_t);
            //background=(ImageView)findViewById(R.id.faerycamera);
            rootRelativelayout = (RelativeLayout)findViewById(R.id.rootRelativeLayout);
            try
            {
              //  background.setImageResource(R.drawable.camera_imageoverlay);
            }catch (OutOfMemoryError e)
            {
                System.gc();
            }
        }
        else
        {
            setContentView(R.layout.activity_faery_camera);
           // background=(ImageView)findViewById(R.id.faerycamera);
            rootRelativelayout = (RelativeLayout)findViewById(R.id.rootRelativeLayout);
            try
            {
               // background.setImageResource(R.drawable.camera_imageoverlay);
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

    /* This Method is implemented to share the imnage via SMS*/

    private String shareImageFileName(){
        final  PlistInfo selectedInfo = youChooseList.get(selectedIndex);
        String imageFile = selectedInfo.image;
        return imageFile;
    }
  /* This Method is used to hide the divider line froim the pop-ups and add color to buttons */


    public void hideDividerLine(){
        final AlertDialog dialog = alertDialogbuilder.show();
        int divierId = dialog.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
        View divider = dialog.findViewById(divierId);
        if(divider != null)
            divider.setBackgroundColor(Color.TRANSPARENT);
        Button dsaButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        dsaButton.setTextColor(getResources().getColor(R.color.light_blue));
        Button okButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        okButton.setTextColor(getResources().getColor(R.color.light_blue));
    }

    /* This Method is used to set Title and Message of the pop-ups used throughout the module */

    private TextView setTitle(String titleString){
        TextView title = new TextView(this);
        title.setText(titleString);
//        title.setTextSize((int) (30*screenInfo.getDensity()));
        title.setTextSize(21);
        title.setTypeface(null, Typeface.BOLD);
        title.setTextColor(Color.BLACK);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, (int) (5 * getScreenInfo.getDensity()), (int) (5 * getScreenInfo.getDensity()), (int) (5 * getScreenInfo.getDensity()));
        return title;
    }

    private TextView setMessage(String message) {
        final TextView messageText = new TextView(this);
        messageText.setText(message);
        messageText.setGravity(Gravity.CENTER);
        messageText.setTextColor(Color.BLACK);
//        messageText.setTextSize((int) (18 * screenInfo.getDensity()));
        messageText.setPadding(0, (int) (5 * getScreenInfo.getDensity()), (int) (5 * getScreenInfo.getDensity()), (int) (5 * getScreenInfo.getDensity()));
        messageText.setTextSize(16);
        return messageText;
    }

    /* This method is used to implement the Add the pop up for showing "Eye Lineup Message" */

    public void youChooseShowLineupMessage() {

        TextView title = setTitle(getString(R.string.alert_title_oop));
        TextView textView = setMessage(getString(R.string.alert_txt_lineup));

        alertDialogbuilder = new AlertDialog.Builder(this);
        alertDialogbuilder.setCustomTitle(title);
        alertDialogbuilder.setView(textView);
        alertDialogbuilder.setCancelable(false);
        alertDialogbuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).create();
        hideDividerLine();
    }

     /* This method is used to implement the Add the pop up for showing "Light Message" regarding light*/

    public void youChooseShowLightMessage() {

        TextView title = setTitle(getString(R.string.alert_title_oop));
        TextView textView = setMessage(getString(R.string.alert_txt_oop));

        alertDialogbuilder = new AlertDialog.Builder(this);
        alertDialogbuilder.setCustomTitle(title);
        alertDialogbuilder.setView(textView);
        alertDialogbuilder.setCancelable(false);
        alertDialogbuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).create();
        hideDividerLine();
    }

    /* This method is used to implement the Add the pop up for “Attention” information regarding light*/

    private void youChooseShowGuidelineMessage() {
        if(showAlert) {

            TextView title = setTitle(getString(R.string.alert_title));
            TextView textView = setMessage(getString(R.string.alert_txt) + "\"Connect With Your Faery\"");

            alertDialogbuilder = new AlertDialog.Builder(this);
            alertDialogbuilder.setCustomTitle(title);
            alertDialogbuilder.setView(textView);
            alertDialogbuilder.setCancelable(false);
            alertDialogbuilder.setNegativeButton("Don't Show Again", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    editor.putBoolean("showAlert", false);
                    editor.apply();
                    dialog.dismiss();
                }
            });
            alertDialogbuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    editor.putBoolean("showAlert", true);
                    editor.apply();
                    dialog.dismiss();
                }
            }).create();
            hideDividerLine();
        }
    }

    public void initView() {

        //ButterKnife.inject(this);
        homeButton=(ImageButton)findViewById(R.id.homeButton);
        autoImageView=(ImageView)findViewById(R.id.autoImageView);
        takeImageView=(ImageView)findViewById(R.id.takeImageView);
        switchImageview=(ImageView)findViewById(R.id.switchImageview);
        mPreviewView=(SquareCameraPreview)findViewById(R.id.cameraSurfaceView);
        takeLinearLayout=(LinearLayout)findViewById(R.id.takeLinearLayout);
        autoLinearLayout=(LinearLayout)findViewById(R.id.autoLinearLayout);
        switchLinearLayout=(LinearLayout)findViewById(R.id.switchLinearLayout);
        buttonsLinearLayout=(LinearLayout)findViewById(R.id.buttonsLinearLayout);
        retakeButton=(Button)findViewById(R.id.retakeButton);
        connectButton=(Button)findViewById(R.id.connectButton);
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
        retakeMenuImageView=(ImageView)findViewById(R.id.retakeMenuImageView);
        saveMenuImageView=(ImageView)findViewById(R.id.saveMenuImageView);
        homeMenuImageView=(ImageView)findViewById(R.id.homeMenuImageView);;
        infoMenuImageView=(ImageView)findViewById(R.id.infoMenuImageView);
        faerieNameTextView.setVisibility(View.GONE);
        faerieReadingTextView.setVisibility(View.GONE);
    }

    public void setListener()
    {

        homeButton.setOnClickListener(this);

    }

    public void initData() {
        mCameraID = getIntent().getIntExtra("direction", -1);
        if (mCameraID == -1)
            mCameraID = getFrontCameraID();
        mFlashMode = "auto";
        mPreviewView.getHolder().addCallback(this);

        String xml = Constants.readYouChooseFromAssetsPlist(this);
        ParsePlistParser pp = new ParsePlistParser();
        youChooseList = pp.parsePlist(xml);
    }

    @Override
    public void onFaceDetection(Face[] faces, Camera camera) {
        if (faces != null && faces.length > 0) {
            Face face = faces[0];

            Log.d(TAG, "fACE score :- " + face.score);

            /* Corrected Code
            *  for Face Detection
            * */

            if (face.score > recognizedFace.score) {
                Log.e("score", face.score + "");

                recognizedFace = face;
            }/*else if (face.score < 300) {
                recognizedFace = new Face();
            }*/
            Log.e(TAG, "RECOG score :- "+ recognizedFace.score );
        }
    }
    @Override
    public void onPause() {
        if(!this.isFinishing())
        {
            BackgroundMusicModel.getInstance().changeState(true);
        }
        super.onPause();
        stopCameraPreview();
    }

    @Override
    public void onResume() {

        if(BackgroundService.mediaPlayer != null)
            if(!BackgroundService.mediaPlayer.isPlaying() && !flag_clicked ){
                BackgroundMusicModel.getInstance().changeState(false);
            }
        super.onResume();
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

        /* Corrected Code
        * for Sharing Image Through SMS
        * */
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

    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.homeButton:
            {
                finish();
            }
            break;
            case R.id.autoImageView: {
                if (mFlashMode.equalsIgnoreCase("auto")) {
                    mFlashMode = "on";
                    autoImageView.setImageResource(R.drawable.cameraflashon);
                } else if (mFlashMode.equalsIgnoreCase("on")) {
                    mFlashMode = "off";
                    autoImageView.setImageResource(R.drawable.cameraflashoff);
                } else if (mFlashMode.equalsIgnoreCase("off")) {
                    mFlashMode = "auto";
                    autoImageView.setImageResource(R.drawable.cameraflashauto);
                }
                setupCamera();
            }
            break;
            case R.id.takeImageView: {
                takePicture();
                BackgroundMusicModel.getInstance().changeState(true);
            }
            break;
            case R.id.switchImageview: {

                if (mCameraID == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    mCameraID = getBackCameraID();
                } else {
                     mCameraID = getFrontCameraID();
                }
                restartPreview();
            }
            break;
            case R.id.retakeButton: {
                restartPreview();
                BackgroundMusicModel.getInstance().changeState(false);
            }
            break;
            case R.id.connectButton: {
                connectFunc();
            }
            break;
            case R.id.faerieImageView: {
                if (menuLinearLayout.getVisibility() == View.VISIBLE) {
                    hideMenuLayout();
                } else {
                    showMenuLayout();
                }
            }
            break;
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
            case R.id.retakeMenuImageView: {
                selectedIndex = 0;
                BitmapDrawable d = (BitmapDrawable) faerieImageView.getDrawable();
                if (d != null && d.getBitmap() != null)
                    d.getBitmap().recycle();
                faerieImageView.setImageBitmap(null);
                faerieRelativeLayout.setVisibility(View.GONE);
                hideMenuLayout();
                BackgroundMusicModel.getInstance().changeState(false);
                restartPreview();
            }
            break;
            case R.id.saveMenuImageView: {

                SaveFaeryCurrentDate.faerySavedDate();
                int result = saveFaeryToDB(youChooseList.get(selectedIndex), 0);
                if (result == 0) {

                    hideMenuLayout();
                    title = setTitle(getString(R.string.alert_title_two));
                    textView = setMessage(String.format("\"%s\"\n To saved your faery", youChooseList.get(selectedIndex).name));

                    alertDialogbuilder = new AlertDialog.Builder(FaeryCameraActivity.this);
                    alertDialogbuilder.setCustomTitle(title);
                    alertDialogbuilder.setView(textView);
                    alertDialogbuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create();
                    hideDividerLine();
                    //Constants.showMessage(FaeryCameraActivity.this, String.format("\"%s\"\nTo your saved faeries", youChooseList.get(selectedIndex).name));
                } else if (result == 1) {

                    title = setTitle(getString(R.string.alert_title_one));
                    textView = setMessage(String.format("You have already saved the faery \"%s\" for this reading.\nAre you sure you want to save it again?", youChooseList.get(selectedIndex).name));
                    alertDialogbuilder = new AlertDialog.Builder(FaeryCameraActivity.this);
                    //final AlertDialog.Builder Alert = new AlertDialog.Builder(FaeryCameraActivity.this);
                    alertDialogbuilder.setCustomTitle(title);
                    alertDialogbuilder.setView(textView);
                    alertDialogbuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            saveFaeryToDB(youChooseList.get(selectedIndex), 1);
                            title = setTitle(getString(R.string.alert_title_two));
                            textView = setMessage(String.format("\"%s\"\n To saved your faery",youChooseList.get(selectedIndex).name));
                            alertDialogbuilder = new AlertDialog.Builder(FaeryCameraActivity.this);
                            //final AlertDialog.Builder builder = new AlertDialog.Builder(FaeryCameraActivity.this);
                            alertDialogbuilder.setCustomTitle(title);
                            alertDialogbuilder.setView(textView);
                            alertDialogbuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).create();
                            hideDividerLine();
                            //Constants.showMessage(FaeryChooseActivity.this, String.format("\"%s\"\nTo your saved faeries", faerieChooseList.get(selectedIndex).name));
                            hideMenuLayout();
                        }
                    })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            });
                    hideDividerLine();
                }
                hideMenuLayout();
            }
            break;
            case R.id.facebookMenuImageView: {
                String text = String.format("This is the message the faery \"%s\" has for me.\n %s\n\n You can get the new World of Froud app at:\n https://itunes.apple.com/us/app/faces-of-faerie/id874271272?ls=1&mt=8", youChooseList.get(selectedIndex).name, youChooseList.get(selectedIndex).reading);
                shareToFacebook(FaeryCameraActivity.this, text, shareImageFileName());
                hideMenuLayout();
            }
            break;
            case R.id.twitterMenuImageView: {
                String text = "Faces of Faerie!\n You can get the new World of Froud app at:\n https://itunes.apple.com/us/app/faces-of-faerie/id874271272?ls=1&mt=8\"";
                shareToTwitter(FaeryCameraActivity.this, text, shareImageFileName());
                hideMenuLayout();
            }
            break;
            case R.id.emailMenuImageView: {
                String subject = String.format("Check out my Faery for Today: %s", youChooseList.get(selectedIndex).name);
                String text = String.format("This is the message the faery \"%s\" has for me.\n %s\n\n You can get the new World of Froud app at:\n https://itunes.apple.com/us/app/faces-of-faerie/id874271272?ls=1&mt=8", youChooseList.get(selectedIndex).name, youChooseList.get(selectedIndex).reading);
                shareToEmail(FaeryCameraActivity.this, subject, text, shareImageFileName());
                hideMenuLayout();
            }
            break;
            case R.id.messageMenuImageView: {
                String text = String.format("This is the message the faery \"%s\" has for me.\n %s\n\n You can get the new World of Froud app at:\n https://itunes.apple.com/us/app/faces-of-faerie/id874271272?ls=1&mt=8", youChooseList.get(selectedIndex).name, youChooseList.get(selectedIndex).reading);
                    shareToSMS(FaeryCameraActivity.this, text, shareImageFileName());
                hideMenuLayout();
            }
        }
    }
    private String prepareSdcardImage() {
        String faeriePath = String.format("faerie%d.png", selectedIndex + 1);
        return "file://" + copyAssets(faeriePath);
    }
    public int saveFaeryToDB(PlistInfo info, int force) {
        Dao dao = new Dao(FaeryCameraActivity.this);
        dao.open();
        int result = dao.addFavourFunc(info, force);
        dao.close();
        return result;
    }
    public void showFaerieChoose(int index) {
        BackgroundMusicModel.getInstance().changeState(true);

        final PlistInfo selectedInfo = youChooseList.get(index);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "BarbedorSCTMed.ttf");
        faerieNameTextView.setTypeface(typeface);
        faerieReadingTextView.setTypeface(typeface);

        faerieNameTextView.setText(selectedInfo.name);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setStartOffset(4000);
        alphaAnimation.setDuration(2000);
        faerieNameTextView.startAnimation(alphaAnimation);
        faerieNameTextView.setVisibility(View.VISIBLE);

        faerieReadingTextView.setText(selectedInfo.reading);
        faerieReadingTextView.setTextSize(30);
        alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setStartOffset(2000);
        alphaAnimation.setDuration(4000);
        faerieReadingTextView.startAnimation(alphaAnimation);
        faerieReadingTextView.setVisibility(View.VISIBLE);

        Animation fade_OutAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        faerieImageView.setImageDrawable(Drawable.createFromPath(selectedInfo.image));
        faerieImageView.startAnimation(fade_OutAnimation);
        //fade_OutAnimation.setDuration(500);
        faerieImageView.setVisibility(View.INVISIBLE);
        Animation fade_InAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        faerieImageView.startAnimation(fade_InAnimation);
        //fade_InAnimation.setStartOffset(1500);
        faerieImageView.setVisibility(View.VISIBLE);

        InputStream ims = null;
        try {
            ims = getAssets().open(String.format("faerie%d.png", index + 1));
            Drawable d = Drawable.createFromStream(ims, null);
            faerieImageView.setImageDrawable(d);
        } catch (Exception e) {}
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
        alphaAnimation.setDuration(2000);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                String fileName = selectedInfo.name;
                if (fileName.contains(" "))
                    fileName = fileName.replaceAll(" ", "_");
                if (fileName.contains("'"))
                    fileName = fileName.replaceAll("'", "");
                fileName = fileName.toLowerCase();
                Constants.playAudio(FaeryCameraActivity.this, getResources().getIdentifier(fileName, "raw", getPackageName()));
                String imageFile = selectedInfo.image;
                if(selectedInfo.image.isEmpty()){
                    Log.e("TAG","empty");
                }
                Log.e("show Image","show Image"+imageFile);
                Log.e("show Image","Imageid"+getResources().getIdentifier(""+imageFile, "drawable", getPackageName()));
                faerieImageView.setImageResource(getResources().getIdentifier(imageFile, "drawable", getPackageName()));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        faerieRelativeLayout.startAnimation(alphaAnimation);
        faerieRelativeLayout.setVisibility(View.VISIBLE);
    }
    public void connectFunc() {
        Random random = new Random(System.currentTimeMillis());
        selectedIndex = random.nextInt(youChooseList.size());
        selectedIndex = selectedIndex % youChooseList.size();
        showFaerieChoose(selectedIndex);
    }
    public void processPhoto() {
        if (recognizedFace == null || recognizedFace.score == 0) {
            youChooseShowLightMessage();
//            Constants.showMessage(this, "Oops!", "The faeries need more light to see you!\nPlease retake your picture");
            BackgroundMusicModel.getInstance().changeState(false);
            restartPreview();
            return;
        }
        if (recognizedFace.score < 70) {
            youChooseShowLineupMessage();
//            Constants.showMessage(this, "Oops!", "I'm sorry we did not see you!\nPlease line up your eyes within the eye guides.\nPlease retake your picture");
            BackgroundMusicModel.getInstance().changeState(false);
            restartPreview();
            return;
        }

        switchLinearLayout.setVisibility(View.GONE);
        autoLinearLayout.setVisibility(View.GONE);
        takeLinearLayout.setVisibility(View.GONE);
        buttonsLinearLayout.setVisibility(View.VISIBLE);
    }
    private void getCamera(int cameraID) {
        Log.d("TAG", "get camera with id " + cameraID);
        while( mCamera == null) {
            try {
                mCamera = Camera.open(cameraID);
                mPreviewView.setCamera(mCamera);
            } catch (Exception e) {
                Log.d("TAG", "Can't open camera with id " + cameraID);
                e.printStackTrace();
            }
            try {
                Thread.sleep(100);
            } catch (Exception e) {}

        }
    }

    /**
     * Start the camera preview
     */
    private void startCameraPreview() {
        determineDisplayOrientation();
        setupCamera();

        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.startPreview();
            Camera.Parameters params = mCamera.getParameters();
            if(params.getMaxNumDetectedFaces() > 0){
                Log.e("TAG","face detection supported");
                mCamera.startFaceDetection();
            }
            mCamera.setFaceDetectionListener(this);
        } catch (IOException e) {
            Log.d("TAG", "Can't start camera preview due to IOException " + e);
            e.printStackTrace();
        }
    }

    /**
     * Stop the camera preview
     */
    private void stopCameraPreview() {
        // Nulls out callbacks, stops face detection
        if (mCamera != null) {
            Camera.Parameters params = mCamera.getParameters();
            if(params.getMaxNumDetectedFaces() > 0){
                mCamera.stopFaceDetection();
            }
            mCamera.stopPreview();
            if (mPreviewView != null)
                mPreviewView.setCamera(null);
            mCamera.release();
            mCamera = null;
        }
    }

    /**
     * Determine the current display orientation and rotate the camera preview
     * accordingly
     */
    private void determineDisplayOrientation() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(mCameraID, cameraInfo);

        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;

        switch (rotation) {
            case Surface.ROTATION_0: {
                degrees = 0;
                break;
            }
            case Surface.ROTATION_90: {
                degrees = 90;
                break;
            }
            case Surface.ROTATION_180: {
                degrees = 180;
                break;
            }
            case Surface.ROTATION_270: {
                degrees = 270;
                break;
            }
        }

        int displayOrientation;

        // Camera direction
        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            // Orientation is angle of rotation when facing the camera for
            // the camera image to match the natural orientation of the device
            displayOrientation = (cameraInfo.orientation + degrees) % 360;
            displayOrientation = (360 - displayOrientation) % 360;
        } else {
            displayOrientation = (cameraInfo.orientation - degrees + 360) % 360;
        }

        mCamera.setDisplayOrientation(displayOrientation);
    }

    /**
     * Setup the camera parameters
     */
    private void setupCamera() {
        // Never keep a global parameters
        Camera.Parameters parameters = mCamera.getParameters();

        Camera.Size bestPreviewSize = determineBestPreviewSize(parameters);
        Camera.Size bestPictureSize = determineBestPictureSize(parameters);

        parameters.setPreviewSize(bestPreviewSize.width, bestPreviewSize.height);
        parameters.setPictureSize(bestPictureSize.width, bestPictureSize.height);


        // Set continuous picture focus, if it's supported
        if (parameters.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            parameters.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }
        parameters.setJpegQuality(100);
        final View changeCameraFlashModeBtn = findViewById(R.id.autoLinearLayout);
        List<String> flashModes = parameters.getSupportedFlashModes();
        if (flashModes != null && flashModes.contains(mFlashMode)) {
            parameters.setFlashMode(mFlashMode);
            changeCameraFlashModeBtn.setVisibility(View.VISIBLE);
        }
//        } else {
//            changeCameraFlashModeBtn.setVisibility(View.INVISIBLE);
//        }

        // Lock in the changes
        mCamera.setParameters(parameters);
    }

    private Camera.Size determineBestPreviewSize(Camera.Parameters parameters) {
        return determineBestSize(parameters.getSupportedPreviewSizes());
    }

    private Camera.Size determineBestPictureSize(Camera.Parameters parameters) {
        return determineBestSize(parameters.getSupportedPictureSizes());
    }

    private Camera.Size determineBestSize(List<Camera.Size> sizes) {
        Camera.Size bestSize = null;
        Camera.Size size;
        int numOfSizes = sizes.size();
        for (int i = 0; i < numOfSizes; i++) {
            size = sizes.get(i);
            boolean isDesireRatio = (size.width / 4) == (size.height / 3);
            boolean isBetterSize = (bestSize == null) || size.width > bestSize.width;

            if (isDesireRatio && isBetterSize) {
                bestSize = size;
            }
        }
        if (bestSize == null) {
            Log.d("TAG", "cannot find the best camera size");
            return sizes.get(numOfSizes - 1);
        }

        return bestSize;
    }

    private void restartPreview() {
        recognizedFace = new Face();
        switchLinearLayout.setVisibility(View.VISIBLE);
        autoLinearLayout.setVisibility(View.VISIBLE);
        takeLinearLayout.setVisibility(View.VISIBLE);
        buttonsLinearLayout.setVisibility(View.GONE);

        stopCameraPreview();

        getCamera(mCameraID);
        startCameraPreview();

    }

    private int getFrontCameraID() {
        PackageManager pm = getPackageManager();
        if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
            return Camera.CameraInfo.CAMERA_FACING_FRONT;
        }

        return getBackCameraID();
    }

    private int getBackCameraID() {
        return Camera.CameraInfo.CAMERA_FACING_BACK;
    }

    /**
     * Take a picture
     */
    private void takePicture() {

        // Shutter callback occurs after the image is captured. This can
        // be used to trigger a sound to let the user know that image is taken
        Camera.ShutterCallback shutterCallback = null;

        // Raw callback occurs when the raw image data is available
        Camera.PictureCallback raw = null;

        // postView callback occurs when a scaled, fully processed
        // postView image is available.
        Camera.PictureCallback postView = null;

        // jpeg callback occurs when the compressed image is available
        if (mCamera != null)
            mCamera.takePicture(shutterCallback, raw, postView, this);
        else
            finish();
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            Constants.pauseAudio();

        }catch(Exception e){
            Log.e("Crashed","Crashed");
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mSurfaceHolder = holder;

        getCamera(mCameraID);
        startCameraPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // The surface is destroyed with the visibility of the SurfaceView is set to View.Invisible
        // stop the preview
        stopCameraPreview();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;

        switch (requestCode) {
            case 1:
                Uri imageUri = data.getData();
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * A picture has been taken
     * @param data
     * @param camera
     */
    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        if (data != null && data.length > 0) {
            processPhoto();
        }
    }

    /**
     * When orientation changes, onOrientationChanged(int) of the listener will be called
     */
    private static class CameraOrientationListener extends OrientationEventListener {

        private int mCurrentNormalizedOrientation;
        private int mRememberedNormalOrientation;

        public CameraOrientationListener(Context context) {
            super(context, SensorManager.SENSOR_DELAY_NORMAL);
        }

        @Override
        public void onOrientationChanged(int orientation) {
            if (orientation != ORIENTATION_UNKNOWN) {
                mCurrentNormalizedOrientation = normalize(orientation);
            }
        }

        private int normalize(int degrees) {
            if (degrees > 315 || degrees <= 45) {
                return 0;
            }

            if (degrees > 45 && degrees <= 135) {
                return 90;
            }

            if (degrees > 135 && degrees <= 225) {
                return 180;
            }

            if (degrees > 225 && degrees <= 315) {
                return 270;
            }

            throw new RuntimeException("The physics as we know them are no more. Watch out for anomalies.");
        }

        public void rememberOrientation() {
            mRememberedNormalOrientation = mCurrentNormalizedOrientation;
        }

        public int getRememberedNormalOrientation() {
            return mRememberedNormalOrientation;
        }
    }
    public void showMenuLayout() {
        menuLinearLayout.setAnimation(null);
        TranslateAnimation animation = new TranslateAnimation(0.0f, 0.0f, 0.0f, 0.0f - Constants.getDensity(FaeryCameraActivity.this) * 355);
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
    public void hideMenuLayout() {
        menuLinearLayout.setAnimation(null);
        TranslateAnimation animation = new TranslateAnimation(0.0f, 0.0f, 0.0f, Constants.getDensity(FaeryCameraActivity.this) * 355);
        animation.setDuration(500);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) menuLinearLayout.getLayoutParams();
                params.setMargins(0, 0, 0, (int) (0 - Constants.getDensity(FaeryCameraActivity.this) * 355));
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
}