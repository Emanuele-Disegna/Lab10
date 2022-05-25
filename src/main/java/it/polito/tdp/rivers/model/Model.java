package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	private Map<Integer,River> idMap;
	private RiversDAO dao;
	private int numGiorniSenzaAcqua;
	private double capacitaMedia;
	
	public Model() {
		dao = new RiversDAO();
	}
	
	public Collection<River> getRivers() {
		if(idMap==null) {
			idMap = new HashMap<>();
			dao.getAllRivers(idMap);
		}
		return idMap.values();
	}
	
	public LocalDate getStartDate(River r) {
		return dao.getFirstAndLastDate(r).get(0);
	}
	
	public LocalDate getEndDate(River r) {
		return dao.getFirstAndLastDate(r).get(1);
	}
	
	public Double getAvg(River r) {
		return dao.getAverage(r);
	}
	
	public int getNumMeasurements(River r) {
		return dao.getNumMisurazioni(r);
	}
	
	public void simula(River r, double k) {
		Simulatore s = new Simulatore(dao.getAllFlows(r), k, getAvg(r));
		numGiorniSenzaAcqua = s.run();
		capacitaMedia = s.getCapacitaMedia();
	}

	public int getNumGiorniSenzaAcqua() {
		return numGiorniSenzaAcqua;
	}

	public double getCapacitaMedia() {
		return capacitaMedia;
	}
	
	
}
