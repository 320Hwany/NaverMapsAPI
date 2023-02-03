package com.mapapi.repository;

import com.mapapi.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressJpaRepository extends JpaRepository<Address, Long> {
}
