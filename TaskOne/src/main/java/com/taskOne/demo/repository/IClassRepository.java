package com.taskOne.demo.repository;

import java.sql.Date; 
import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.taskOne.demo.model.Classe;
import com.taskOne.demo.model.Classe.Level;
import com.taskOne.demo.model.Classe.Opponent;


@Repository
public interface IClassRepository extends CrudRepository<Classe, Integer>{

	
	//List<Classe> findByLocAndComplexityAndLastupdateAndRecommended(Integer loc, Level complexity, Date lastupdate, Opponent recommended);
	
	/* Last Solution
	 * 
	 * @Query("SELECT c FROM Classe classe WHERE (:complexity is null or c.complexity = :complexity) "
	 		+ "and (:loc is null or c.loc = :loc)"
	 		+ "and (:lastupdate is null or c.lastupdate = :lastupdate)"
	 		+ "and (:recommended is null or c.recommended = :recommended)")
	    List<Classe> findClassesByComplexityAndLocAndLastupdateAndRecommended(@Param("complexity") Level complexity, @Param("loc") Integer loc,
	    		@Param("lastupdate") Date lastupdate, @Param("recommended") Opponent recommended);

	*/
	
	
	
	List<Classe> findByComplexity(
			Level key1);
	
	List<Classe> findByLastupdateGreaterThanEqual(
			Date key);
	
	List<Classe> findByLocGreaterThanEqual(
			Integer key);
	
	List<Classe> findByRecommended(
			Opponent key);
	
	List<Classe> findByComplexityAndRecommended(
			Level key1, Opponent key2);
	
	List<Classe> findByComplexityAndLastupdateGreaterThanEqual(
			Level key1,Date key2);
		
	List<Classe> findByComplexityAndLocGreaterThanEqual(
			Level key1, Integer key2);
	
	List<Classe> findByRecommendedAndLastupdateGreaterThanEqual(
			Opponent key1,Date key2);
	
	List<Classe> findByRecommendedAndLocGreaterThanEqual(
			Opponent key1,Integer key2);
	
	List<Classe> findByLastupdateGreaterThanEqualAndLocGreaterThanEqual(
			Date key1,Integer key2);
	
	
	List<Classe> findByComplexityAndRecommendedAndLastupdateGreaterThanEqual(Level key1, Opponent key3, Date key2);
	
	List<Classe> findByComplexityAndLastupdateGreaterThanEqualAndLocGreaterThanEqual(Level key1, Date key2, Integer key3);
	
	List<Classe> findByComplexityAndRecommendedAndLocGreaterThanEqual(Level key1, Opponent key2, Integer key3);
	
	List<Classe> findByRecommendedAndLastupdateGreaterThanEqualAndLocGreaterThanEqual(Opponent key1, Date key2,Integer key3);
	
	List<Classe> findByComplexityAndRecommendedAndLocGreaterThanEqualAndLastupdateGreaterThanEqual(
			Level key1,Opponent key2, Integer key3, Date key4);
	
	

	
}
