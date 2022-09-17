package com.jessegreenough.javabelt.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jessegreenough.javabelt.models.Course;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long>{
	
	public List<Course> findAll();

}
