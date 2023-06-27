package it.polito.tdp.rivers.model;

import java.time.LocalDate;

public class Event implements Comparable<Event>{
	//evento rappresenta uscita-entrata
	LocalDate data;
	double entrata;
	
	
	
	public Event(LocalDate data, double entrata) {
		super();
		this.data = data;
		this.entrata = entrata;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public double getEntrata() {
		return entrata;
	}
	public void setEntrata(double entrata) {
		this.entrata = entrata;
	}
	@Override
	public int compareTo(Event o) {
		
		return this.data.compareTo(o.data);
	}
	
	
	
	
	

}
