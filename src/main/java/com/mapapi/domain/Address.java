package com.mapapi.domain;

import com.mapapi.dto.Graph;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
        int longitude = Integer.parseInt(graph.getLongitude()) - Integer.parseInt(inputGraph.getLongitude());
        int latitude = Integer.parseInt(graph.getLatitude()) - Integer.parseInt(inputGraph.getLatitude());
        this.distance = Math.sqrt(Math.exp(longitude) - Math.exp(latitude));
    }
}
