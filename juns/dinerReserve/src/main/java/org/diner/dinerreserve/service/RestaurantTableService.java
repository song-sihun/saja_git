package org.diner.dinerreserve.service;

import lombok.RequiredArgsConstructor;
import org.diner.dinerreserve.domain.RestaurantTable;
import org.diner.dinerreserve.repository.RestaurantTableRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantTableService {

    private final RestaurantTableRepository restaurantTableRepository;

    public Iterable<RestaurantTable> findAll() {
        return restaurantTableRepository.findAll();
    }

    public Optional<RestaurantTable> findById(Long id) {
        return restaurantTableRepository.findById(id);
    }

    public Iterable<RestaurantTable> findAllByRestaurantId(Long restaurantId) {
        return restaurantTableRepository.findAllByRestaurantId(restaurantId);
    }

    @Transactional
    public RestaurantTable save(RestaurantTable restaurantTable) {
        return restaurantTableRepository.save(restaurantTable);
    }

    @Transactional
    public RestaurantTable update(RestaurantTable restaurantTable) {
        return restaurantTableRepository.save(restaurantTable);
    }

    @Transactional
    public void deleteById(Long id) {
        restaurantTableRepository.deleteById(id);
    }



}
