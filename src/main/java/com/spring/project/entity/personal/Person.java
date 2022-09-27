package com.spring.project.entity.personal;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection="PII")
@Component
public class Person implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Transient
    public static final String SEQUENCE_NAME = "user_sequence";


    @Id
    @Indexed
    private int pii_id;
    @Indexed
    private String name;
    private String occupation;
    private Address address;
    private String gender;
    private Integer age;
    private Integer mobileNo;
    private Integer brandCount;


}
