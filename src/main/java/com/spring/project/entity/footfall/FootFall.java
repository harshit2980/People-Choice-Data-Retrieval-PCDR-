package com.spring.project.entity.footfall;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.project.geo.GeoLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "footfall")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class FootFall implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Transient
    public static final String SEQUENCE_NAME = "user_sequence";

    @Indexed
    @Id
    private int footFallId;
    @Indexed
    private  int brandId;
    @Indexed
    private int piiId;
    private Date createdAt;
//    private Place place;
    private GeoLocation geoLocation;
    private String geoHash;

    public void populateCreatedAt(){
        this.createdAt=Date.from(Instant.now());
    }


}
