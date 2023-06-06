package com.taskOne.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.taskOne.demo.model.DatabaseFile;

@Repository
public interface DatabaseFileRepository extends JpaRepository<DatabaseFile, String> {

}