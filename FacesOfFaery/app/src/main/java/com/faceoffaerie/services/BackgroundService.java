package com.faceoffaerie.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.faceoffaerie.R;
import com.faceoffaerie.contants.AppUtils;
import com.faceoffaerie.interfaces.BackgroundMusicModel;
import com.faceoffaerie.interfaces.BackgroundMusicModel.OnMusicStateListener;

import java.io.IOException;

public class BackgroundService extends Service implements OnMusicStateListener {

	public static final String TAG = "BackgroundService";
	public static MediaPlayer mediaPlayer = null;

	@Override
	public IBinder onBind(Intent arg0)
    {
		return null;
	}
	@Override
	public void onCreate()
	{
		Log.e("TAG","On create service");
		BackgroundMusicModel.getInstance().setListener(this);
     	playAudio();

		/*PhoneStateListener mPhoneStateListener = new PhoneStateListener() {
			@Override
			public void onCallStateChanged(int state, String incomingNumber) {
				// Test for incoming call, dialing call, active or on hold
				if (state==TelephonyManager.CALL_STATE_RINGING || state==TelephonyManager.CALL_STATE_OFFHOOK)
				{
					// Put here the code to stop your music
				}
				else if(TelephonyManager.EXTRA_STATE.equals(TelephonyManager.EXTRA_STATE_IDLE) ){
					// start music if your music was palying previously

				}
				super.onCallStateChanged(state, incomingNumber);
			}
		};

		TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		mTelephonyMgr.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);*/
	}
	@Override
	public void onStart(Intent intent, int startId)
	{
	}
	@Override
	public void onDestroy()
	{
	}
	public void stateChanged() {
		boolean isBackgroundMode = BackgroundMusicModel.getInstance().getState();
		if (isBackgroundMode)
		{
			AppUtils.log(TAG, "Music Paused " + isBackgroundMode);
			pauseAudio();
		}
		else
		{
			AppUtils.log(TAG, "Music Play " + isBackgroundMode);
			playAudio() ;
		}
	}
	public void playAudio(){
		if (mediaPlayer == null)
		{
			mediaPlayer = MediaPlayer.create(this, R.raw.faerie_loop_short1);
			//mediaPlayer.prepare();
			mediaPlayer.setLooping(true);
			mediaPlayer.start();
		}
		else
		{
			int currentPosition = mediaPlayer.getCurrentPosition();
			mediaPlayer.start();
			mediaPlayer.seekTo(currentPosition);
		}
	}
	public void pauseAudio() {
		if (mediaPlayer != null && mediaPlayer.isPlaying())
		{
			AppUtils.log(TAG, "In pauseAudio mediaPlayer whether playing or not " + mediaPlayer.isPlaying());
			mediaPlayer.pause();
		}
		/*else if (mediaPlayer != null)
		{
			AppUtils.log(TAG, "In pauseAudio mediaPlayer is not null");
			mediaPlayer.reset();
			mediaPlayer = null;
		}*/
	}


}
