package com.spring.project.servicelayer;

import com.spring.project.entity.personal.Person;
import com.spring.project.repo.FootFallRepository;
import com.spring.project.repo.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PersonServiceLayer {

    private static final Logger LOG = LoggerFactory.getLogger(PersonServiceLayer.class);

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private Person person;



    @Cacheable(cacheNames ="person",key="#id")
    public Person getPersonById(int id) {
        LOG.info("fetching person from db");
        Person person=null;
        try{
            Optional<Person> optional = this.personRepository.findById(id);
            if(optional.isPresent()){
            person=optional.get();
            }

        } catch(Exception e){
            e.printStackTrace();
        }
        return person;

    }

    public Person addPerson(Person person){
        return this.personRepository.save(person);
    }

    @CachePut(cacheNames = "person",key="#id")
    public void updatePerson(Person person, int id){
        LOG.info("person update");
        person.setPii_id(id);
        personRepository.save(person);
    }

    @CacheEvict(cacheNames = "person",key="#id")
    public void deletePerson(int id) {
        LOG.info("person deleted");
        this.personRepository.deleteById(id);
    }

    public Integer getBrandCount(int pii_id) {

        Person person;
        Optional<Person> optional=personRepository.findById(pii_id);
        if(optional.isPresent()){
        person=optional.get();
        return person.getBrandCount();}
        else{
            return null;
        }
    }
}
