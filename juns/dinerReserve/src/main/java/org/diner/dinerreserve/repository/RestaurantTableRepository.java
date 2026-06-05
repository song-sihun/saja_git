package org.diner.dinerreserve.repository;

import org.diner.dinerreserve.domain.RestaurantTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantTableRepository extends CrudRepository<RestaurantTable, Long>, PagingAndSortingRepository<RestaurantTable, Long> {
    List<RestaurantTable> findAllByRestaurantId(Long restaurantId);
    Page<RestaurantTable> findAll(Pageable pageable);
    List<RestaurantTable> findByRestaurantIdAndActive(Long restaurantId, Boolean active);

    List<RestaurantTable> findByRestaurantIdAndCapacityGreaterThanEqualAndActive(
            Long restaurantId,
            Integer capacity,
            Boolean active
    );

}
