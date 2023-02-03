package com.mapapi.repository;


import com.mapapi.domain.Address;
import com.mapapi.dto.Graph;

import java.util.List;

public interface AddressRepository {

    void save(Address address);

    List<Address> findAll();

    List<Address> findOrderByDistance();
}
