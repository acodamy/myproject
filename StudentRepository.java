package com.springbootrest.repositories;

import org.springframework.data.repository.CrudRepository;

import com.springbootrest.entities.Student;

//create a repository for student by extending from JpaRepository having Student class as reference
public interface StudentRepository extends CrudRepository<Student, Long>{

}

