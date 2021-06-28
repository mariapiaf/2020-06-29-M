package it.polito.tdp.imdb.model;

public class Adiacente implements Comparable<Adiacente>{

	private Director adiacente;
	private int peso;
	
	public Adiacente(Director adiacente, int peso) {
		super();
		this.adiacente = adiacente;
		this.peso = peso;
	}

	public Director getAdiacente() {
		return adiacente;
	}

	public void setAdiacente(Director adiacente) {
		this.adiacente = adiacente;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return adiacente.firstName+" "+adiacente.lastName+ " " + peso;
	}

	@Override
	public int compareTo(Adiacente o) {
		return -(this.peso-o.peso);
	}
	
	
}
