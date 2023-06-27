package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import it.polito.tdp.rivers.db.RiversDAO;

public class Simulazione {
	private River r;
	private double k;
	private double fMed;
	private RiversDAO dao;
	//stato del sistema
	private double C;//occupazione attuale
	private Map<LocalDate,Double> flussiMisurati;
	private double Ccumulativo;
	private double occupazione=0;
	private int insoddisfatti=0;
	
	//costanti
	private double Q;
	private double fOutMin;
	private LocalDate dataInizio;
	private LocalDate dataFine;
	//coda degli eventi
	private PriorityQueue<Event> queue;
	//output

	public Simulazione(River r, double k, double flussoMedio) {
		this.dao=new RiversDAO();
		this.r=r;
		this.k=k;
		this.fMed=flussoMedio*86400;//m^3 al giorno
		this.Q=k*fMed*30;
		this.C=Q/2;
		this.fOutMin=this.fMed*0.8;
		
		this.dataInizio=dao.getInizio(r);
		this.dataFine=dao.getFine(r);
		this.flussiMisurati=this.dao.getFlussi(r);
		
		
	}

	public void simula() {
		this.initialize();
		this.run();	
		System.out.println("occupazione media : "+ this.occupazione/this.dao.getMisurazioni(r));
		System.out.println("insoddisfatti: "+ this.insoddisfatti);
	}

	private void run() {
		this.Ccumulativo=0;
		while (!this.queue.isEmpty()) {
			Event e=this.queue.poll();
			double flussoUscita;
			if(Math.random()<0.05) {
				//extra
				flussoUscita=this.fOutMin*10;
			}else {
				flussoUscita=this.fOutMin;
			}
			double flussoEntrata=this.flussiMisurati.get(e.getData());
			if(C+flussoEntrata>=flussoUscita) {
				this.C=C+flussoEntrata-flussoUscita;
			}else if(C+flussoEntrata<flussoUscita) {
				this.C=0;
				this.insoddisfatti++;
			}
			this.occupazione+=C;
		}
		
	}

	private void initialize() {
		this.queue=new PriorityQueue();
		LocalDate data=this.dataInizio;
		while(data.isBefore(dataFine)) {
			
			this.queue.add(new Event(data,0));
			data=data.plus(1,ChronoUnit.DAYS);	
		}
		
		
	}
	
	

}
