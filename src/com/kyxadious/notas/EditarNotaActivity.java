package com.kyxadious.notas;

import com.kyxadious.notas.dao.NotaDAO;
import com.kyxadious.notas.model.Nota;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.app.ApplicationErrorReport.AnrInfo;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class EditarNotaActivity extends ActionBarActivity {

	private Nota nota;
	private TextView textViewData; 
	private TextView textViewHora; 
	private EditText editTextTexto;
	private LinearLayout editarNotaLinearLayout;
	
	private static final String ID = "id";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_nota);

		/* ActionBar */
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		
		Intent intent = getIntent();
		String idNota = intent.getStringExtra(ID);
		NotaDAO notaDAO = new NotaDAO(getApplicationContext());
		nota = notaDAO.getNota(idNota);
		
		textViewData = (TextView) findViewById(R.id.tv_editar_data);
		textViewHora = (TextView) findViewById(R.id.tv_editar_hora);
		editTextTexto = (EditText) findViewById(R.id.ed_editar_texto);
		editarNotaLinearLayout = (LinearLayout) findViewById(R.id.linear_layout_editar_nota);
		
		textViewData.setText(nota.getData());
		textViewHora.setText(nota.getHora());
		editTextTexto.setText(nota.getTexto());
		editTextTexto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				editTextTexto.setFocusable(true);
				editTextTexto.setFocusableInTouchMode(true);
			}
		});
		
		
	}
	
	public void corNotaRosa(View view) {
		editarNotaLinearLayout.setBackgroundColor(Color.parseColor("#f8e0e8"));
		editTextTexto.setBackgroundColor(Color.parseColor("#f8e0e8"));
	}
	
    public void corNotaFlamingo(View view) {
    	editarNotaLinearLayout.setBackgroundColor(Color.parseColor("#f8e8d0"));
		editTextTexto.setBackgroundColor(Color.parseColor("#f8e8d0"));
    }

    public void corNotaAmarelo(View view) {
    	editarNotaLinearLayout.setBackgroundColor(Color.parseColor("#f7f4b4"));
		editTextTexto.setBackgroundColor(Color.parseColor("#f7f4b4"));
    }
	
    public void corNotaVerde(View view) {
    	editarNotaLinearLayout.setBackgroundColor(Color.parseColor("#e0f8d8"));
		editTextTexto.setBackgroundColor(Color.parseColor("#e0f8d8"));
    }
	
	public void corNotaRoxo(View view) {
		editarNotaLinearLayout.setBackgroundColor(Color.parseColor("#e8e8f8"));
		editTextTexto.setBackgroundColor(Color.parseColor("#e8e8f8"));
	}
	
	public void salvarNota(View view) {
        Nota atualizarNota = new Nota();
        atualizarNota.setId(nota.getId());
        atualizarNota.setData(nota.getData());
        atualizarNota.setHora(nota.getHora());
		atualizarNota.setTexto(editTextTexto.getText().toString());
		
		NotaDAO notaDAO = new NotaDAO(getApplicationContext());
		notaDAO.atualizarNota(atualizarNota);
		
		//Voltar para tela principal para listar todas as notas 
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent); 
	}
	
	public void cancelarNota(View view) {
		AlertDialog.Builder mensagemBuilder = new AlertDialog.Builder(this);
		mensagemBuilder.setMessage("Você tem certeza que não quer editar essa nota?");
		mensagemBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				/* Voltar para tela principal para listar todas as notas */
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
			}
		});
		
		mensagemBuilder.setNegativeButton("Não", null);
		mensagemBuilder.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.editar_nota, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		} else if (id == android.R.id.home) {
			Intent intent = new Intent(getApplicationContext(), MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
