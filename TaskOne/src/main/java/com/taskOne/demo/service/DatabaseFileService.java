package com.taskOne.demo.service;


import java.io.IOException;
import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.taskOne.demo.exception.FileNotFoundException;
import com.taskOne.demo.exception.FileStorageException;
import com.taskOne.demo.model.Classe;
import com.taskOne.demo.model.DatabaseFile;
import com.taskOne.demo.model.Classe.Level;
import com.taskOne.demo.model.Classe.Opponent;
import com.taskOne.demo.repository.DatabaseFileRepository;



@Service
public class DatabaseFileService {

    @Autowired
    private DatabaseFileRepository dbFileRepository;

    public DatabaseFile storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DatabaseFile dbFile = new DatabaseFile(fileName, file.getContentType(), file.getBytes());

            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
    
    
	//--------------------------------------------------------------------
	public Boolean delete(String id) {
		
		//ricerchiamo la foto tramite id
		Optional<DatabaseFile> foundfile= dbFileRepository.findById(id);
		
		//se non è stata trovata restituiamo false
		if(foundfile.isEmpty()==true) {
			
			return false;
			}
		
		//in caso contratio cancelliamo la foto dal DB
		dbFileRepository.delete(foundfile.get());
		
		return true;
	}

	//-------------------------------------------------------------------
	
    public DatabaseFile getFile(String fileId) {
    	
        return dbFileRepository
        		.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found with id " + fileId));
    }
}