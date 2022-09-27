package com.spring.project.entity.personal;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Address implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String area;
    private String city;
    private String state;
    private String country;
    private Integer pincode;

}
