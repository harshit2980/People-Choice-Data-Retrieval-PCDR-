package com.spring.project.service;

import com.spring.project.dbseq.PersonDbSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

@Service
public class PersonSequenceGeneratorService {

    @Autowired
    private MongoOperations mongoOperations;

    public int getPersonSequenceNumber(String seqName){

        // get seq no.
        Query query= new Query(Criteria.where("id").is(seqName));

        // update the seq no.
        Update update = new Update().inc("seq",1);

        // modify the document
        PersonDbSequence counter= mongoOperations.findAndModify(query,update,options().returnNew(true).upsert(true),
                PersonDbSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }

    public void getPersonSequenceNumberDec(String seqName){

        // get seq no.
        Query query= new Query(Criteria.where("id").is(seqName));

        // update the seq no.
        Update update = new Update().inc("seq",-1);

        // modify the document
        PersonDbSequence counter= mongoOperations.findAndModify(query,update,options().returnNew(true).upsert(true),
                PersonDbSequence.class);
//        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }

}
