/*
Copyright 2009-2011 Urban Airship Inc. All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
this list of conditions and the following disclaimer in the documentation
and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE URBAN AIRSHIP INC ``AS IS'' AND ANY EXPRESS OR
IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
EVENT SHALL URBAN AIRSHIP INC OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.faceoffaerie.main;

import android.app.Application;
import android.content.Intent;

import com.crashlytics.android.Crashlytics;
import com.faceoffaerie.contants.AppUtils;
import com.faceoffaerie.services.BackgroundService;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import io.fabric.sdk.android.Fabric;
import android.content.ServiceConnection;
import android.content.ComponentName;
import android.os.IBinder;

public class MyApplication extends Application {

	public static final String TAG = "MyApplication";

	@Override
	public void onCreate() {
		super.onCreate();
		Fabric.with(this, new Crashlytics());

		getApplicationContext().bindService(new Intent(getApplicationContext(), BackgroundService.class), sc, BIND_AUTO_CREATE);
		//getApplicationContext().startService(new Intent(getApplicationContext(), BackgroundService.class));
		// Enable Local Datastore.
		Parse.enableLocalDatastore(this);

		// Add your initialization code here
		Parse.initialize(this, "jcARjQoL20GMjnswvxPvaevS8XkGzrsXLwCXOBP8", "6ZRmwOfZ7HTRB5B0Pwewqx7lNVxVVIxiiQx1XQLH");
		ParseInstallation.getCurrentInstallation().saveInBackground();

		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
		// Optionally enable public read access.
		defaultACL.setPublicReadAccess(true);
		defaultACL.setPublicWriteAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);

		ParsePush.subscribeInBackground("", new SaveCallback() {
			@Override
			public void done(ParseException e) {

			}
		});
	}
	private ServiceConnection sc = new ServiceConnection()
	{
		@Override
		public void onServiceDisconnected(ComponentName name)
		{
			AppUtils.log(TAG, "onServiceDisconnected ComponentName: " + name);
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service)
		{
			AppUtils.log(TAG, "onServiceDisconnected ComponentName: "+ name + "Service: " + service);
		}
	};
}
