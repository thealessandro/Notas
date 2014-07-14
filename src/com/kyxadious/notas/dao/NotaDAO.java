package com.kyxadious.notas.dao;

import java.util.ArrayList;

import com.kyxadious.notas.model.Nota;
import com.kyxadious.notas.sqlite.NotasSQLiteHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class NotaDAO {

	private SQLiteDatabase database;
	private NotasSQLiteHelper dbHelper;
	
	private static final String TAG = NotaDAO.class.getSimpleName();
	
	public NotaDAO(Context context) {
		dbHelper = new NotasSQLiteHelper(context);
	}
	
	public void openReadableDatabase() {
		database = dbHelper.getReadableDatabase();
	}
	
	public void openWritableDatabase() {
		database = dbHelper.getWritableDatabase();
	}
	
	public void closeDatabase() {
		database.close();
	}
	
	/* Adicionar Nota */
	public void addNota(Nota nota) {
		openWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(NotasSQLiteHelper.DATA, nota.getData());
		values.put(NotasSQLiteHelper.HORA, nota.getHora());
		values.put(NotasSQLiteHelper.TEXTO, nota.getTexto());
		values.put(NotasSQLiteHelper.COR, nota.getCor());
		
		database.insert(NotasSQLiteHelper.TABELA, null, values);
		
		closeDatabase();
	}
	
	/* Retornar uma Nota */
	public Nota getNota(String id) {
		openReadableDatabase();
		
		Cursor cursor = database.query(NotasSQLiteHelper.TABELA, 
										NotasSQLiteHelper.COLUNAS, 
										NotasSQLiteHelper.ID + " = ?", 
										new String[] { id }, 
										null, 
										null, 
										null,
										null);
		
		if (cursor != null) {
			cursor.moveToFirst();
		}
		
		Nota nota = new Nota();
		nota.setId(Integer.valueOf(cursor.getString(0)));
		nota.setData(cursor.getString(1));
		nota.setHora(cursor.getString(2));
		nota.setTexto(cursor.getString(3));
		nota.setCor(cursor.getString(4));
		
		cursor.close();
		closeDatabase();
		
		return nota;
	}
	
	/* Todas as Notas */
	public ArrayList<Nota> getTodasNotas() {
		
		ArrayList<Nota> notas = new ArrayList<Nota>();
		String query = "SELECT " + NotasSQLiteHelper.ID + ", "
								  + NotasSQLiteHelper.DATA + ", "
								  + NotasSQLiteHelper.HORA + ", "
								  + NotasSQLiteHelper.TEXTO + ", "
								  + NotasSQLiteHelper.COR + " FROM "
								  + NotasSQLiteHelper.TABELA + " ORDER BY "
								  + NotasSQLiteHelper.ID + " DESC";
		
		openReadableDatabase();
		Cursor cursor = database.rawQuery(query, null);
		
		Nota nota = null;
		if (cursor.moveToFirst()) {
			do {
				nota = new Nota();
				nota.setId(Integer.valueOf(cursor.getString(0)));
				nota.setData(cursor.getString(1));
				nota.setHora(cursor.getString(2));
				nota.setTexto(cursor.getString(3));
				nota.setCor(cursor.getString(4));
						
				notas.add(nota);
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		closeDatabase();
		
		return notas;
	}
	
	/* Atualizar Nota */
	public void atualizarNota(Nota nota) {
		openWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(NotasSQLiteHelper.ID, nota.getId());
		values.put(NotasSQLiteHelper.DATA, nota.getData());
		values.put(NotasSQLiteHelper.HORA, nota.getHora());
		values.put(NotasSQLiteHelper.TEXTO, nota.getTexto());
		values.put(NotasSQLiteHelper.COR, nota.getCor());
		
		database.update(NotasSQLiteHelper.TABELA, 
						values, 
						NotasSQLiteHelper.ID + " = ?", 
						new String[] { String.valueOf(nota.getId()) });
		
		closeDatabase();
	}
	
	/* Deletar Nota */
	public void deletarNota(String id) {
		openWritableDatabase();
		
		database.delete(NotasSQLiteHelper.TABELA, 
						NotasSQLiteHelper.ID + " = ?", 
						new String[] { id } );
		
		closeDatabase();
	}
	

}

































