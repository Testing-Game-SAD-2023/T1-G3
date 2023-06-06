package com.taskOne.demo.controller.api;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.taskOne.demo.model.Classe;
import com.taskOne.demo.model.DatabaseFile;
import com.taskOne.demo.model.Classe.Level;
import com.taskOne.demo.model.Classe.Opponent;
import com.taskOne.demo.service.DatabaseFileService;
import com.taskOne.demo.service.iClasseService;

import jakarta.servlet.http.HttpServletRequest;


@RestController
public class UserClasseController {

	
	

	//Nel ClassController, rimuoviamo la lista istanziata dal costruttore,
	// perché verrà istanziata dal Service,
	//però va istanziato un ogetto Service, usato in tutti i metodi del controller
	
	@Autowired
	@Qualifier("mainClasseService")
	private iClasseService classService;
    @Autowired
    private DatabaseFileService fileStorageService;
	
	
	public UserClasseController() {


		
	}

	
	//Utility String to Date
		 public Date convertStringToDate(String dateString) {
		        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		        try {
		            java.util.Date utilDate = format.parse(dateString);
		            return new Date(utilDate.getTime());
		        } catch (ParseException e) {
		            // Gestione dell'eccezione in caso di formato di data non valido
		            e.printStackTrace();
		            return null;
		        }
		    }
		
		 
		 @GetMapping("/user/downloadFile/{fileName:.+}")
		    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
		        // Load file as Resource
		        DatabaseFile databaseFile = fileStorageService.getFile(fileName);


		        return ResponseEntity.ok()
		                .contentType(MediaType.parseMediaType(databaseFile.getFileType()))
		                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + databaseFile.getFileName() + "\"")
		                .body(new ByteArrayResource(databaseFile.getData()));
		    }
		 
		 
		//Visualizzazione Classi: Admin&User
		@RequestMapping("user/api/VisualizzaClassi")
		public Iterable<Classe> getAll(){
			
			return classService.getAll();
		}
		
		//Ricerca Avanzata: Admin&User
		@RequestMapping("user/api/RicercaAvanzata")
		public List<Classe> getListClassesComplete(@RequestParam(value = "loc", defaultValue = "")Integer loc,
				@RequestParam(value = "complexity", required=false) String complexity,
				@RequestParam(value = "lastUpdate", defaultValue = "") String lastUpdate,
				@RequestParam(value = "recommended", required=false) String recommended){
			
			Boolean condRecommended=true;
			if((recommended == null)) {
				condRecommended=false;
			}
			if((recommended == "")) {
				condRecommended=false;
			}
			
			
			Opponent esempio_opponent=null;
			if(condRecommended) {
				
				esempio_opponent=Opponent.valueOf(recommended.toUpperCase());
			}
			
			Boolean condComplexity=true;
			if((complexity == null)) {
				condComplexity=false;
			}
			if((complexity == "")) {
				condComplexity=false;
			}
			
			
			Level esempio_level=null;
			if(condComplexity) {
				
				esempio_level=Level.valueOf(complexity.toUpperCase());
			}
			
			
			Date date=null;
			if(lastUpdate != "")
			{
				date= convertStringToDate(lastUpdate);
			}

			if(esempio_level!= null) {
				if(esempio_opponent != null) {
						if(date!= null) {
								if(loc!=null) {
									return classService.ricercaComplete(esempio_level, esempio_opponent, loc, date);
								}else {
									return classService.ricercaComplexityLastUpdateRecommended(esempio_level, date, esempio_opponent);
								}
								
						}else {
							if(loc!=null) {
								return classService.ricercaComplexityRecommendedLoc(esempio_level, esempio_opponent, loc);
							}else {
								return classService.ricercaComplexityRecommended(esempio_level, esempio_opponent);
							}
						}
					
				}else {
					if(date!= null) {
						if(loc!=null) {	
							return classService.ricercaComplexityLastUpdateLoc(esempio_level, date, loc);
						}else {
							return classService.ricercaComplexityLastUpdate(esempio_level, date);
						}
					}else {
						if(loc!=null) {
							return classService.ricercaComplexityLoc(esempio_level, loc);
						}else {
							return classService.ricercaComplexity(esempio_level);
						}
					}	
				}
			}else { //se complexity == null
				
				if(esempio_opponent != null) {
					if(date!= null) {
							if(loc!=null) {
								return classService.ricercaRecommendedLastUpdateLoc(esempio_opponent, date, loc);
							}else {
								return classService.ricercaRecommendedLastUpdate(esempio_opponent, date);
							}
					}else {
						if(loc!=null) {
							return classService.ricercaRecommendedLoc(esempio_opponent, loc);
						}else {
							return classService.ricercaRecommended(esempio_opponent);
						}
					}
				
				}else {
					if(date!= null) {
							if(loc!=null) {
									return classService.ricercaLastUpdateLoc(date, loc);
							}else {
									return classService.ricercaLastUpdate(date);
							}
					
					}else {
							if(loc!=null) {
									return classService.ricercaLoc(loc);
							}else {
									System.out.println("Tutti i campi sono null");
									return (List<Classe>) classService.getAll();
							}
					}	
				}		
			}
			
				
			
		}
		
	
	
	
	//Get by id non richiesto
	/*@RequestMapping("/api/classi/{id}")
	public Classe getById(@PathVariable int id) {
		
		//filtro la lista tramite l'id
		Optional<Classe> classej= classService.getById(id);
		
		//verifico che la foto sia stata trovata
		
		if(classej.isEmpty()) {
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"item not found");
		}
		
		//se invece la troviamo la restituiamo
		
		return classej.get();
		
	}
	*/
}
