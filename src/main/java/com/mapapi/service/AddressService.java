package com.mapapi.service;

import com.mapapi.domain.Address;
import com.mapapi.dto.Graph;
import com.mapapi.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AddressService {

    private final AddressRepository addressRepository;

    @Transactional
    public void save(Graph graph) {
        Address address = Address.getFromGraph(graph);
        addressRepository.save(address);
    }

    @Transactional
    public void calculateDistance(Graph graph) {
        List<Address> addressList = addressRepository.findAll();
        addressList.stream().forEach(address -> address.calculateDistance(graph));
    }

    public List<Address> findOrderByDistance() {
        return addressRepository.findOrderByDistance();
    }
}
