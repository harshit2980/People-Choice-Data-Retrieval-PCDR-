package com.spring.project.servicelayer;

import com.spring.project.entity.brand.Brand;
import com.spring.project.entity.personal.Person;
import com.spring.project.repo.BrandRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BrandServiceLayer {

    private static final Logger LOG = LoggerFactory.getLogger(BrandServiceLayer.class);

    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private Brand brand;

    @Cacheable(cacheNames ="brand",key="#id")
    public Brand getBrandById(int id) {
        LOG.info("fetching brand from db");
        Brand brand=null;
        try{
            Optional<Brand> optional = this.brandRepository.findById(id);
            if(optional.isPresent()){
                brand=optional.get();
            }

        } catch(Exception e){
            e.printStackTrace();
        }
        return brand;

    }

    public Brand addBrand(Brand brand){
        /*mongoTemplate.indexOps(Person.class).ensureIndex(new Index().on("Pii_id",Sort.Direction.ASC));
        mongoTemplate.indexOps(Person.class).ensureIndex(new Index().on("name",Sort.Direction.ASC));*/
        return this.brandRepository.save(brand);
    }

    @CachePut(cacheNames = "brand",key="#id")
    public void updateBrand(Brand brand, int id){
        LOG.info("brand update");
        brand.setBrandId(id);
        brandRepository.save(brand);
    }

    @CacheEvict(cacheNames = "brand",key="#id")
    public void deleteBrand(int id) {
        LOG.info("brand deleted");
        this.brandRepository.deleteById(id);
    }

    public Integer getPersonCount(int brand_id) {

        Brand brand;
        Optional<Brand> optional=brandRepository.findById(brand_id);
        if(optional.isPresent()){
        brand=optional.get();
        return brand.getFootFallCount();}
        else{
            return null;
        }
    }

}
