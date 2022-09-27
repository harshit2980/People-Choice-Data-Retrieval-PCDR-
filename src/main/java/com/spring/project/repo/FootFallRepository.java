package com.spring.project.repo;

import com.spring.project.entity.brand.Brand;
import com.spring.project.entity.footfall.FootFall;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FootFallRepository extends MongoRepository<FootFall,Integer> {

    //SQL Equivalent : select brandId from footfall where piiId=?
    @Query(value="{ 'piiId' : ?0 }", fields="{ 'brandId' : 1}")
    List<FootFall> findByPersonsId(int piiId);


    //SQL Equivalent : select piiId from footfall where brandId=?
    @Query(value="{'brandId':?0}", fields = "{'piiId':1}")
    List<FootFall> findByBrandId(int brandId);

    //SQL Equivalent : SELECT * FROM footfall where piiId = ? and brandId=?
    //@Query("{author: ?0, cost: ?1}")
    @Query("{$and :[{piiId: ?0},{brandId: ?1}] }")
    List<FootFall> getFootFallByPiiIdAndBrandId(int piiId, int brandId);

    List<FootFall> findByBrandIdNot(int brandId);




    //SQL Equivalent : select count(*) from footfall where piiId=?
   /* @Query(value ="{piiId: ?0}", count=true)
    Integer getFootFallCountByPersonId(int piiId);*/

    //SQL Equivalent : select count(*) from footfall where BrandId=?
    /*@Query(value ="{brandId: ?0}", count=true)
    Integer getFootFallCountByBrandId(int brandId);*/
}
