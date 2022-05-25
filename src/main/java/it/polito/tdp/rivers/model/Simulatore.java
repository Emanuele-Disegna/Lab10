package it.polito.tdp.rivers.model;

import java.util.List;
import java.util.PriorityQueue;

public class Simulatore {

	//Parametri
	private double CAPACITA_TOTALE;
	private double FLUSSO_USCITA_MINIMO;
	private int NUM_MISURAZIONI;
	
	//Stato del mondo
	private double capacita;
	
	//Coda di eventi
	private PriorityQueue<Flow> coda;
	
	//Output
	private int numGiorniSenzaAcqua;
	private double sommaCapacita;
	
	public Simulatore(List<Flow> f, double k, double flussoMedio) {
		CAPACITA_TOTALE = k * flussoMedio * 30 /** 60 * 60 * 24*/;
		System.out.println("La capacita totale è: "+CAPACITA_TOTALE);
		
		capacita = CAPACITA_TOTALE/2;
		System.out.println("La capacita iniziale è: "+capacita);
		
		FLUSSO_USCITA_MINIMO = 0.8 * flussoMedio;
		System.out.println("Il flusso di uscita minimo è: "+FLUSSO_USCITA_MINIMO);
		
		NUM_MISURAZIONI = f.size();
		System.out.println("Le misurazioni totali sono: "+NUM_MISURAZIONI);
		
		coda = new PriorityQueue<Flow>(f);
	}
	
	public int run() {
		while(!coda.isEmpty()) {
			Flow f = coda.poll();
			System.out.println(f);
			processaEvento(f);
		}
		return numGiorniSenzaAcqua;
	}

	private void processaEvento(Flow f) {
		double flussoIn = f.getFlow();
		double flussoOut = FLUSSO_USCITA_MINIMO;
		
		if(Math.random()*100<5) {
			//Tiro a caso un numero da 0 a 100 e vedo se cade tra 0 e 5
			flussoOut = flussoOut*10;
		}
		
		capacita += flussoIn - flussoOut;
		System.out.println("Il flusso in ingresso è "+flussoIn);
		System.out.println("Il flusso in uscita è "+flussoOut);
		System.out.println("La capacita è "+capacita);
		
		if(capacita<0) {
			numGiorniSenzaAcqua++;
			capacita=0;
			System.out.println("FAIL");
		} else if(capacita>CAPACITA_TOTALE) {
			//Tracimazione
			capacita=CAPACITA_TOTALE;
		}
		
		sommaCapacita += capacita;
		
	}

	public double getCapacitaMedia() {
		return sommaCapacita/NUM_MISURAZIONI;
	}
	
	
	
}
