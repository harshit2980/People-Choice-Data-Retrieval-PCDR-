package com.spring.project.repo;

import com.spring.project.entity.brand.Brand;
import com.spring.project.entity.footfall.FootFall;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface BrandRepository extends MongoRepository<Brand,Integer> {

    List<Brand> findDistinctByCategory(String category);
}
