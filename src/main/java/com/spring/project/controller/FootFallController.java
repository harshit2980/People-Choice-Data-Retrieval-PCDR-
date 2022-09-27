package com.spring.project.controller;

import com.spring.project.entity.brand.Brand;
import com.spring.project.entity.footfall.FootFall;
import com.spring.project.entity.personal.Person;
import com.spring.project.repo.FootFallRepository;
import com.spring.project.service.FootSequenceGeneratorService;
import com.spring.project.servicelayer.FootFallServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class FootFallController {

    @Autowired
    private FootFallServiceLayer footFallServiceLayer;

    @Autowired
    private FootSequenceGeneratorService service;

    @Autowired
    private FootFall footFall;
    @Autowired
    private FootFallRepository footFallRepository;

    // getting Brand Name through Person Id
    @GetMapping("/footFallPersonId/{id}")
    public ResponseEntity<List<String>> getFootFallBrand(@PathVariable("id") int id){
        List<String> brandNameResponse=footFallServiceLayer.getFootFallByPersonId(id);
        if(brandNameResponse.size()<=0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

                return ResponseEntity.of(Optional.of(brandNameResponse));
    }


    //getting Person Name through Brand Id
    @GetMapping("/footFallBrandId/{id}")
    public ResponseEntity<List<String>> getFootFallPerson(@PathVariable("id") int id){
        List<String> personNameResponse=footFallServiceLayer.getFootFallByBrandId(id);
        if(personNameResponse.size()<=0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else {
            return ResponseEntity.of(Optional.of(personNameResponse));
        }
    }


    // insert Footfall info
    @PostMapping("/footFall/{pii_id}/{brand_id}")
    public ResponseEntity<FootFall> addFootFall(@PathVariable int pii_id, @PathVariable int brand_id){

        FootFall footFallResponse;
        footFallResponse= footFallServiceLayer.addFootFall(pii_id,brand_id);
        if(footFallResponse!=null){
            footFall.setFootFallId(service.getFootSequenceNumber(FootFall.SEQUENCE_NAME));
            System.out.println(footFall);
            return ResponseEntity.status(HttpStatus.CREATED).body(footFallResponse);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }


    // Delete FootFall info by FootFall Id
    @DeleteMapping("/footFall/{id}")
    public ResponseEntity<?> deleteFootFall(@PathVariable("id") int id){
        Optional<FootFall> optional=footFallRepository.findById(id);
        if(optional.isPresent()){
        try {
            this.footFallServiceLayer.deleteFootFall(id);
            service.getFootSequenceNumberDec(FootFall.SEQUENCE_NAME);
            return ResponseEntity.status(HttpStatus.OK).body("deleted: "+id);
        }catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }}
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" no foot fall present ");
        }

    }



}





