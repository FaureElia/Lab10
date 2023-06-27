package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.List;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	private RiversDAO dao= new RiversDAO();

	public List<River> getRivers() {
		return this.dao.getAllRivers();
		
	}

	public LocalDate getInizio(River r) {
		return this.dao.getInizio(r);
		
	}
	public LocalDate getFine(River r) {
		return this.dao.getFine(r);
	}

	public int getNumeroMisurazioni(River r) {
		
		return this.dao.getMisurazioni(r);
	}

	public double getFlussoMedio(River r) {
		
		return this.dao.getMedia(r);
	}

	public void simula(River r, double k) {
		Simulazione s=new Simulazione(r,k,this.getFlussoMedio(r));
		s.simula(r,k);
		
		
	}
	


}
