package com.jessegreenough.javabelt.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jessegreenough.javabelt.models.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long>{

	
		public List<Student> findAll();
		
}
