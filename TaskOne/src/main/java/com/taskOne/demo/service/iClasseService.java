package com.taskOne.demo.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import com.taskOne.demo.model.Classe;
import com.taskOne.demo.model.Classe.Level;
import com.taskOne.demo.model.Classe.Opponent;

public interface iClasseService {

	public Iterable<Classe> getAll();

	public Optional<Classe> getById( int id);
	

	public Classe create(Classe photo);
	
	public Optional<Classe> update(int id, Classe photo);
	
	public Boolean delete(int id);
	
	
	/*
	 * Last update
	public List<Classe> searchByFilters( Level complexity,Integer loc, Date lastUpdate, Opponent recommended);
	*/
	
	
	
	public List<Classe> ricercaComplexity(Level complexity);
	

	public List<Classe> ricercaLastUpdate(Date lastUpdate);
	
	public List<Classe> ricercaLoc(Integer loc);
	
	public List<Classe> ricercaRecommended(Opponent recomended);
	
	public List<Classe> ricercaComplexityLastUpdate(Level complexity,Date lastUpdate);
	
	public List<Classe> ricercaComplexityRecommended(Level complexity,Opponent recommended);
	
	public List<Classe> ricercaComplexityLoc(Level complexity,Integer loc);
	
	
	public List<Classe> ricercaRecommendedLastUpdate(Opponent recommended,Date lastUpdate);
	
	public List<Classe> ricercaRecommendedLoc(Opponent recommended,Integer loc);
	
	public List<Classe> ricercaLastUpdateLoc(Date lastupdate,Integer loc);
	
	public List<Classe> ricercaComplexityLastUpdateRecommended(Level complexity, Date lastUpdate,Opponent recommended);
	
	public List<Classe> ricercaComplexityRecommendedLoc(Level complexity, Opponent recommended, Integer loc);
	
	public List<Classe> ricercaComplexityLastUpdateLoc(Level complexity, Date lastupdate, Integer loc);
	
	public List<Classe> ricercaRecommendedLastUpdateLoc(Opponent recommended, Date lastUpdate, Integer loc);
	
	public List<Classe> ricercaComplete(Level complexity, Opponent recommended,Integer loc, Date lastUpdate);
	
	
	
}
