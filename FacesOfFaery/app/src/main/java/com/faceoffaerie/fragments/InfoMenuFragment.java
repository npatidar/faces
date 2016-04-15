package com.faceoffaerie.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.faceoffaerie.R;
import com.faceoffaerie.activities.InfoActivity;
import com.faceoffaerie.activities.SplashActivity;
import com.faceoffaerie.bitmap.GetScreenInfo;
import com.faceoffaerie.bitmap.IsPhoneOrTablet;

public class InfoMenuFragment extends Fragment implements OnClickListener {

	GetScreenInfo getScreenInfo;
	RelativeLayout rootRelativeLayout;
	ImageView info_background;
	Button homeButton;
	ImageView instructionsImageView;
	ImageView creditsImageView;
	ImageView rateImageView;
	ImageView shareImageView;
	ImageView moreImageView;
	ImageView pathWaysToFaeryImageView;
	ImageView faeriesTalesImageView;
	ImageView trollsBookImageView;
	ImageView fernie_brae_imageview;
	ImageView lessonsLearnedImageView;
	ImageView facebookOfImageView;
	ImageView twitterOfImageView;
	ImageView pinterestOfImageView;
	ImageView worldOfImageView;
	ImageView amazonStoreUKImageView;
	ImageView facebookBBPImageView;
	ImageView twitterBPPImageView;
	ImageView pinterestBBPImageView;
	ImageView bbpCreationsImageView;
	ImageView amazonStoreUSImageView;
	ImageView backgroundimage_ovel_instruction;
	ImageView backgroundimage_ovel_credits;
	ImageView backgroundimage_ovel_rate;
	ImageView backgroundimage_ovel_share;
	ImageView backgroundimage_ovel_home;
	Bitmap backgroundBitmap = null;


	ImageView background;

	View rootView;
	public static InfoMenuFragment newInstance(int index) {
		InfoMenuFragment f = new InfoMenuFragment();
		Bundle args = new Bundle();
		args.putInt("index", index);
		f.setArguments(args);
		return f;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e("Info fragment","Info fragment");
		setRetainInstance(true);

    }
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		//getActivity().requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		getScreenInfo= new GetScreenInfo(getContext());
		boolean isTablet = IsPhoneOrTablet.isTablet(getContext());


		if(isTablet){

			Log.e("Istablet", "tablet");
			//getActivity().requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
			rootView = inflater.inflate(R.layout.fragment_info_menu_t, container, false);
			try
			{
				info_background=(ImageView)rootView.findViewById(R.id.info_background);
				info_background.setImageResource(R.drawable.infoscreen_background_tablet);

			}catch (OutOfMemoryError e)
			{
				System.gc();
			}
		}
		else
		{
			//getActivity().requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
			rootView = inflater.inflate(R.layout.fragment_info_menu, container, false);
			try
			{
				info_background=(ImageView)rootView.findViewById(R.id.info_background);
				info_background.setImageResource(R.drawable.infoscreen_background_m);

			}
			catch (OutOfMemoryError e)
			{
				System.gc();
			}
		}
		System.gc();
		initView(rootView);
		setListener();
		return rootView;
	}
	public void initView(View view) {
		rootRelativeLayout = (RelativeLayout) view.findViewById(R.id.rootRelativeLayout);
		homeButton = (Button) view.findViewById(R.id.homeButton);
		instructionsImageView = (ImageView) view.findViewById(R.id.instructionsImageView);
		creditsImageView = (ImageView) view.findViewById(R.id.creditsImageView);
		rateImageView = (ImageView) view.findViewById(R.id.rateImageView);
		shareImageView = (ImageView) view.findViewById(R.id.shareImageView);
		moreImageView = (ImageView) view.findViewById(R.id.moreImageView);
		pathWaysToFaeryImageView = (ImageView) view.findViewById(R.id.pathWaysToFaeryImageView);
		faeriesTalesImageView = (ImageView) view.findViewById(R.id.faeriesTalesImageView);
		trollsBookImageView = (ImageView) view.findViewById(R.id.trollsBookImageView);
		lessonsLearnedImageView = (ImageView) view.findViewById(R.id.lessonsLearnedImageView);
		facebookOfImageView = (ImageView) view.findViewById(R.id.facebookOfImageView);
		twitterOfImageView = (ImageView) view.findViewById(R.id.twitterOfImageView);
		pinterestOfImageView = (ImageView) view.findViewById(R.id.pinterestOfImageView);
		worldOfImageView = (ImageView) view.findViewById(R.id.worldOfImageView);
		amazonStoreUKImageView = (ImageView) view.findViewById(R.id.amazonStoreUKImageView);
		facebookBBPImageView = (ImageView) view.findViewById(R.id.facebookBBPImageView);
		twitterBPPImageView = (ImageView) view.findViewById(R.id.twitterBPPImageView);
		pinterestBBPImageView = (ImageView) view.findViewById(R.id.pinterestBBPImageView);
		bbpCreationsImageView = (ImageView) view.findViewById(R.id.bbpCreationsImageView);
		amazonStoreUSImageView = (ImageView) view.findViewById(R.id.amazonStoreUSImageView);
		fernie_brae_imageview=(ImageView) view.findViewById(R.id.fernie_brae_imageview);
		backgroundimage_ovel_instruction=(ImageView)view.findViewById(R.id.backgroundimage_ovel_instruction);
		backgroundimage_ovel_credits=(ImageView)view.findViewById(R.id.backgroundimage_ovel_credits);
		backgroundimage_ovel_rate=(ImageView)view.findViewById(R.id.backgroundimage_ovel_rate);
		backgroundimage_ovel_share=(ImageView)view.findViewById(R.id.backgroundimage_ovel_share);
		backgroundimage_ovel_home=(ImageView)view.findViewById(R.id.backgroundimage_ovel_home);

		if (InfoActivity.getInstance().fromFaeryChoose) {
			homeButton.setBackgroundResource(R.drawable.close);
		} else {
			homeButton.setBackgroundResource(R.drawable.home);
		}
	}
	@Override
	public void onPause() {
		super.onPause();
	}
	public void onResume() {
		super.onResume();
	}
	public void setListener() {


		instructionsImageView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN: {
						Log.e("Animation Show down", "Animation Show down");
						getActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								backgroundimage_ovel_instruction.setVisibility(View.VISIBLE);
								Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in1);
								backgroundimage_ovel_instruction.startAnimation(animation);
							}
						});

						break;
					}
					case MotionEvent.ACTION_CANCEL: {

						Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out1);
						backgroundimage_ovel_instruction.startAnimation(animation);
						backgroundimage_ovel_instruction .setVisibility(View.INVISIBLE);

						Log.e("Animation Show ACTION","Animation Show ACTION_BUTTON_RELEASE");
						break;
					}
					case MotionEvent.ACTION_UP: {

						Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out1);
						backgroundimage_ovel_instruction.startAnimation(animation);
						backgroundimage_ovel_instruction .setVisibility(View.INVISIBLE);

						Log.e("Animation Show up","Animation Show up");
						break;
					}
				}
				return false;

			}
		});

		creditsImageView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN: {
						getActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								backgroundimage_ovel_credits.setVisibility(View.VISIBLE);
								Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in1);
								backgroundimage_ovel_credits.startAnimation(animation);
							}
						});

						break;
					}

					case MotionEvent.ACTION_CANCEL: {

						Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out1);
						backgroundimage_ovel_credits.startAnimation(animation);
						backgroundimage_ovel_credits .setVisibility(View.INVISIBLE);

						Log.e("Animation Show ACTION", "Animation Show ACTION_BUTTON_RELEASE");
						break;
					}
					case MotionEvent.ACTION_UP: {

						Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out1);
						backgroundimage_ovel_credits.startAnimation(animation);
						backgroundimage_ovel_credits .setVisibility(View.INVISIBLE);
						break;
					}
				}
				return false;

			}
		});

		rateImageView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN: {
						getActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								backgroundimage_ovel_rate.setVisibility(View.VISIBLE);
								Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in1);
								backgroundimage_ovel_rate.startAnimation(animation);
							}
						});

						break;
					}

					case MotionEvent.ACTION_CANCEL: {

						Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out1);
						backgroundimage_ovel_rate.startAnimation(animation);
						backgroundimage_ovel_rate .setVisibility(View.INVISIBLE);

						Log.e("Animation Show ACTION", "Animation Show ACTION_BUTTON_RELEASE");
						break;
					}
					case MotionEvent.ACTION_UP: {

						Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out1);
						backgroundimage_ovel_rate.startAnimation(animation);
						backgroundimage_ovel_rate .setVisibility(View.INVISIBLE);
						break;
					}
				}
				return false;

			}
		});

		shareImageView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN: {
						getActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								backgroundimage_ovel_share.setVisibility(View.VISIBLE);
								Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in1);
								backgroundimage_ovel_share.startAnimation(animation);
							}
						});

						break;
					}

					case MotionEvent.ACTION_CANCEL: {

						Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out1);
						backgroundimage_ovel_share.startAnimation(animation);
						backgroundimage_ovel_share .setVisibility(View.INVISIBLE);

						Log.e("Animation Show ACTION", "Animation Show ACTION_BUTTON_RELEASE");
						break;
					}
					case MotionEvent.ACTION_UP: {

						Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out1);
						backgroundimage_ovel_share.startAnimation(animation);
						backgroundimage_ovel_share.setVisibility(View.INVISIBLE);
						break;
					}
				}
				return false;

			}
		});

		homeButton.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN: {
						getActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								backgroundimage_ovel_home.setVisibility(View.VISIBLE);
								Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in1);
								backgroundimage_ovel_home.startAnimation(animation);
							}
						});

						break;
					}

					case MotionEvent.ACTION_CANCEL: {

						Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out1);
						backgroundimage_ovel_home.startAnimation(animation);
						backgroundimage_ovel_home .setVisibility(View.INVISIBLE);

						Log.e("Animation Show ACTION", "Animation Show ACTION_BUTTON_RELEASE");
						break;
					}
					case MotionEvent.ACTION_UP: {

						Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out1);
						backgroundimage_ovel_home.startAnimation(animation);
						backgroundimage_ovel_home .setVisibility(View.INVISIBLE);
						break;
					}
				}
				return false;

			}
		});



		homeButton.setOnClickListener(this);
		instructionsImageView.setOnClickListener(this);
		creditsImageView.setOnClickListener(this);
		rateImageView.setOnClickListener(this);
		shareImageView.setOnClickListener(this);
		pathWaysToFaeryImageView.setOnClickListener(this);
		faeriesTalesImageView.setOnClickListener(this);
		trollsBookImageView.setOnClickListener(this);
		lessonsLearnedImageView.setOnClickListener(this);
		facebookOfImageView.setOnClickListener(this);
		twitterOfImageView.setOnClickListener(this);
		pinterestOfImageView.setOnClickListener(this);
		worldOfImageView.setOnClickListener(this);
		amazonStoreUKImageView.setOnClickListener(this);
		facebookBBPImageView.setOnClickListener(this);
		twitterBPPImageView.setOnClickListener(this);
		pinterestBBPImageView.setOnClickListener(this);
		bbpCreationsImageView.setOnClickListener(this);
		amazonStoreUSImageView.setOnClickListener(this);
		fernie_brae_imageview.setOnClickListener(this);
	}
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.homeButton: {
				InfoActivity.getInstance().finish();
			}
			break;
			case R.id.instructionsImageView: {
				InfoActivity.getInstance().showInstructionsFragment();
			}
			break;
			case R.id.creditsImageView: {
				InfoActivity.getInstance().showCreditsFragment();
			}
			break;
			case R.id.rateImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://itunes.apple.com/us/app/faces-of-faerie/id874271272?ls=1&mt=8"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.shareImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://itunes.apple.com/us/app/faces-of-faerie/id874271272?ls=1&mt=8"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.pathWaysToFaeryImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://itunes.apple.com/us/app/froud-meditations-pathways/id563141623?ls=1&mt=8"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.faeriesTalesImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.abramsbooks.com/Books/Brian_Froud_s_Faeries__Tales-9781419713866.html"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.trollsBookImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://itunes.apple.com/us/book/trolls/id568842229"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.lessonsLearnedImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vimeo.com/97022699"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.fernie_brae_imageview:
			{
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ferniebrae.com/index"));
				InfoActivity.getInstance().startActivity(intent);

			}
			case R.id.facebookOfImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/RealmofFroud?fref=ts"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.twitterOfImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/RealmofFroud"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.pinterestOfImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.pinterest.com/worldoffroud/pins/"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.worldOfImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://worldoffroud.com/"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.amazonStoreUKImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://astore.amazon.co.uk/woroffro-21"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.facebookBBPImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/pages/Big-Ben-Parliament-Creations-Inc-BBP-Creations/250030905017668"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.twitterBPPImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/BBPCreations"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.pinterestBBPImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.pinterest.com/bbpcreations/"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.bbpCreationsImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bbpcreations.com/"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.amazonStoreUSImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://astore.amazon.com/woroffro-20"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
		}
	}

}