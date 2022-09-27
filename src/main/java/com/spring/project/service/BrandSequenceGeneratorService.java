package com.spring.project.service;

import com.spring.project.dbseq.BrandDbSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

@Service
public class BrandSequenceGeneratorService {

    @Autowired
    private MongoOperations mongoOperations;

    public int getBrandSequenceNumber(String seqName){

        // get seq no.
        Query query= new Query(Criteria.where("id").is(seqName));

        // update the seq no.
        Update update = new Update().inc("seq",1);

        // modify the document
        BrandDbSequence counter= mongoOperations.findAndModify(query,update,options().returnNew(true).upsert(true),
                BrandDbSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }

    public void getBrandSequenceNumberDec(String seqName){

        // get seq no.
        Query query= new Query(Criteria.where("id").is(seqName));

        // update the seq no.
        Update update = new Update().inc("seq",-1);

        // modify the document
        BrandDbSequence counter= mongoOperations.findAndModify(query,update,options().returnNew(true).upsert(true),
                BrandDbSequence.class);
        // return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }

}
