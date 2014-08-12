package com.kyxadious.notas;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.kyxadious.notas.dao.NotaDAO;
import com.kyxadious.notas.model.Nota;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.text.Html;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class NovaNotaActivity extends ActionBarActivity {

	private String corNota;
	private ActionBar actionBar;
	private TextView dataTextView;
	private TextView horaTextView;
	private EditText novaNotaEditText;
	private LinearLayout novaNotaLinearLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nova_nota);

		/* Configurando o ambiente do app */
		configuracaoDoAmbiente();
		
		/* TextView */
		dataTextView = (TextView) findViewById(R.id.tv_nova_data);
		horaTextView = (TextView) findViewById(R.id.tv_nova_hora);
		novaNotaEditText = (EditText) findViewById(R.id.ed_nova_nota);
		novaNotaLinearLayout = (LinearLayout) findViewById(R.id.linear_layout_nova_nota);
		
		/* Configurando a hora atual para o horaTextView */
		Time hojeTime = new Time();
		hojeTime.setToNow();
		horaTextView.setText(hojeTime.format("%H:%M").toString());
		
		/* Configurando a data atual para o dataTextView */
		Date hojeDate = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dataTextView.setText(dateFormat.format(hojeDate).toString());
		
		/* Ativando o foco do EditText */
		//novaNotaEditText.setFocusable(true);
		//novaNotaEditText.setFocusableInTouchMode(true);
		
	}
	
	public void corNotaRosa(View view) {	
		String corRosa = "#f8e0e8";
		corNota = corRosa;
		novaNotaLinearLayout.setBackgroundColor(Color.parseColor(corRosa));
		novaNotaEditText.setBackgroundColor(Color.parseColor(corRosa));
	}
	
	public void corNotaFlamingo(View view) {
		String corFlamingo = "#f8e8d0";
		corNota = corFlamingo;
		novaNotaLinearLayout.setBackgroundColor(Color.parseColor(corFlamingo));
		novaNotaEditText.setBackgroundColor(Color.parseColor(corFlamingo));
	}
	
	public void corNotaAmarelo(View view) {
		String corAmarelo = "#f7f4b4";
		corNota = corAmarelo;
		novaNotaLinearLayout.setBackgroundColor(Color.parseColor(corAmarelo));
		novaNotaEditText.setBackgroundColor(Color.parseColor(corAmarelo));
	}
	
	public void corNotaVerde(View view) {
		String corVerde = "#e0f8d8";
		corNota = corVerde;
		novaNotaLinearLayout.setBackgroundColor(Color.parseColor(corVerde));
		novaNotaEditText.setBackgroundColor(Color.parseColor(corVerde));
	}
	
	public void corNotaRoxo(View view) {
		String corRoxo = "#e8e8f8";
		corNota = corRoxo;
		novaNotaLinearLayout.setBackgroundColor(Color.parseColor(corRoxo));
		novaNotaEditText.setBackgroundColor(Color.parseColor(corRoxo));
	}
	
	/* Botão salvar nova nota */
	public void salvarNota(View view) {
			
		String texto = novaNotaEditText.getText().toString();
		
		if (texto.length() != 0) {
			
		    Nota nota = new Nota();
		    nota.setData(dataTextView.getText().toString());
		    nota.setHora(horaTextView.getText().toString());
		    nota.setTexto(texto);
		    nota.setCor(corNota);
		
		    NotaDAO notaDAO = new NotaDAO(getApplicationContext());
		    notaDAO.addNota(nota);
		
		    exibirToast("Nota salva", Toast.LENGTH_SHORT);
		    //Toast.makeText(getApplicationContext(), "Nota salva", Toast.LENGTH_SHORT).show();
		    
		    /* Voltar para tela principal para listar todas as notas */
		    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		    startActivity(intent);
		
		} else {
			exibirToast("Escreva algo na nota", Toast.LENGTH_LONG);
		    //Toast.makeText(getApplicationContext(), "Escreva algo na nota", Toast.LENGTH_LONG).show();
		    
		}
	}
	
	/* Botão cancelar nova nota */
	public void cancelarNota(View view) {
		AlertDialog.Builder mensagemBuilder = new AlertDialog.Builder(this);
		mensagemBuilder.setMessage("Você tem certeza que quer cancelar esta nota?");
		mensagemBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				/* Voltar para tela principal para listar todas as notas */
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
			}
		});
		
		mensagemBuilder.setNegativeButton("Cancelar", null);
		mensagemBuilder.show();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

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
	
	private void exibirToast(String texto, int toastLength){
		Toast msgToast = Toast.makeText(getApplicationContext(), texto, toastLength);
		msgToast.setGravity(Gravity.CENTER, 0, 0);
		msgToast.show();
	}

	private void configuracaoDoAmbiente () {
		/* Cor amarela padrão da nota */
		corNota = "#f7f4b4";
		
		/* Configurando o ActionBar */
		actionBar = getSupportActionBar();
		//actionBar.setDisplayHomeAsUpEnabled(true);
		//actionBar.setHomeButtonEnabled(true);
		//actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#222222")));
		//String title = getResources().getString(R.string.title_activity_nova_nota);
		//actionBar.setTitle(Html.fromHtml("<font color='#ffffff'>"+ title +"</font>"));
	}
	

}