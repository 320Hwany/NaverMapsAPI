package com.mapapi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor
@Embeddable
public class Graph {

    private String longitude;
    private String latitude;

    @Builder
    public Graph(String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public static Graph getFromXAndY(String longitude, String latitude) {
        return Graph.builder()
                .longitude(longitude)
                .latitude(latitude)
                .build();
    }
}
