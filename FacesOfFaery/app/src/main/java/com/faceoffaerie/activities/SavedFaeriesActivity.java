package com.faceoffaerie.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.faceoffaerie.R;
import com.faceoffaerie.adapter.SavedFaerieListAdapter;
import com.faceoffaerie.bitmap.GetScreenInfo;
import com.faceoffaerie.bitmap.IsPhoneOrTablet;
import com.faceoffaerie.contants.PlistInfo;
import com.faceoffaerie.db.Dao;
import com.faceoffaerie.interfaces.BackgroundMusicModel;
import com.faceoffaerie.services.BackgroundService;
import com.faceoffaerie.swipemenulistview.SwipeMenu;
import com.faceoffaerie.swipemenulistview.SwipeMenuCreator;
import com.faceoffaerie.swipemenulistview.SwipeMenuItem;
import com.faceoffaerie.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

public class SavedFaeriesActivity extends Activity implements OnClickListener{


    RelativeLayout rootRelativeLayout;
    ImageView titleImageView;
    TextView swipeTextView;
    ImageButton homeButton;
    SwipeMenuListView faeryListView;
    Bitmap backgroundBitmap = null;
    GetScreenInfo getScreenInfo;
    ImageView background;
    ImageView backgroundimage_ovel_saved;

    private ArrayList<PlistInfo> savedFaeries = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getScreenInfo= new GetScreenInfo(this);
        boolean isTablet = IsPhoneOrTablet.isTablet(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(isTablet){

            Log.e("Istablet", "tablet");
            setContentView(R.layout.activity_saved_faeries_t);
            background=(ImageView)findViewById(R.id.saved_faery);
            rootRelativeLayout = (RelativeLayout)findViewById(R.id.rootRelativeLayout);
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
            setContentView(R.layout.activity_saved_faeries);
            background=(ImageView)findViewById(R.id.saved_faery);
            rootRelativeLayout = (RelativeLayout)findViewById(R.id.rootRelativeLayout);
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

    public void initView() {
        //ButterKnife.inject(this);
        rootRelativeLayout=(RelativeLayout)findViewById(R.id.rootRelativeLayout);
        titleImageView=(ImageView)findViewById(R.id.titleImageView);
        swipeTextView=(TextView)findViewById(R.id.swipeTextView);
        homeButton=(ImageButton)findViewById(R.id.homeButton);
        faeryListView=(SwipeMenuListView)findViewById(R.id.faeryListView);
        backgroundimage_ovel_saved=(ImageView)findViewById(R.id.backgroundimage_ovel_saved);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "BarbedorSCTMed.ttf");
        swipeTextView.setTypeface(typeface);
    }

    public void setListener() {

        homeButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        SavedFaeriesActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                backgroundimage_ovel_saved.setVisibility(View.VISIBLE);
                                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in1);
                                backgroundimage_ovel_saved.startAnimation(animation);
                            }
                        });

                        break;
                    }
                    case MotionEvent.ACTION_UP: {

                        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out1);
                        backgroundimage_ovel_saved.startAnimation(animation);
                        backgroundimage_ovel_saved.setVisibility(View.INVISIBLE);
                        break;
                    }
                }
                return false;

            }
        });

        homeButton.setOnClickListener(this);
        setSwipeListSetting();
    }

    public void initData() {
        Dao dao = new Dao(this);
        dao.open();
        savedFaeries = dao.getFavourFunc();
        dao.close();
        SavedFaerieListAdapter savedFaerieListAdapter = new SavedFaerieListAdapter(this, savedFaeries, faeryListView);
        faeryListView.setAdapter(savedFaerieListAdapter);
        savedFaerieListAdapter.notifyDataSetChanged();
    }
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.homeButton: {
                finish();
            }
            break;
        }
    }
    public void setSwipeListSetting() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item ff3b30
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(0xffff3b30));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set item title
                deleteItem.setTitle("Delete");
                // set item title fontsize
                deleteItem.setTitleSize(18);
                // set item title font color
                deleteItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        faeryListView.setMenuCreator(creator);

        // step 2. listener item click event
        faeryListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Dao dao = new Dao(SavedFaeriesActivity.this);
                dao.open();
                dao.removeFavourFunc(savedFaeries.get(position).PID);
                dao.close();
                initData();
                return false;
            }
        });

        // set SwipeListener
        faeryListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        // set MenuStateChangeListener
        faeryListView.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen(int position) {
            }

            @Override
            public void onMenuClose(int position) {
            }
        });

        // other setting
//		listView.setCloseInterpolator(new BounceInterpolator());
        faeryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        // test item long click
        faeryListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                //Toast.makeText(getApplicationContext(), position + " long click", Toast.LENGTH_SHORT).show();

                return false;
            }
        });
    }
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
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
