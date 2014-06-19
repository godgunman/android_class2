package com.example.simpleui;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MessageDBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "db";
	private static final String TABLE_NAME = "message";

	private static final int DATABASE_VERSION = 1;
	private static final String CREATE_TABLE = "CREATE TABLE people("
			+ "id INTEGER, " + "text TEXT, " + "isEncrypt INTEGER, "
			+ "PRIMARY KEY(id));";

	public MessageDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
	}

	public void insert(Message message) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("text", message.text);
		values.put("isEncrypt", message.isEncrypt);
		db.insert(TABLE_NAME, null, values);
	}

}
