package com.kyxadious.notas;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.os.Build;

public class SobreActivity extends ActionBarActivity {

	private ActionBar actionBar;
	private ImageView imageViewLogo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sobre);
		
		/* Configuração do ambiente do app */
		configuracaoDoAmbiente();
        
        /* ImageView */
        imageViewLogo = (ImageView) findViewById(R.id.iv_sobre_logo);
        
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo, options);
        bitmap = Bitmap.createScaledBitmap(bitmap, 170, 170, false);
        imageViewLogo.setImageBitmap(bitmap);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.sobre, menu);
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
	
	
	private void configuracaoDoAmbiente() {
		/* ActionBar */
		actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        //actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#222222")));
		//String title = getResources().getString(R.string.title_activity_sobre);
		//actionBar.setTitle(Html.fromHtml("<font color='#ffffff'>"+ title +"</font>"));
        
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
