package com.kyxadious.notas;

import com.kyxadious.notas.dao.NotaDAO;
import com.kyxadious.notas.model.Nota;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.app.AlertDialog;
import android.app.ApplicationErrorReport.AnrInfo;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

public class EditarNotaActivity extends ActionBarActivity {

	private Nota nota;
	private String corNota;
	private NotaDAO notaDAO;
	private ActionBar actionBar;
	private TextView textViewData; 
	private TextView textViewHora; 
	private EditText editTextTexto;
	private LinearLayout editarNotaLinearLayout;
	
	private static final String ID = "id";
	private static final String TAG = EditarNotaActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_nota);

		/* Configuração do ambiente do app */
		configuracaoDoAmbiente();
		
		try {
		    Intent intent = getIntent();
		    String idNota = intent.getStringExtra(ID);
		    nota = notaDAO.getNota(idNota);
		    corNota = nota.getCor();
		} catch (Exception e) {
			Log.d(TAG, "Problema com a Intent: "+e.toString());
			exibirToast("Aconteceu algo de errado!", Toast.LENGTH_LONG);
		}
		
		textViewData = (TextView) findViewById(R.id.tv_editar_data);
		textViewHora = (TextView) findViewById(R.id.tv_editar_hora);
		editTextTexto = (EditText) findViewById(R.id.ed_editar_texto);
		editarNotaLinearLayout = (LinearLayout) findViewById(R.id.linear_layout_editar_nota);
		
		/* Ativando o foco do EditText */
		//editTextTexto.setFocusable(true);
		//editTextTexto.setFocusableInTouchMode(true);
		
		/* Alimentando os campos */
		textViewData.setText(nota.getData());
		textViewHora.setText(nota.getHora());
		editTextTexto.setText(nota.getTexto());
		editTextTexto.setBackgroundColor(Color.parseColor(nota.getCor()));
		editarNotaLinearLayout.setBackgroundColor(Color.parseColor(nota.getCor()));
		
		
	}
	
	public void corNotaRosa(View view) {
		String corRosa = "#f8e0e8";
		corNota = corRosa;
		editarNotaLinearLayout.setBackgroundColor(Color.parseColor(corRosa));
		editTextTexto.setBackgroundColor(Color.parseColor(corRosa));
	}
	
    public void corNotaFlamingo(View view) {
    	String corFlamingo = "#f8e8d0";
    	corNota = corFlamingo; 
    	editarNotaLinearLayout.setBackgroundColor(Color.parseColor(corFlamingo));
		editTextTexto.setBackgroundColor(Color.parseColor(corFlamingo));
    }

    public void corNotaAmarelo(View view) {
    	String corAmarelo = "#f7f4b4";
    	corNota = corAmarelo;
    	editarNotaLinearLayout.setBackgroundColor(Color.parseColor(corAmarelo));
		editTextTexto.setBackgroundColor(Color.parseColor(corAmarelo));
    }
	
    public void corNotaVerde(View view) {
    	String corVerde = "#e0f8d8";
    	corNota = corVerde;
    	editarNotaLinearLayout.setBackgroundColor(Color.parseColor(corVerde));
		editTextTexto.setBackgroundColor(Color.parseColor(corVerde));
    }
	
	public void corNotaRoxo(View view) {
		String corRoxo = "#e8e8f8";
		corNota = corRoxo;
		editarNotaLinearLayout.setBackgroundColor(Color.parseColor(corRoxo));
		editTextTexto.setBackgroundColor(Color.parseColor(corRoxo));
	}
	
	public void salvarNota(View view) {
		
		/* Mudar a cor do butão quando clicado */
		Button button = (Button) findViewById(R.id.bt_editar_salvar_nota);
		button.setBackgroundColor(Color.parseColor("#a98375"));
		
        Nota atualizarNota = new Nota();
        atualizarNota.setId(nota.getId());
        atualizarNota.setData(nota.getData());
        atualizarNota.setHora(nota.getHora());
		atualizarNota.setTexto(editTextTexto.getText().toString());
		atualizarNota.setCor(corNota);
		
		NotaDAO notaDAO = new NotaDAO(getApplicationContext());
		notaDAO.atualizarNota(atualizarNota);
		
		exibirToast("Nota editada", Toast.LENGTH_SHORT);
		//Toast.makeText(getApplicationContext(), "Nota editada", Toast.LENGTH_SHORT).show();
		
		//Voltar para tela principal para listar todas as notas 
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent); 
		
	}
	
	public void cancelarNota(View view) {
		AlertDialog.Builder mensagemBuilder = new AlertDialog.Builder(this);
		mensagemBuilder.setMessage("Esta nota será cancelada. Você tem certeza disso?");
		mensagemBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
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

	private void exibirToast(String texto, int toastLength){
		Toast msgToast = Toast.makeText(getApplicationContext(), texto, toastLength);
		msgToast.setGravity(Gravity.CENTER, 0, 0);
		msgToast.show();
	}
	
	private void configuracaoDoAmbiente() {
		/* ActionBar */
		actionBar = getSupportActionBar();
		//actionBar.setDisplayHomeAsUpEnabled(true);
		//actionBar.setHomeButtonEnabled(true);
		notaDAO = new NotaDAO(getApplicationContext());
		
	}
	
		
}