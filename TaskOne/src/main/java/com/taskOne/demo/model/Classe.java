package com.taskOne.demo.model;



import java.sql.Date;

import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.OnDelete;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;


@Entity
public class Classe {

	
	public enum  Level{
		SIMPLE("simple"),
		MODERATE("moderate"),
		COMPLEX("complex"),
		INSANE("insane");
		
		
		private final String value;
		Level(String value){
			this.value=value;
		}
		
	}
	
	public enum  Opponent{
		RANDOOP("Randoop"),
		EVOSUITE("EvoSuite");
		
		private final String value;
		Opponent(String value){
			this.value=value;
		}
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String url;
	

	@Enumerated(EnumType.STRING)
	private Level complexity;

	private int loc;
	//Date last_update;	//da sistemare
	Date lastupdate;
	
	
	@Enumerated(EnumType.STRING)
	private Opponent recommended; 
	


    private String fileId;



	
	public Classe() {}
	
	/*public Classe(int id, Level complexity, int loc, Date last_update,Opponent recommended) {
		super();
		this.id = id;
		this.complexity = complexity;
		this.loc = loc;
		this.last_update = last_update;
		this.recommended=recommended;
	}*/
	
	
	public Classe(int id, Level complexity, int loc, Date lastupdate,Opponent recommended) {
		super();
		this.id = id;
		this.complexity = complexity;
		this.loc = loc;
		this.lastupdate = lastupdate;
		this.recommended=recommended;
	}
	
	public Opponent getRecommended() {
		return recommended;
	}

	public void setRecommended(Opponent recommended) {
		this.recommended = recommended;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public Level getComplexity() {
		return complexity;
	}
	public void setComplexity(Level complexity) {
		this.complexity = complexity;
	}
	public int getLoc() {
		return loc;
	}
	public void setLoc(int loc) {
		this.loc = loc;
	}
	/*public Date getLast_update() {
		return last_update;
	}
	public void setLast_update(Date last_update) {
		this.last_update = last_update;
	}*/
	
	public Date getLastupdate() {
		return lastupdate;
	}
	public void setLastupdate(Date last_update) {
		this.lastupdate = last_update;
	}
	
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileId() {
		return fileId;
	}

	
	
	
	
}
