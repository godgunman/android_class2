package com.example.simpleui;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MessageDBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "db";
	private static final String TABLE_NAME = "message";

	private static final int DATABASE_VERSION = 1;
	private static final String CREATE_TABLE = "CREATE TABLE message("
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

	public List<Message> getMessages() {
		SQLiteDatabase db = getReadableDatabase();
		String sql = "SELECT * FROM " + TABLE_NAME;

		Cursor cursor = db.rawQuery(sql, null);
		List<Message> result = new ArrayList<Message>();

		while (cursor.moveToNext()) {
			result.add(new Message(cursor.getString(1), cursor.getInt(2) != 0));
		}
		return result;
	}
}
