package com.spring.project.servicelayer;


import ch.hsr.geohash.GeoHash;
import com.spring.project.entity.brand.Brand;
import com.spring.project.entity.personal.Person;
import com.spring.project.geo.GeoLocation;
import com.spring.project.repo.BrandRepository;
import com.spring.project.repo.FootFallRepository;
import com.spring.project.entity.footfall.FootFall;
import com.spring.project.repo.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class FootFallServiceLayer {

    private static final Logger LOG = LoggerFactory.getLogger(FootFallServiceLayer.class);

    @Autowired
    private FootFallRepository footFallRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonServiceLayer personServiceLayer;

    @Autowired
    private BrandServiceLayer brandServiceLayer;

    @Autowired
    private FootFall footFall;
    @Autowired
    private Person person;
    @Autowired
    private Brand brand;

    @Autowired
    private GeoLocation geoLocation;

    @Autowired
    MongoTemplate mongoTemplate;


    @Cacheable(cacheNames = "footFallPerson", key = "#id")
    public List<String> getFootFallByPersonId(int id) {
        LOG.info("fetching Brand Name from FootFall db");
        List<String> brandName = new ArrayList<>();

        List<FootFall> list = footFallRepository.findByPersonsId(id);
        for (FootFall str : list) {
            Optional<Brand> optional = brandRepository.findById(str.getBrandId());
            if(optional.isPresent()){
            brand = optional.get();
            brandName.add(brand.getBrandName());}
        }

        return brandName;
    }



    /*ye cacheName kiya hota h,aur key kiya hoti h har function m cacheName
     aur key name same h toh koi problem toh nahi hogi*/
    @Cacheable(cacheNames = "footFallBrand", key = "#id")
    public List<String> getFootFallByBrandId(int id) {
        LOG.info("fetching Person Name from FootFall db");
        List<String> personName = new ArrayList<>();

        List<FootFall> list = footFallRepository.findByBrandId(id);
        for (FootFall str : list) {
            Optional<Person> optional = personRepository.findById(str.getPiiId());
            if(optional.isPresent()){
            person = optional.get();
            personName.add(person.getName());}
        }

        return personName;
    }

    public FootFall addFootFall(int pii_id, int brand_id) {
        Optional<Person> opt1 = personRepository.findById(pii_id);
        Optional<Brand> opt2 = brandRepository.findById(brand_id);
        List<FootFall> opt3 = footFallRepository.getFootFallByPiiIdAndBrandId(pii_id, brand_id);

        FootFall result = null;
        if (opt1.isPresent() && opt2.isPresent()) {
            person = opt1.get();
            brand = opt2.get();
            if (opt3.size() == 0) {
            if (person.getBrandCount() == null) {
                person.setBrandCount(1);
            } else {
                person.setBrandCount((person.getBrandCount()) + 1);
            }
            personServiceLayer.updatePerson(person, person.getPii_id());
        }
            if (brand.getFootFallCount() == null) {
            brand.setFootFallCount(1);
            } else {
            brand.setFootFallCount((brand.getFootFallCount()) + 1);
            }
            brandServiceLayer.updateBrand(brand, brand.getBrandId());

            footFall.setPiiId(person.getPii_id());
            footFall.setBrandId(brand.getBrandId());
            footFall.populateCreatedAt();
            //footFall.setPlace((person.getAddress()));
            footFall.setGeoLocation(brand.getGeoLocation());
            GeoHash geohash = GeoHash.withCharacterPrecision(brand.getGeoLocation().getLatitude(), brand.getGeoLocation().getLongitude(), 9);
            footFall.setGeoHash(geohash.toBase32());
            result = this.footFallRepository.save(footFall);
            return result;
        } else {
            return result;
        }
    }


    @CachePut(cacheNames = "footFall", key = "#id")
    public void updateFootFall(FootFall footFall, int id) {
        LOG.info("footFall update");
        footFall.setFootFallId(id);
        footFallRepository.save(footFall);
    }


    @CacheEvict(cacheNames = "footFall", key = "#id")
    public void deleteFootFall(int id) {
        LOG.info("footFall deleted");
        this.footFallRepository.deleteById(id);
    }











    /*public List<String> nearBy(int brandId){
        Optional<Brand> optional = brandRepository.findById(brandId);
        brand = optional.get();
        GeoHash geohash = GeoHash.withCharacterPrecision(brand.getGeoLocation().getLatitude(), brand.getGeoLocation().getLongitude(), 6);
        String geoHashString = geohash.toBase32();

    }*/


    /*public Integer getCategoryCount(int pii_id){
        List<Brand> brandList=brandRepository.findDistinctByCategory()
    }
*/
    /*public List<String> nearBy(int brandId, long distance) {
        Optional<Brand> optional = brandRepository.findById(brandId);
        brand = optional.get();
        if (distance <= 500) {
            GeoHash geohash = GeoHash.withCharacterPrecision(brand.getGeoLocation().getLatitude(), brand.getGeoLocation().getLongitude(), 6);
            String geoHashString = geohash.toBase32();
            List<FootFall> foot=footFallRepository.findByBrandIdNot(brandId);

            MatchOperation matchOperation = Aggregation
                    .match(new Criteria("geoHash").is(geoHashString));
            ProjectionOperation projectionOperation = Aggregation
                    .project("piiId");

            GroupOperation groupOperation = Aggregation
                    .group("piiId");

            Aggregation aggregation = Aggregation
                    .newAggregation(matchOperation, projectionOperation, groupOperation);
            AggregationResults<Object> aggregationResults
                    = mongoTemplate.aggregate(aggregation, "footfall", Object.class);

        }
        else if (distance >= 1000) {
            GeoHash geohash = GeoHash.withCharacterPrecision(brand.getGeoLocation().getLatitude(), brand.getGeoLocation().getLongitude(), 5);
            String geoHashString = geohash.toBase32();

            List<FootFall> foot=footFallRepository.findByBrandIdNot(brandId);

            MatchOperation matchOperation = Aggregation
                    .match(new Criteria("geoHash").is(geoHashString));
            ProjectionOperation projectionOperation = Aggregation
                    .project("piiId");

            GroupOperation groupOperation = Aggregation
                    .group("piiId");

            Aggregation aggregation = Aggregation
                    .newAggregation(matchOperation, projectionOperation, groupOperation);
            AggregationResults<FootFall> aggregationResults
                    = mongoTemplate.aggregate(aggregation, "footfall", FootFall.class);
        }


    }
}
*/


}
