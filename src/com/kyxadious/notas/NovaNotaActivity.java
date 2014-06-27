package com.kyxadious.notas;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Build;

public class NovaNotaActivity extends ActionBarActivity {

	private EditText notaEditText;
	private LinearLayout dataHoraLinearLayout;
	private TextView dataTextView;
	private TextView horaTextView;
	
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
	
		/* EditeText nova nota */
		notaEditText = (EditText) findViewById(R.id.ed_nova_nota);
		
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
		
		
		
		
	}
	
	public void corNotaRosa(View view) {
		dataHoraLinearLayout.setBackground(view.getBackground());
		notaEditText.setBackground(view.getBackground());
	}
	
	public void corNotaFlamingo(View view) {
		dataHoraLinearLayout.setBackground(view.getBackground());
		notaEditText.setBackground(view.getBackground());
	}
	
	public void corNotaAmarelo(View view) {
		dataHoraLinearLayout.setBackground(view.getBackground());
		notaEditText.setBackground(view.getBackground());
	}
	
	public void corNotaVerde(View view) {
		dataHoraLinearLayout.setBackground(view.getBackground());
		notaEditText.setBackground(view.getBackground());
	}
	
	public void corNotaRoxo(View view) {
		dataHoraLinearLayout.setBackground(view.getBackground());
		notaEditText.setBackground(view.getBackground());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nova_nota, menu);
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
