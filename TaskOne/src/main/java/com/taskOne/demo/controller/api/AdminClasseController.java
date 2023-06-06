package com.taskOne.demo.controller.api;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskOne.demo.model.Classe;
import com.taskOne.demo.model.DatabaseFile;
import com.taskOne.demo.model.Response;
import com.taskOne.demo.model.Classe.Opponent;
import com.taskOne.demo.model.Classe.Level;
import com.taskOne.demo.service.DatabaseFileService;
import com.taskOne.demo.service.iClasseService;

import java.text.SimpleDateFormat;
import java.text.ParseException;




@RestController
public class AdminClasseController {

	@Autowired
	@Qualifier("mainClasseService")
	private iClasseService classService;
	@Autowired
    private DatabaseFileService fileStorageService;


	
	public AdminClasseController() {
		
		
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
	
	
	//Upload classe/file  
	 ObjectMapper objectMapper= new ObjectMapper();
	    
	    @RequestMapping(value="admin/api/upload",method= RequestMethod.POST, consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	    public Response uploadComplete(@RequestParam(required=true,value="file")MultipartFile file, @RequestParam(required=true,value="jsondata")String jsondata) throws IOException{
	    	
	    	DatabaseFile fileName = fileStorageService.storeFile(file);

	    	String namefile= file.getOriginalFilename();
	    	String namefolder= namefile.substring(0, namefile.length() - 5);
	    	String idFile=fileName.getId();
	    	System.out.println("------------------ "+ idFile +"/*/**/*/*/*/*/*/*/*");
	    	
	        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	                .path("/uploadFile/")
	                .path(fileName.getFileName())
	                .toUriString();
	        
	        
	        Classe classej= objectMapper.readValue(jsondata,Classe.class);
	        classej.setUrl("./"+namefolder+"/"+namefile);
	        classej.setFileId(idFile);
	        classService.create(classej);
	    	
	        return new Response(fileName.getFileName(), fileDownloadUri,
	                file.getContentType(), file.getSize());
	    }
	    
	    
	 
	 
	 
	 
	 
	 
	 
	//Visualizzazione Classi: Admin&User
	@RequestMapping("admin/api/VisualizzaClassi")
	public Iterable<Classe> getAll(){
		
		return classService.getAll();
	}
	
	
	
	//Ricerca Avanzata: Admin&User
	@RequestMapping("admin/api/RicercaAvanzata")
	public List<Classe> getListClassesComplete(@RequestParam(value = "loc", defaultValue = "")Integer loc,
			@RequestParam(value = "complexity", required=false) String complexity,
			@RequestParam(value = "lastUpdate", defaultValue = "") String lastUpdate,
			@RequestParam(value = "recommended", required=false) String recommended){
		

		
		Boolean condRecommended=true;

		if((recommended == null) || (recommended == "")) {
			condRecommended=false;
		}
		
		
		Opponent esempio_opponent=null;
		if(condRecommended) {
			
			esempio_opponent=Opponent.valueOf(recommended.toUpperCase());
		}
		
		Boolean condComplexity=true;
		if((complexity == null) || (complexity == "")) {
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
	
	
	//Ricerca classe by id
	/*@RequestMapping("admin/api/classi/{id}")

	public Classe getById(@PathVariable int id) {
		
		//filtro la lista tramite l'id
		Optional<Classe> classej= classService.getById(id);
		
		//verifico che la foto sia stata trovata
		
		if(classej.isEmpty()) {
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"item not found");
		}
		
		//se invece la troviamo la restituiamo
		
		return classej.get();
		
	}*/
	
	
	
	//metodo creazione
	/*@RequestMapping(value="admin/api/classi", method= RequestMethod.POST)
	public Classe create(@RequestBody Classe classej) {
		
		return classService.create(classej);
		
	}
	*/
	
	
	
	//metodo modifica
	/*
	 * {
			"id": 6,
			"complexity": "INSANE",
			"loc": 80,
			"lastupdate": "2021-09-12",
			"recommended": "RANDOOP"
			}
			
	 L'id lo prevede per forza in ingresso e deve essere lo stesso messo nell'url,
	 oppure uno di valore superiore		
			
	 */
	@RequestMapping(value="admin/api/ModificaClasse/{id}", method= RequestMethod.PUT)
	public Classe update(@PathVariable int id, @RequestBody Classe classej) {
		
		Optional<Classe> updatephoto= classService.update(id, classej);
	
		if(updatephoto.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"item not found");
		}
		
		
		return updatephoto.get();
	}
	
	
	//metodo ELIMINAZIONE
	@RequestMapping(value="admin/api/EliminaClasse/{id}", method= RequestMethod.DELETE)
	public int delete(@PathVariable int id) {
		
		//verifico se l'ID è presente
		Boolean isDeleted= classService.delete(id);  
		
		
		
		//se è false
		if(isDeleted==false) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"item not found");
			
		}else{
			
			return 0; 		//se va a buon fine
		}
		
		//Se è presente elimino cioè true, non restituiamo nulla al client e di default 
		// gli arrivera status 200 ok
		
	}
	
	
	
	
	
	
}
