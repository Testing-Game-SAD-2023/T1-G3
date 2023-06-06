package com.taskOne.demo.service;

import java.sql.Date;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.taskOne.demo.model.Classe;
import com.taskOne.demo.model.Classe.Level;
import com.taskOne.demo.model.Classe.Opponent;
import com.taskOne.demo.repository.IClassRepository;
import com.taskOne.demo.service.iClasseService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service("mainClasseService")
public class DbClasseService implements iClasseService{

	
	@Autowired
	private IClassRepository classeRepository;
	@Autowired
	private DatabaseFileService dbFileService;
	
	
	
	
	
	
	@Override
	public Iterable<Classe> getAll(){
		
		return classeRepository.findAll();
	}
	
	@Override
	public Optional<Classe> getById( int id) {
		
		return classeRepository.findById(id);
		
	}
	
	@Override
	public Classe create(Classe classej) {
		
		return classeRepository.save(classej);
		
	}
	
	//***********************************************************

	/*LAST UPDATE
	public List<Classe> searchByFilters( Level complexity,Integer loc, Date lastUpdate, Opponent recommended) {
		
		List<Classe> lista=null;
		
		lista=classeRepository.findClassesByComplexityAndLocAndLastupdateAndRecommended(complexity, loc, lastUpdate, recommended);
		
		
		
		
		for (Classe classe : lista) {
		    System.out.println("ID: " + classe.getId());
		    System.out.println("Nome: " + classe.getLoc());
		    System.out.println("Anno: " + classe.getComplexity());
		    System.out.println("Descrizione: " + classe.getLastupdate());
		    System.out.println("--------------------");
		}
		
		return lista;
		
	}

	
	*/
	//*************************************************************
	
	
	public List<Classe> ricercaComplexity(Level complexity){
		
		
	return	classeRepository.findByComplexity(complexity);
		
	
	}
	
	
	public List<Classe> ricercaLastUpdate(Date lastUpdate){
		
		
	return	classeRepository.findByLastupdateGreaterThanEqual(lastUpdate);
		
	
	}
	
	
	public List<Classe> ricercaLoc(Integer loc){
		
		return	classeRepository.findByLocGreaterThanEqual(loc);
	}
	
	
	public List<Classe> ricercaRecommended(Opponent recomended){
		
		return	classeRepository.findByRecommended(recomended);
	}
	
	public List<Classe> ricercaComplexityLastUpdate(Level complexity,Date lastUpdate){
		
		
	return	classeRepository.findByComplexityAndLastupdateGreaterThanEqual(complexity,lastUpdate);
		
	
	}
	
	
		public List<Classe> ricercaComplexityLastUpdateRecommended(Level complexity,Date lastUpdate, Opponent recommended){
		
		
		return	classeRepository.findByComplexityAndRecommendedAndLastupdateGreaterThanEqual(complexity,recommended, lastUpdate);
			
		
		}
		
	
		public List<Classe> ricercaComplexityRecommended(Level complexity,Opponent recommended){
			
			return	classeRepository.findByComplexityAndRecommended(complexity, recommended);
			
		}
		
		
		public List<Classe> ricercaComplexityLoc(Level complexity,Integer loc){
			
			return classeRepository.findByComplexityAndLocGreaterThanEqual(complexity, loc);
		}
		
		public List<Classe> ricercaRecommendedLastUpdate(Opponent recommended,Date lastUpdate){
			
			return classeRepository.findByRecommendedAndLastupdateGreaterThanEqual(recommended, lastUpdate);
			
		}
		
		public List<Classe> ricercaRecommendedLoc(Opponent recommended,Integer loc){
			
			return classeRepository.findByRecommendedAndLocGreaterThanEqual(recommended, loc);
		}
		
		public List<Classe> ricercaLastUpdateLoc(Date lastupdate,Integer loc){
		
			return classeRepository.findByLastupdateGreaterThanEqualAndLocGreaterThanEqual(lastupdate, loc);
			
		}
		
		public List<Classe> ricercaComplexityRecommendedLoc(Level complexity, Opponent recommended, Integer loc){
			
			
			return classeRepository.findByComplexityAndRecommendedAndLocGreaterThanEqual(complexity, recommended, loc);
		}
		
		public List<Classe> ricercaRecommendedLastUpdateLoc(Opponent recommended, Date lastUpdate, Integer loc){
			
			return classeRepository.findByRecommendedAndLastupdateGreaterThanEqualAndLocGreaterThanEqual(recommended, lastUpdate, loc);
		}
		
		public List<Classe> ricercaComplexityLastUpdateLoc(Level complexity, Date lastupdate, Integer loc){
			
			return classeRepository.findByComplexityAndLastupdateGreaterThanEqualAndLocGreaterThanEqual(complexity, lastupdate, loc);
		}
		
		
	public List<Classe> ricercaComplete(Level complexity, Opponent recommended,Integer loc, Date lastUpdate){
		
		
	return	classeRepository.findByComplexityAndRecommendedAndLocGreaterThanEqualAndLastupdateGreaterThanEqual(complexity,recommended,loc,lastUpdate);
		
	
	}
	
	
	@Override
	public Optional<Classe> update(int id, Classe classej) {
		
		//non modifica url, ne chiave esterna cioè fileID
		
		//ricerchiamo la foto tramite il metodo find ByID
		Optional<Classe> foundclassej=classeRepository.findById(id);
		
		if(foundclassej.isEmpty()==true) {
			
			return Optional.empty();
			}
		
		// Se c'è la modifichiamo
		
		if((Integer)classej.getId() != null) {
			
			foundclassej.get().setId(classej.getId());
		}
		
		if(classej.getComplexity() != null) {
			
			foundclassej.get().setComplexity(classej.getComplexity());
			
		}
		
		if(classej.getLastupdate() != null) {
			
			foundclassej.get().setLastupdate(classej.getLastupdate());
		}
		
		
		if((Integer)classej.getLoc() != null) {
			
			foundclassej.get().setLoc(classej.getLoc());
		}
		
		
		if(classej.getRecommended() != null) {
			
			foundclassej.get().setRecommended(classej.getRecommended());
		}
		
		
		//la salviamo sul database
		
		classeRepository.save(foundclassej.get());		//notiamo che sia in Create che Update usiamo il metodo SAVE perchè JPA
													//è in grado da capire se l'oggettoi passato è da modificare o creare
		
		//la restituiamo aggiornata al chiamante
		
		return foundclassej;
	}
	
	@Override
	public Boolean delete(int id) {
		
		//ricerchiamo la foto tramite id
		Optional<Classe> foundclassej= classeRepository.findById(id);
		
		//se non è stata trovata restituiamo false
		if(foundclassej.isEmpty()==true) {
			
			return false;
			}
		
		String idFile=foundclassej.get().getFileId();
		
		//in caso contratio cancelliamo la foto dal DB
		
		classeRepository.delete(foundclassej.get());
		
		//----------------------------
		
		dbFileService.delete(idFile);
		
		//--------------------------------
		return true;
	}
	
	
	
}
