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
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils.TruncateAt;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
	private AdMobBroadcastReceiver adMobBroadcastReceiver;
	
	private static final String TAG = MainActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
			
		/* AdMob */
		adMobBroadcastReceiver = new AdMobBroadcastReceiver();
		this.registerReceiver(adMobBroadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
		
		
		// Botão para criar nova nota 
		imageViewIconeCriarNota = (ImageView) findViewById(R.id.iv_plus_note);
		imageViewIconeCriarNota.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/* tirar o registro o BroadcastReceiver */
				unregisterReceiver(adMobBroadcastReceiver);
				
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
		
		/* Click: visualizando uma nota */
		listViewNotas.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				TextView textViewTexto = (TextView) view.findViewById(R.id.tv_item_texto);
				
				/* Ellipsize (...) e Single Line*/
				if (textViewTexto.getEllipsize() == TruncateAt.END) {
				    textViewTexto.setEllipsize(null);
					textViewTexto.setSingleLine(false);
				} else { 
				    textViewTexto.setEllipsize(TruncateAt.END);
					textViewTexto.setSingleLine(true);
				}	
				
			}
		});
		
		/* Click longo: deletando uma nota */
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
	
	private class AdMobBroadcastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
		    boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
	        String reason = intent.getStringExtra(ConnectivityManager.EXTRA_REASON);
	        boolean isFailover = intent.getBooleanExtra(ConnectivityManager.EXTRA_IS_FAILOVER, false);

	        @SuppressWarnings("deprecation")
		    NetworkInfo currentNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
	        NetworkInfo otherNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_OTHER_NETWORK_INFO);

	        if (currentNetworkInfo.isConnected()) {      
	        	/* AdMob */
        		adView = (AdView) findViewById(R.id.adView);
        		AdRequest adRequest = new AdRequest.Builder().build();
        		adView.loadAd(adRequest);
            	
            	if (adView.getVisibility() == AdView.GONE)
       		     	adView.setVisibility(View.VISIBLE);
	             
	        } else {
	        	if (adView != null && adView.getVisibility() == AdView.VISIBLE)
       		     	adView.setVisibility(View.GONE);	        	
	       
	        }
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
