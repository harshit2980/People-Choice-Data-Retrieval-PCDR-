package com.spring.project.entity.brand;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.project.geo.GeoLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "brand")
@Component
public class Brand implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Transient
    public static final String SEQUENCE_NAME = "user_sequence";


    @Indexed
    @Id
    private int brandId;
    @Indexed
    private String brandName;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2D)
    private GeoLocation geoLocation;
    private String category;
    private Integer footFallCount;

}