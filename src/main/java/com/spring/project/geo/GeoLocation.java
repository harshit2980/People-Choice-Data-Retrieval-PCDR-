package com.spring.project.geo;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class GeoLocation implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L;

    Double longitude;
    Double latitude;


}
