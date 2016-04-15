/**
 * manage static variables and shared preferences
 */
package com.faceoffaerie.contants;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.ContextThemeWrapper;

import com.faceoffaerie.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Constants {

	public final static int MSG_SUCCESS = 0;
	public final static int MSG_FAIL = 1;
	private final static String packageName = "com.faceoffaerie";
	private static MediaPlayer mediaPlayer;

	public static void setWidth(Context context, int width) {
		SharedPreferences.Editor editor = context.getSharedPreferences(packageName, 0).edit();
		editor.putInt("width", width);
		editor.commit();
	}

	public static int getWidth(Context context) {
		SharedPreferences pref = context.getSharedPreferences(packageName, 0);
		return pref.getInt("width", 720);
	}

	public static void setHeight(Context context, int height) {
		SharedPreferences.Editor editor = context.getSharedPreferences(packageName, 0).edit();
		editor.putInt("height", height);
		editor.commit();
	}
	public static int getHeight(Context context) {
		SharedPreferences pref = context.getSharedPreferences(packageName, 0);
		return pref.getInt("height", 1280);

	}
	public static void setDensity(Context context, float density) {
		SharedPreferences.Editor editor = context.getSharedPreferences(packageName, 0).edit();
		editor.putFloat("density", density);
		editor.commit();
	}
	public static int[] animArray = {R.drawable.animated_symbol_01, R.drawable.animated_symbol_02,
			R.drawable.animated_symbol_03,R.drawable.animated_symbol_04,R.drawable.animated_symbol_05,
			R.drawable.animated_symbol_06,R.drawable.animated_symbol_07,R.drawable.animated_symbol_08,
			R.drawable.animated_symbol_09,R.drawable.animated_symbol_10,R.drawable.animated_symbol_11,
			R.drawable.animated_symbol_12,R.drawable.animated_symbol_13,R.drawable.animated_symbol_14,
			R.drawable.animated_symbol_15,R.drawable.animated_symbol_16,R.drawable.animated_symbol_17,
			R.drawable.animated_symbol_18,R.drawable.animated_symbol_19,R.drawable.animated_symbol_20,
			R.drawable.animated_symbol_21,R.drawable.animated_symbol_22,R.drawable.animated_symbol_23,
			R.drawable.animated_symbol_24,R.drawable.animated_symbol_25,R.drawable.animated_symbol_26,
			R.drawable.animated_symbol_27,R.drawable.animated_symbol_28,R.drawable.animated_symbol_29,
			R.drawable.animated_symbol_30,R.drawable.animated_symbol_31,R.drawable.animated_symbol_32,
			R.drawable.animated_symbol_33,R.drawable.animated_symbol_34,R.drawable.animated_symbol_35,
			R.drawable.animated_symbol_36,R.drawable.animated_symbol_37,R.drawable.animated_symbol_38,
			R.drawable.animated_symbol_39,R.drawable.animated_symbol_40,R.drawable.animated_symbol_41,
			R.drawable.animated_symbol_42,R.drawable.animated_symbol_43,R.drawable.animated_symbol_44,
			R.drawable.animated_symbol_45,R.drawable.animated_symbol_46,R.drawable.animated_symbol_47,
			R.drawable.animated_symbol_48,R.drawable.animated_symbol_49,R.drawable.animated_symbol_50,
			R.drawable.animated_symbol_52,R.drawable.animated_symbol_52,R.drawable.animated_symbol_53,
			R.drawable.animated_symbol_54,R.drawable.animated_symbol_55,R.drawable.animated_symbol_57,
			R.drawable.animated_symbol_58,R.drawable.animated_symbol_59,R.drawable.animated_symbol_60,
			R.drawable.animated_symbol_61,R.drawable.animated_symbol_62,R.drawable.animated_symbol_63 };
	public static float getDensity(Context context) {
		SharedPreferences pref = context.getSharedPreferences(packageName, 0);
		return pref.getFloat("density", 1.0f);
	}
	public static String readFaerieChooseFromAssetsPlist(Context context) {
		StringBuffer sb = new StringBuffer();
		BufferedReader br=null;
		try {
			br = new BufferedReader(new InputStreamReader(context.getAssets().open("faerie_choose.plist")));
			String temp;
			while ((temp = br.readLine()) != null)
				sb.append(temp);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close(); // stop reading
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return sb.toString();
	}
	public static String readYouChooseFromAssetsPlist(Context context) {
		StringBuffer sb = new StringBuffer();
		BufferedReader br=null;
		try {
			br = new BufferedReader(new InputStreamReader(context.getAssets().open("you_choose.plist")));
			String temp;
			while ((temp = br.readLine()) != null)
				sb.append(temp);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close(); // stop reading
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static void playAudio(Context context, int audioId) {

		Log.e("Music is start out", "music out");
		try {
			mediaPlayer = MediaPlayer.create(context, audioId);
			mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					mp.release();
				}
			});
			mediaPlayer.start();
		}catch (Exception e)
		{
			Log.e("Music File","music file is crash");
		}
	}

	public static void playmusic(Context context, int musicId) {

		Log.e("Music is start out", "music out");
		try {
			mediaPlayer = MediaPlayer.create(context, musicId);
			mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					mp.release();
				}
			});
			mediaPlayer.setLooping(true);
			mediaPlayer.start();
		}catch (Exception e)
		{
			Log.e("Music File","music file is crash");
		}
	}

	public static void pausemusic(){
		if(mediaPlayer != null){
			if (mediaPlayer.isPlaying()){
				mediaPlayer.stop();
			}
		}
	}

	public static void pauseAudio(){
		if(mediaPlayer != null){
			if (mediaPlayer.isPlaying()){
				mediaPlayer.stop();
			}
		}
	}
}
