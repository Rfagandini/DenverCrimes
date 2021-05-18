package it.polito.tdp.crimes.model;

public class Edges {

	String firstcrime;
	String secondcrime;
	double peso;
	
	
	
	public Edges(String firstcrime, String secondcrime, double peso) {
		
		this.firstcrime = firstcrime;
		this.secondcrime = secondcrime;
		this.peso = peso;
	}
	public String getFirstcrime() {
		return firstcrime;
	}
	public void setFirstcrime(String firstcrime) {
		this.firstcrime = firstcrime;
	}
	public String getSecondcrime() {
		return secondcrime;
	}
	public void setSecondcrime(String secondcrime) {
		this.secondcrime = secondcrime;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return firstcrime + "___"+ secondcrime+"___" + peso;
	}
	
	
	
}