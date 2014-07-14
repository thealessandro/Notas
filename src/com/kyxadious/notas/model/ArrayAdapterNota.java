package com.kyxadious.notas.model;

import java.util.List;

import com.kyxadious.notas.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ArrayAdapterNota extends ArrayAdapter<Nota>{
	
	private Context context;
	private int idListViewNota;

	public ArrayAdapterNota(Context context, int resource, List<Nota> objects) {
		super(context, resource, objects);
		this.context = context;
		this.idListViewNota = resource;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		View view = convertView;
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		Nota nota = getItem(position);
		ItemNota itemNota;
		
		if (view == null) {
			itemNota = new ItemNota();
			view = layoutInflater.inflate(idListViewNota, null);
			itemNota.index = (TextView) view.findViewById(R.id.tv_item_index);
			itemNota.data = (TextView) view.findViewById(R.id.tv_item_data);
			itemNota.hora = (TextView) view.findViewById(R.id.tv_item_hora);
			itemNota.texto = (TextView) view.findViewById(R.id.tv_item_texto);
			itemNota.cor = (LinearLayout) view.findViewById(R.id.linear_layout_item);
			view.setTag(itemNota);
		} else {
			itemNota = (ItemNota) view.getTag();
		}
		
		/* configurando itemNota com valores de nota */
		itemNota.index.setText(String.valueOf(nota.getId()));
		itemNota.data.setText(nota.getData());
		itemNota.hora.setText(nota.getHora());
		itemNota.texto.setText(nota.getTexto());
		itemNota.cor.setBackgroundColor(Color.parseColor(nota.getCor()));
		
		return view;
	}
	
	private static class ItemNota {
		public TextView index;
		public TextView data;
		public TextView hora;
		public TextView texto;
		public LinearLayout cor;
	}

}




























