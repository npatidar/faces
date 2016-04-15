package com.faceoffaerie.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

import com.faceoffaerie.R;
import com.faceoffaerie.fragments.InfoCreditsFragment;
import com.faceoffaerie.fragments.InfoInstructionsFragment;
import com.faceoffaerie.fragments.InfoMenuFragment;
import com.faceoffaerie.interfaces.BackgroundMusicModel;
import com.faceoffaerie.services.BackgroundService;

import butterknife.ButterKnife;

public class InfoActivity extends AppCompatActivity implements OnClickListener{

    private final int MENUFRAGMENT = 0;
    private final int INSTRUCTIONSFRAGMENT = 1;
    private final int CREDITSFRAGMENT = 2;

    private static InfoActivity infoActivity;
    private FragmentManager fragmentManager = null;
    private FragmentTransaction fragmentTransaction = null;
    public InfoMenuFragment infoMenuFragment = null;
    public InfoInstructionsFragment infoInstructionsFragment = null;
    public InfoCreditsFragment infoCreditsFragment = null;
    public boolean fromFaeryChoose = false;
    boolean flag = false;

    public static InfoActivity getInstance()
    {
        return infoActivity;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.e("info is back","info is back");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_menu);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        infoActivity = this;
        initView();
        setListener();
        initData();
        showInfoMenuFragment();
        Log.e("info is back 2", "info is back 2");
    }

    public void initView()
    {
        ButterKnife.inject(this);
    }

    public void setListener()
    {

    }

    public void initData() {
        fromFaeryChoose = getIntent().getBooleanExtra("fromFaeryChoose", false);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        infoMenuFragment = InfoMenuFragment.newInstance(MENUFRAGMENT);
        infoMenuFragment.setRetainInstance(true);
        infoInstructionsFragment = InfoInstructionsFragment.newInstance(INSTRUCTIONSFRAGMENT);
        infoInstructionsFragment.setRetainInstance(true);
        infoCreditsFragment = InfoCreditsFragment.newInstance(CREDITSFRAGMENT);
        infoCreditsFragment.setRetainInstance(true);

        fragmentTransaction.add(R.id.rootFrameLayout, infoMenuFragment);
        fragmentTransaction.add(R.id.rootFrameLayout, infoInstructionsFragment);
        fragmentTransaction.add(R.id.rootFrameLayout, infoCreditsFragment);
        fragmentTransaction.commit();

    }
    public void onResume() {
        if(BackgroundService.mediaPlayer != null)
            if(!BackgroundService.mediaPlayer.isPlaying()){
                BackgroundMusicModel.getInstance().changeState(false);
            }
        super.onResume();

        Log.e("info_onresume","info_onresume");

    }

    @Override
    public void onPause() {
        if(!this.isFinishing())
        {
            BackgroundMusicModel.getInstance().changeState(true);
        }
        super.onPause();
        Log.e("info_onpause","info_onpause");

    }
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {

        }
    }
    public void showInfoMenuFragment() {
        flag = false;
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.show(infoMenuFragment);
        fragmentTransaction.hide(infoInstructionsFragment);
        fragmentTransaction.hide(infoCreditsFragment);
        fragmentTransaction.commit();
    }
    public void showInstructionsFragment() {
        flag = true;
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.show(infoInstructionsFragment);
        fragmentTransaction.hide(infoMenuFragment);
        fragmentTransaction.hide(infoCreditsFragment);
        fragmentTransaction.commit();
    }
    public void showCreditsFragment() {
        flag = true;
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.show(infoCreditsFragment);
        fragmentTransaction.hide(infoInstructionsFragment);
        fragmentTransaction.hide(infoMenuFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {

        Log.e("TAG", "onBackPressed");
        if(flag){
            Log.e("TAG", "in side if");
            showInfoMenuFragment();
        }else{
            Log.e("TAG","in side else");
            this.finish();
        }
        //super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "onDestroy");
    }
}
