package com.spring.project.dbseq;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "person_db_sequence")
public class PersonDbSequence {

    @Id
    private String id;
    private int seq;
}
