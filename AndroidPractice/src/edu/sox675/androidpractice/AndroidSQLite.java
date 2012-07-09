package edu.sox675.androidpractice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AndroidSQLite extends SQLiteOpenHelper {
//	private final String sqlCreatePersona = "CREATE TABLE personas(id NVARCHAR(12)  NOT NULL PRIMARY KEY AUTOINCREMENT, "
//	+ "login NVARCHAR(100)  NOT NULL)"
//	+ "password NVARCHAR(100)  NOT NULL)"
//	+ "confirm_password NVARCHAR(100)  NOT NULL)";

	public AndroidSQLite(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
//		db.execSQL(sqlCreatePersona);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
