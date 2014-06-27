package com.kyxadious.notas.model;

import java.util.Date;

public class Nota {

	private Date data;
	private String hora;
	private String texto;

	public Nota() {

	}

	public Nota(Date data, String hora, String texto) {
		super();
		this.data = data;
		this.hora = hora;
		this.texto = texto;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

}
