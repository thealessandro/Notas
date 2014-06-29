package com.kyxadious.notas.model;

public class Nota {

	private int id;
	private String data;
	private String hora;
	private String texto;
	private String cor;

	public Nota() {

	}

	public Nota(String data, String hora, String texto, String cor) {
		this.data = data;
		this.hora = hora;
		this.texto = texto;
		this.cor = cor;
	}

	public Nota(int id, String data, String hora, String texto, String cor) {
		this.id = id;
		this.data = data;
		this.hora = hora;
		this.texto = texto;
		this.cor = cor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
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

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

}
