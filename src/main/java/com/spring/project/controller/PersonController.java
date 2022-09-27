package com.spring.project.controller;

import com.spring.project.entity.personal.Person;
import com.spring.project.service.PersonSequenceGeneratorService;
import com.spring.project.servicelayer.PersonServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class PersonController {

    @Autowired
    private PersonServiceLayer personServiceLayer;

    @Autowired
    private PersonSequenceGeneratorService service;

    @Autowired
    Person person;



    // get single person
    @GetMapping("/person/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable("id") int id){
        Person person= personServiceLayer.getPersonById(id);
        if(person == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(person));
    }

    // insert user info
    @PostMapping("/person")
    public ResponseEntity<Person> addPerson(@RequestBody Person person){
        person.setPii_id(service.getPersonSequenceNumber(Person.SEQUENCE_NAME));
        Person personResponse;
        try{
        personResponse= personServiceLayer.addPerson(person);
        System.out.println(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(personResponse);
        }catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/person/{id}")
    public ResponseEntity<Person> updatePerson(@RequestBody Person person,
                                               @PathVariable("id") int id) {
        try {
            this.personServiceLayer.updatePerson(person, id);
            return ResponseEntity.ok().body(person);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/person/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable("id") int id){
        service.getPersonSequenceNumberDec(Person.SEQUENCE_NAME);
        try {
            this.personServiceLayer.deletePerson(id);
            return ResponseEntity.status(HttpStatus.OK).body("deleted: "+id);
        }catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/brandCount/{id}")
    public ResponseEntity<?> brandCount(@PathVariable("id") int id){
        Integer count=personServiceLayer.getBrandCount(id);
        if(count!=null)
        return ResponseEntity.status(HttpStatus.OK).body("brand count :"+count);
        else{
            return ResponseEntity.status(HttpStatus.OK).body("person not visited any brand yet");
        }
    }
}





