package com.faceoffaerie.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.faceoffaerie.contants.PlistInfo;

import java.util.ArrayList;

public class  Dao {
	public static boolean isActive = false;
	private static final String DATABASE_NAME = "FaceOfFaerie.db";
	private static final int DATABASE_VERSION = 1;
	private static final String FAVOUR_DB_NAME = "FaceOfFaerieDB";

	private SQLiteDatabase db;
	private DatabaseHelper dbHelper;

	public Dao(Context context) {

		dbHelper = new DatabaseHelper(context, DATABASE_NAME, null,
				DATABASE_VERSION);
	}

	public void open() {
		db = dbHelper.getWritableDatabase();
	}

	public void close() {
		db.close();
		dbHelper.close();
	}

	public ArrayList<PlistInfo> getFavourFunc() {
		ArrayList<PlistInfo> list = new ArrayList<PlistInfo>();

		Cursor u = null;
		u = db.query(FAVOUR_DB_NAME, null, null, null, null, null, "PID");

		int count = u.getCount();
		if (count == 0) {
			u.close();
			return list;
		}
		u.moveToFirst();
		for (int i = 0; i < count; i++) {
			try {
				PlistInfo info = new PlistInfo();
				info.PID = u.getInt(0);
				info.fileName = u.getString(1);
				info.image = u.getString(2);
				info.name = u.getString(3);
				info.date = u.getString(4);
				info.reading = u.getString(5);
				info.iPad_Left_Eye_x = u.getInt(6);
				info.iPad_Mouth_y = u.getInt(7);
				info.iPad_Right_Eye_x = u.getInt(8);
				info.iPhone_Left_Eye_x = u.getInt(9);
				info.iPhone_Mouth_y = u.getInt(10);
				info.iPhone_Right_Eye_x = u.getInt(11);
				list.add(info);
			} catch (Exception e) {
			}
			u.moveToNext();
		}
		u.close();

		return list;

	}

	public void removeFavourFunc(int PID) {
		if (getFavourFunc().size() == 0)
			return;
		db.delete(FAVOUR_DB_NAME, "PID=" + "'" + PID + "'", null);
	}
	public boolean existFavourFunc(PlistInfo info) {
		Cursor u = null;
		u = db.query(FAVOUR_DB_NAME, null, "fileName=?", new String[]{ info.fileName }, null, null, null);

		int count = u.getCount();
		if (count == 0) {
			u.close();
			return false;
		}
		u.close();
		return true;

	}
	public int addFavourFunc(PlistInfo info, int force) {
		if (existFavourFunc(info) && force == 0)
			return 1;
		ContentValues values = new ContentValues();
		values.put("fileName", info.fileName);
		values.put("image",info.image);
		values.put("name", info.name);
		values.put("date",info.date);
		values.put("reading", info.reading);
		values.put("iPad_Left_Eye_x", info.iPad_Left_Eye_x);
		values.put("iPad_Mouth_y", info.iPad_Mouth_y);
		values.put("iPad_Right_Eye_x", info.iPad_Right_Eye_x);
		values.put("iPhone_Left_Eye_x", info.iPhone_Left_Eye_x);
		values.put("iPhone_Mouth_y", info.iPhone_Mouth_y);
		values.put("iPhone_Right_Eye_x", info.iPhone_Right_Eye_x);
		db.insert(FAVOUR_DB_NAME, null, values);
		values = null;
		return 0;
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		public Context context;

		public DatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
			this.context = context;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			StringBuffer sb = new StringBuffer();
			sb = new StringBuffer();
			sb.append("CREATE TABLE ");
			sb.append(FAVOUR_DB_NAME);
			sb.append("(");
			sb.append("PID INTEGER primary key autoincrement,");
			sb.append("fileName VARCHAR(100),");
			sb.append("image VARCHAR(100),");
			sb.append("name VARCHAR(50),");
			sb.append("date VARCHAR(50),");
			sb.append("reading VARCHAR(200),");
			sb.append("iPad_Left_Eye_x INTEGER,");
			sb.append("iPad_Mouth_y INTEGER,");
			sb.append("iPad_Right_Eye_x INTEGER,");
			sb.append("iPhone_Left_Eye_x INTEGER,");
			sb.append("iPhone_Mouth_y INTEGER,");
			sb.append("iPhone_Right_Eye_x INTEGER");
			sb.append(")");
			db.execSQL(sb.toString());
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
	}

}
