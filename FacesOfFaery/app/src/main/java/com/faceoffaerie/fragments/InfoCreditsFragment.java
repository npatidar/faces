package com.faceoffaerie.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.faceoffaerie.R;
import com.faceoffaerie.activities.InfoActivity;
import com.faceoffaerie.bitmap.GetScreenInfo;
import com.faceoffaerie.bitmap.IsPhoneOrTablet;

public class InfoCreditsFragment extends Fragment implements OnClickListener {

	RelativeLayout rootRelativeLayout;
	ImageButton closeButton;
	ImageView worldOfImageView;
	ImageView bbpCreationsImageView;
	ImageView lillToddImageView;
	GetScreenInfo getScreenInfo;
	Bitmap backgroundBitmap = null;
	ImageView background;

	View rootView;

	public static InfoCreditsFragment newInstance(int index) {
		InfoCreditsFragment f = new InfoCreditsFragment();
		Bundle args = new Bundle();
		args.putInt("index", index);
		f.setArguments(args);
		return f;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
    }
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		getScreenInfo= new GetScreenInfo(getContext());
		boolean isTablet = IsPhoneOrTablet.isTablet(getContext());
		if(isTablet){

			Log.e("Istablet", "tablet");

			rootView = inflater.inflate(R.layout.fragment_info_credits_t, container, false);
			try
			{
				background=(ImageView)rootView.findViewById(R.id.credit_background);
				background.setImageResource(R.drawable.infoscreen_background_tablet);

			}catch (OutOfMemoryError e)
			{
				System.gc();
			}
		}
		else
		{
			rootView = inflater.inflate(R.layout.fragment_info_credits, container, false);
			try
			{
				background=(ImageView)rootView.findViewById(R.id.credit_background);
				background.setImageResource(R.drawable.infoscreen_background_m);
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
		closeButton = (ImageButton) view.findViewById(R.id.closeButton);
		worldOfImageView = (ImageView) view.findViewById(R.id.worldOfImageView);
		bbpCreationsImageView = (ImageView) view.findViewById(R.id.bbpCreationsImageView);
		lillToddImageView = (ImageView) view.findViewById(R.id.lillToddImageView);
	}
	@Override
	public void onPause() {
		super.onPause();
	}
	public void onResume() {
		super.onResume();
	}
	public void setListener() {
		closeButton.setOnClickListener(this);
		worldOfImageView.setOnClickListener(this);
		bbpCreationsImageView.setOnClickListener(this);
		lillToddImageView.setOnClickListener(this);
	}
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.closeButton: {
				InfoActivity.getInstance().showInfoMenuFragment();
			}
			break;
			case R.id.worldOfImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.worldoffroud.com/about/index.php"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.bbpCreationsImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bbpcreations.com/BBP-Creations-Company-iPhone-iPad-Application-Development.php"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.lillToddImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://lilliantoddjones.com/Lillian_Rhiannon/News.html"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
		}
	}

}