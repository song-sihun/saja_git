package org.diner.dinerreserve.repository;

import org.diner.dinerreserve.config.status.RestaurantCategory;
import org.diner.dinerreserve.domain.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Long>, PagingAndSortingRepository<Restaurant, Long> {
    Optional<Restaurant> findByNameAndAddress(String name, String address);
    Page<Restaurant> findByName(String name, Pageable pageable);
    Page<Restaurant> findByNameContaining(String name, Pageable pageable);
    Page<Restaurant> findByAddress(String address, Pageable pageable);
    Page<Restaurant> findByAddressContaining(String address, Pageable pageable);
    Page<Restaurant> findBy(String city, Pageable pageable);
    Page<Restaurant> findAll(Pageable pageable);
    Page<Restaurant> findByActiveTrue(Pageable pageable);
    boolean existsByNameAndAddress(String name, String address);
    boolean existsByNameAndAddressAndIdNot(String name, String address, long id);
    Page<Restaurant> findByNameContainingOrAddressContaining(
            String name,
            String address,
            Pageable pageable
    );

    Page<Restaurant> findByCategory(
            RestaurantCategory category,
            Pageable pageable
    );





}
