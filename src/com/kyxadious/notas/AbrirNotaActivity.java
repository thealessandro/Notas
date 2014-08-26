package com.kyxadious.notas;

import com.kyxadious.notas.dao.NotaDAO;
import com.kyxadious.notas.model.Nota;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class AbrirNotaActivity extends ActionBarActivity {

	private Nota nota;
	private NotaDAO notaDAO;
	private ActionBar actionBar;
	private TextView textViewHora;
	private TextView textViewData;
	private TextView textViewTexto;
	private LinearLayout linearLayoutAbrirNota;
	
	private static final String ID = "id";
	private static final String TAG = AbrirNotaActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_abrir_nota);

		/* Configurando o ambiente */
		configuracaoDoAmbiente();
		
		try { 
		    Intent intent = getIntent();
		    String idNota = intent.getStringExtra(ID);
		    nota = notaDAO.getNota(idNota);
		} catch (Exception e) {
			Log.d(TAG, "Problema com a Intent: "+e.toString());
			exibirToast("Aconteceu algo de errado!", Toast.LENGTH_LONG);
		}
			
		textViewData = (TextView) findViewById(R.id.tv_abrir_data);
		textViewHora = (TextView) findViewById(R.id.tv_abrir_hora);
		textViewTexto = (TextView) findViewById(R.id.tv_abrir_texto);
		linearLayoutAbrirNota = (LinearLayout) findViewById(R.id.linear_layout_abrir_nota);
		
		textViewData.setText(nota.getData());
		textViewHora.setText(nota.getHora());
		textViewTexto.setText(nota.getTexto());
		textViewTexto.setBackgroundColor(Color.parseColor(nota.getCor()));
		linearLayoutAbrirNota.setBackgroundColor(Color.parseColor(nota.getCor()));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.abrir_nota, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.action_settings) {
			return true;
			
		} else if (id == R.id.action_editar) {
			Intent intent = new Intent(getApplicationContext(), EditarNotaActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra(ID, String.valueOf(nota.getId()));
			startActivity(intent);
			return true;
			
		} else if (id == R.id.action_deletar) {
			AlertDialog.Builder deletarBuilder = new AlertDialog.Builder(AbrirNotaActivity.this);
			deletarBuilder.setMessage("Esta nota será apagada. Você tem certeza?");
			deletarBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					notaDAO.deletarNota(String.valueOf(nota.getId()));
					
					Intent intent = new Intent(getApplicationContext(), MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
			});
			deletarBuilder.setNegativeButton("Cancelar", null);
			deletarBuilder.show();		
			return true;
			
		} else if (id == android.R.id.home) {
			Intent intent = new Intent(getApplicationContext(), MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
			
		}
	
		return super.onOptionsItemSelected(item);
	}

	private void configuracaoDoAmbiente() {
		/* ActionBar */
		actionBar = getSupportActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		/* NotaDAO */
		notaDAO = new NotaDAO(getApplicationContext());

	}
	
	private void exibirToast(String texto, int toastLength){
		Toast msgToast = Toast.makeText(getApplicationContext(), texto, toastLength);
		msgToast.setGravity(Gravity.CENTER, 0, 0);
		msgToast.show();
	}

}
