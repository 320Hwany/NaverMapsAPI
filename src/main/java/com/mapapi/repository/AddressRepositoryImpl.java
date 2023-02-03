package com.mapapi.repository;

import com.mapapi.domain.Address;
import com.mapapi.domain.QAddress;
import com.mapapi.dto.Graph;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mapapi.domain.QAddress.address;

@RequiredArgsConstructor
@Repository
public class AddressRepositoryImpl implements AddressRepository {

    private final AddressJpaRepository addressJpaRepository;
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public void save(Address address) {
        addressJpaRepository.save(address);
    }

    @Override
    public List<Address> findAll() {
        return addressJpaRepository.findAll();
    }

    @Override
    public List<Address> findOrderByDistance() {
        return jpaQueryFactory.selectFrom(address)
                .limit(3)
                .orderBy(address.distance.asc())
                .fetch();
    }
}
