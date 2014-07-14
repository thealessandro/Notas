package com.kyxadious.notas.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class NotasSQLiteHelper extends SQLiteOpenHelper{
	
	
	public static final String TABELA = "nota";
	public static final String ID = "id";
	public static final String DATA = "data";
	public static final String HORA = "hora";
	public static final String TEXTO = "texto";
	public static final String COR = "cor";
	public static final String[] COLUNAS = { ID, DATA, HORA, TEXTO, COR };
	
	
	private static final String DATABASE_NAME = "notas.db";
	private static final int DATABASE_VERSION = 1;
	
	
	private static final String DATABASE_DROP = "DROP TABLE IF EXISTS " + TABELA;
	private static final String DATABASE_CREATE = "CREATE TABLE " + TABELA 
													+ "("
													+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
													+ DATA + " TEXT, "
													+ HORA + " TEXT, " 
													+ TEXTO + " TEXT, "
													+ COR + " TEXT "
													+ ")";
	

	public NotasSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		//context.deleteDatabase(DATABASE_NAME);
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DATABASE_DROP);
		onCreate(db);
	}

}


















