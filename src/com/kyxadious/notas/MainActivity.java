package com.kyxadious.notas;


import java.util.ArrayList;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.kyxadious.notas.dao.NotaDAO;
import com.kyxadious.notas.model.ArrayAdapterNota;
import com.kyxadious.notas.model.Nota;
import com.kyxadious.notas.sqlite.NotasSQLiteHelper;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Html;
import android.text.TextUtils.TruncateAt;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	
	private AdView adView;
	private NotaDAO notaDAO;
	private ActionBar actionBar;
	private ArrayList<Nota> notas;
	private ListView listViewNotas;
	private ArrayAdapterNota adapterNota;
	private AdMobBroadcastReceiver adMobBroadcastReceiver;
	
	private static final String ID = "id";
	private static final String TAG = MainActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		
		/* Configuração do ambiente do app */
		configuracaoDoAmbiente();
		
		/* AdMob */
		//this.registerReceiver(adMobBroadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
				
		/* ListViewNotas */
		listViewNotas = (ListView) findViewById(R.id.list_view_notas);
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
				
				TextView textViewIndex = (TextView) view.findViewById(R.id.tv_item_index);
				TextView textViewTexto = (TextView) view.findViewById(R.id.tv_item_texto);
				
				final int positionItem = position;
				final String textoNota = textViewTexto.getText().toString();
				final String idNota = textViewIndex.getText().toString();
                final String[] opcoesCustomAlertDialog = { "Abrir" , "Editar", "Deletar", "Compartilhar" };
			    final ArrayAdapter<String> arrayAdapterCustomAlertDialog = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_alert_dialog, R.id.tv_custom_alert_dialog, opcoesCustomAlertDialog);
				
                AlertDialog.Builder customBuilder = new AlertDialog.Builder(MainActivity.this);
				customBuilder.setAdapter(arrayAdapterCustomAlertDialog, new DialogInterface.OnClickListener() {
					
					/* Escolher uma opeção do CustomAlertDialog  */
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String nameItem = arrayAdapterCustomAlertDialog.getItem(which);
						
						if (nameItem.equals(opcoesCustomAlertDialog[0])) { // Abrir
							Intent intent = new Intent(getApplicationContext(), AbrirNotaActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							intent.putExtra(ID, idNota);
							startActivity(intent);
							
						}else if (nameItem.equals(opcoesCustomAlertDialog[1])) { // Editar
							Intent intent = new Intent(getApplicationContext(), EditarNotaActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							intent.putExtra(ID, idNota);
							startActivity(intent);
							
						} else if (nameItem.equals(opcoesCustomAlertDialog[2])) { // Deletar
							AlertDialog.Builder deleteBuilder = new AlertDialog.Builder(MainActivity.this);
				          	deleteBuilder.setMessage("Esta nota será apagada. Você tem certeza?");
				          	deleteBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									notaDAO.deletarNota(idNota);
																
									/* atualizar o listView quando um item for deletado do banco */ 
									adapterNota.remove(adapterNota.getItem(positionItem));
									adapterNota.notifyDataSetChanged();
									
									/* Atualizar o número total de notas */
									String subTitle = notaDAO.getNumeroTotalNotas();
									actionBar.setSubtitle(subTitle +" notas");
								}
							});
				          	deleteBuilder.setNegativeButton("Cancelar", null);
				          	deleteBuilder.show();
							
						} else if (nameItem.equals(opcoesCustomAlertDialog[3])) { // Compartilhar
							Intent share = new Intent(Intent.ACTION_SEND);
					        share.setType("text/plain");
					        //share.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK); 
					        share.putExtra(Intent.EXTRA_TEXT, textoNota);
					        startActivity(Intent.createChooser(share, "Compartilhar sua nota via"));
							
						}
					}
				});
				customBuilder.show();
				
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
			
		} else if (id == R.id.action_about) {
			Intent intent = new Intent(getApplicationContext(), SobreActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
			
		} else if (id == R.id.action_share) {
			String appNotas = "Confira \"Notas\" - https://play.google.com/store/apps/details?id=com.kyxadious.notas";
			Intent share = new Intent(Intent.ACTION_SEND);
	        share.setType("text/plain");
	        //share.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK); 
	        share.putExtra(Intent.EXTRA_TEXT, appNotas);
	        startActivity(Intent.createChooser(share, "Compartilhar \"Notas\" via"));
			return true;
			
		} else if (id == R.id.action_create_note) {
			Intent intent = new Intent(getApplicationContext(), NovaNotaActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
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

	        /*
	        if (currentNetworkInfo.isConnected()) {      
	        	// AdMob 
        		adView = (AdView) findViewById(R.id.adView);
        		AdRequest adRequest = new AdRequest.Builder().build();
        		adView.loadAd(adRequest);
            	
            	if (adView.getVisibility() == AdView.GONE)
       		     	adView.setVisibility(View.VISIBLE);
	             
	        } else {
	        	if (adView != null && adView.getVisibility() == AdView.VISIBLE)
       		     	adView.setVisibility(View.GONE);	        	
	       
	        }
	        */
		}
	}
	
	
	private void configuracaoDoAmbiente() {
		notaDAO = new NotaDAO(getApplicationContext());
		notas = notaDAO.getTodasNotas(); 
	    adapterNota = new ArrayAdapterNota(getApplicationContext(), R.layout.item, notas);
	    
		/* Configurando o ActionBar */
		actionBar = getSupportActionBar();
		String subTitle = notaDAO.getNumeroTotalNotas();
		actionBar.setSubtitle(subTitle + " notas");
		
		adMobBroadcastReceiver = new AdMobBroadcastReceiver();
	}
	
	@Override
	protected void onDestroy() {
		actionBar = null;
		adMobBroadcastReceiver = null;
		notaDAO = null;
		notas = null;
		adapterNota = null;
		super.onDestroy();
	}
		
}