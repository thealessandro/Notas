package com.kyxadious.notas;


import java.util.ArrayList;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.kyxadious.notas.dao.NotaDAO;
import com.kyxadious.notas.model.ArrayAdapterNota;
import com.kyxadious.notas.model.Nota;
import com.kyxadious.notas.sqlite.NotasSQLiteHelper;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private AdView adView;
	private ImageView imageViewIconeCriarNota;
	private ListView listViewNotas;
	private ArrayAdapterNota adapterNota;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		
		/* AdMob */
		adView = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);		
	
		
		// Botão para criar nova nota 
		imageViewIconeCriarNota = (ImageView) findViewById(R.id.iv_plus_note);
		imageViewIconeCriarNota.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), NovaNotaActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
			}
		});
		
		
		/* ListViewNotas */
		listViewNotas = (ListView) findViewById(R.id.list_view_notas);
		NotaDAO notaDAO = new NotaDAO(getApplicationContext());
		ArrayList<Nota> notas = notaDAO.getTodasNotas(); 
	    adapterNota = new ArrayAdapterNota(getApplicationContext(), R.layout.item, notas);
		listViewNotas.setAdapter(adapterNota);
		
		
		/* Deletando uma nota */
		listViewNotas.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				TextView idNota = (TextView) view.findViewById(R.id.tv_item_index);
				NotaDAO notaDAO = new NotaDAO(getApplicationContext());
				notaDAO.deletarNota(idNota.getText().toString());
				
				/* atualizar o listView quando um item for deletado do banco */
				adapterNota.remove(adapterNota.getItem(position));
				adapterNota.notifyDataSetChanged();
				
				return true;
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}	

}
