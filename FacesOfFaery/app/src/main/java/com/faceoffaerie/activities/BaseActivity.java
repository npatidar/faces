package com.faceoffaerie.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.faceoffaerie.R;
import com.faceoffaerie.contants.AppUtils;
import com.faceoffaerie.interfaces.BackgroundMusicModel;

import java.util.List;

public class BaseActivity extends FragmentActivity {

	public static final String TAG = "BaseActivity";
	private int layoutID;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(layoutID);
	}
	@Override
	public void onResume() {
		AppUtils.log(TAG, "onResume");
		//BackgroundMusicModel.getInstance().changeState(false);
		super.onResume();
	}
	@Override
	public void onPause() {
		AppUtils.log(TAG, "onPause");
		//BackgroundMusicModel.getInstance().changeState(true);
		super.onPause();
	}
	protected void setLayoutId(Context context, final int layoutID) {
		this.layoutID = layoutID;
	}

	public void shareToFacebook(Context context, String text, Uri uri) {
		Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.app_name));
		shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
		shareIntent.putExtra(Intent.EXTRA_STREAM, uri);

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
				context.startActivity(shareIntent);
				return;
			}
		}
	}
	public void shareToTwitter(Context context, String text, Uri uri) {
		Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.app_name));
		shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
		shareIntent.putExtra(Intent.EXTRA_STREAM, uri);

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
				context.startActivity(shareIntent);
				break;
			}
		}
	}
	public void shareToEmail(Context context, String subject, String text, Uri uri) {
		Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
		shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
		shareIntent.putExtra(Intent.EXTRA_STREAM, uri);

		PackageManager pm = context.getPackageManager();
		List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
		for (final ResolveInfo app : activityList)
		{
			if ((app.activityInfo.name).contains("android.gm"))
			{
				final ActivityInfo activity = app.activityInfo;
				final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
				shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
				shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
				shareIntent.setComponent(name);
				context.startActivity(shareIntent);
				break;
			}
		}
	}
	public void shareToSMS(Context context, String text, Uri uri) {
		Intent sendIntent = new Intent(Intent.ACTION_SEND);
		sendIntent.setClassName("com.android.mms", "com.android.mms.ui.ComposeMessageActivity");
		sendIntent.putExtra("sms_body", text);
		sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
		sendIntent.setType("text/plain");
		context.startActivity(sendIntent);
	}
}