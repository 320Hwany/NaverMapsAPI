package com.mapapi.domain;

import com.mapapi.dto.Graph;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static java.lang.Math.*;
import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@Getter
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Embedded
    private Graph graph;

    private double distance;

    @Builder
    public Address(Graph graph) {
        this.graph = graph;
    }

    public static Address getFromGraph(Graph graph) {
        return Address.builder()
                .graph(graph)
                .build();
    }

    public void calculateDistance(Graph inputGraph) {

        double startLat = Double.parseDouble(graph.getLatitude());
        double startLong = Double.parseDouble(graph.getLongitude());
        double endLat = Double.parseDouble(inputGraph.getLongitude());
        double endLong = Double.parseDouble(inputGraph.getLongitude());

        double dLat  = Math.toRadians((endLat - startLat));
        double dLong = Math.toRadians((endLong - startLong));

        startLat = Math.toRadians(startLat);
        endLat   = Math.toRadians(endLat);

        double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        this.distance = 6371 * c;
    }

    public static double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }

}
