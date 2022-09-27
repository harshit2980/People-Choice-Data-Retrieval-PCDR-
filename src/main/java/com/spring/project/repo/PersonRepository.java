package com.spring.project.repo;

import com.spring.project.entity.personal.Person;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface PersonRepository extends MongoRepository<Person,Integer> {

    
}
