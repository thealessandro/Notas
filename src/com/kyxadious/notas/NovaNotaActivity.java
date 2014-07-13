package com.kyxadious.notas;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.kyxadious.notas.dao.NotaDAO;
import com.kyxadious.notas.model.Nota;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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

public class NovaNotaActivity extends ActionBarActivity {

	
	private LinearLayout dataHoraLinearLayout;
	private TextView dataTextView;
	private TextView horaTextView;
	private EditText novaNotaEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nova_nota);

		/* ActionBar */
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		
		/* LinearLayout data e hora */
		dataHoraLinearLayout = (LinearLayout) findViewById(R.id.linear_layout_data_hora);
		
		/* TextView */
		dataTextView = (TextView) findViewById(R.id.tv_data);
		horaTextView = (TextView) findViewById(R.id.tv_hora);
		
		/* Configurando a hora atual para o horaTextView */
		Time hojeTime = new Time();
		hojeTime.setToNow();
		horaTextView.setText(hojeTime.format("%H:%M").toString());
		
		/* Configurando a data atual para o dataTextView */
		Date hojeDate = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dataTextView.setText(dateFormat.format(hojeDate).toString());
		
		/* EditeText nova nota */
		novaNotaEditText = (EditText) findViewById(R.id.ed_nova_nota);		
		novaNotaEditText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				novaNotaEditText.setFocusable(true);
				novaNotaEditText.setFocusableInTouchMode(true);
			}
		});
		
		
		
		
	}
	
	public void corNotaRosa(View view) {	
		dataHoraLinearLayout.setBackgroundColor(Color.parseColor("#f8e0e8"));
		novaNotaEditText.setBackgroundColor(Color.parseColor("#f8e0e8"));
	}
	
	public void corNotaFlamingo(View view) {
		dataHoraLinearLayout.setBackgroundColor(Color.parseColor("#f8e8d0"));
		novaNotaEditText.setBackgroundColor(Color.parseColor("#f8e8d0"));
	}
	
	public void corNotaAmarelo(View view) {
		dataHoraLinearLayout.setBackgroundColor(Color.parseColor("#f7f4b4"));
		novaNotaEditText.setBackgroundColor(Color.parseColor("#f7f4b4"));
	}
	
	public void corNotaVerde(View view) {
		dataHoraLinearLayout.setBackgroundColor(Color.parseColor("#e0f8d8"));
		novaNotaEditText.setBackgroundColor(Color.parseColor("#e0f8d8"));
	}
	
	public void corNotaRoxo(View view) {
		dataHoraLinearLayout.setBackgroundColor(Color.parseColor("#e8e8f8"));
		novaNotaEditText.setBackgroundColor(Color.parseColor("#e8e8f8"));
	}
	
	/* Botão salvar nova nota */
	public void salvarNota(View view) {
		
		String texto = novaNotaEditText.getText().toString();
		
		if (texto.length() != 0) {
		    Nota nota = new Nota();
		    nota.setData(dataTextView.getText().toString());
		    nota.setHora(horaTextView.getText().toString());
		    nota.setTexto(texto);
		
		    NotaDAO notaDAO = new NotaDAO(getApplicationContext());
		    notaDAO.addNota(nota);
		
		    /* Voltar para tela principal para listar todas as notas */
		    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		    startActivity(intent);
		
		} else { 
		    Toast.makeText(getApplicationContext(), "Escreva algo na nota", Toast.LENGTH_LONG).show();
		    
		}
	}
	
	/* Botão cancelar nova nota */
	public void cancelarNota(View view) {
		
		AlertDialog.Builder mensagemBuilder = new AlertDialog.Builder(this);
		mensagemBuilder.setMessage("Você tem certeza que quer cancelar essa nota?");
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

		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.nova_nota, menu);
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
		}
		return super.onOptionsItemSelected(item);
	}

	

}
