package com.spring.project.controller;

import com.spring.project.entity.brand.Brand;
import com.spring.project.service.BrandSequenceGeneratorService;
import com.spring.project.servicelayer.BrandServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class BrandController {

    @Autowired
    private BrandServiceLayer brandServiceLayer;

    @Autowired
    private BrandSequenceGeneratorService service;

    @Autowired
    Brand brand;



    // get single person
    @GetMapping("/brand/{id}")
    public ResponseEntity<Brand> getBrand(@PathVariable("id") int id){
        Brand brand= brandServiceLayer.getBrandById(id);
        if(brand == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(brand));
    }

    // insert user info
    @PostMapping("/brand")
    public ResponseEntity<Brand> addBrand(@RequestBody Brand brand){
        brand.setBrandId(service.getBrandSequenceNumber(Brand.SEQUENCE_NAME));
        Brand brandResponse;
        try{
            brandResponse= brandServiceLayer.addBrand(brand);
        System.out.println(brand);
        return ResponseEntity.status(HttpStatus.CREATED).body(brandResponse);
        }catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/brand/{id}")
    public ResponseEntity<Brand> updateBrand(@RequestBody Brand brand,
                                               @PathVariable("id") int id) {
        try {
            this.brandServiceLayer.updateBrand(brand, id);
            return ResponseEntity.ok().body(brand);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/brand/{id}")
    public ResponseEntity<?> deleteBrand(@PathVariable("id") int id){

        try {
            this.brandServiceLayer.deleteBrand(id);
            service.getBrandSequenceNumberDec(Brand.SEQUENCE_NAME);
            return ResponseEntity.status(HttpStatus.OK).body("deleted: "+id);
        }catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/personCount/{id}")
    public ResponseEntity<?> personCount(@PathVariable("id") int id){
        Integer count=brandServiceLayer.getPersonCount(id);
        if(count!=null)
        return ResponseEntity.status(HttpStatus.OK).body("person count :"+count);
        else
            return ResponseEntity.status(HttpStatus.OK).body("Brand is not visited by any person");

    }
}





