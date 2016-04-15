package com.faceoffaerie.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.faceoffaerie.R;
import com.faceoffaerie.activities.FaeryChooseActivity;
import com.faceoffaerie.activities.InfoActivity;
import com.faceoffaerie.bitmap.GetScreenInfo;
import com.faceoffaerie.bitmap.IsPhoneOrTablet;
import com.faceoffaerie.interfaces.BackgroundMusicModel;

import butterknife.OnClick;

/**
 * Created by Genesis-17 on 4/8/2016.
 */
public class MenuLayoutFragment extends Fragment  implements View.OnClickListener{

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
    GetScreenInfo getScreenInfo;
    Bitmap backgroundBitmap = null;
    boolean isTablet;
    FaeryChooseActivity faeryChooseActivity;
    TextView title;
    TextView textview;
    AlertDialog.Builder Alert;

    private int slideIndex = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Fragment create", "Fragment create");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        faeryChooseActivity=(FaeryChooseActivity)getActivity();
        //getScreenInfo= new GetScreenInfo(getContext());
        isTablet = IsPhoneOrTablet.isTablet(faeryChooseActivity);
        View view;
        if(isTablet){
            Log.e("Fragment tablet","Fragment tablet");
            view = inflater.inflate(R.layout.menulayout_t, container, false);
        }
        else {
            Log.e("Fragment mobile","Fragment mobile");
            view = inflater.inflate(R.layout.menulayout_m, container, false);

        }
        initView(view);
        return view;


    }


    public void onClick(View v)
    {
        Log.e("fragment Onclick", "fragment Onclick");
//        if(v.getId() == R.id.faerieImageView)
//        {
//            if(slideIndex == 0){
//                setSlideIndex(1);
//                FaeryChooseActivity.animationMusicPlayerVolume_Up();
//            }
//            else if(slideIndex == 1)
//            {
//                setSlideIndex(0);
//                FaeryChooseActivity.animationMusicPlayerVolume_Down();
//            }
//        }
        //faeryChooseActivity.itemClick(v.getId());
        Log.e("Onclick", "Onclick");
    }

//    public int getSlideIndex()
//    {
//        return slideIndex;
//    }
//
//    public void setSlideIndex(int slideIndex)
//    {
//        this.slideIndex = slideIndex;
//    }
    public void initView(View view)
    {
        menuLinearLayout=(LinearLayout)view.findViewById(R.id.menuLinearLayout);
        facebookMenuImageView=(ImageView)view.findViewById(R.id.facebookMenuImageView);
        twitterMenuImageView=(ImageView)view.findViewById(R.id.twitterMenuImageView);
        emailMenuImageView=(ImageView)view.findViewById(R.id.emailMenuImageView);
        messageMenuImageView=(ImageView)view.findViewById(R.id.messageMenuImageView);
        cancelMenuImageView=(ImageView)view.findViewById(R.id.cancelMenuImageView);
        reconnectMenuImageView=(ImageView)view.findViewById(R.id.reconnectMenuImageView);
        saveMenuImageView=(ImageView)view.findViewById(R.id.saveMenuImageView);
        homeMenuImageView=(ImageView)view.findViewById(R.id.homeMenuImageView);
        infoMenuImageView=(ImageView)view.findViewById(R.id.infoMenuImageView);
        infoMenuImageView.setOnClickListener(this);

    }

//    public TextView setTitle(String titleString){
//        TextView title = new TextView(getContext());
//        title.setText(titleString);
//        title.setTextSize(22);
//        title.setTypeface(null, Typeface.BOLD);
//        title.setTextColor(Color.BLACK);
//        title.setGravity(Gravity.CENTER);
//        title.setPadding(0, (int) (5 * getScreenInfo.getDensity()), (int) (5 * getScreenInfo.getDensity()), (int) (5 * getScreenInfo.getDensity()));
//        return title;
//    }
//    public TextView setMessage(String message){
//        final TextView messageText = new TextView(getContext());
//        messageText.setText(message);
//        messageText.setTextColor(Color.BLACK);
//        messageText.setGravity(Gravity.CENTER);
//        messageText.setTextSize(15);
//        return messageText;
//    }
//
//    public void hideDividerLine(){
//        final AlertDialog dialog = Alert.show();
//        int divierId = dialog.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
//        View divider = dialog.findViewById(divierId);
//        if(divider != null)
//            divider.setBackgroundColor(Color.TRANSPARENT);
//        Button dsaButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
//        dsaButton.setTextColor(Color.BLUE);
//        Button okButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
//        okButton.setTextColor(Color.BLUE);
//
//    }


        //int viewId = v.getId();


}
