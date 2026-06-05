package org.diner.dinerreserve.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.diner.dinerreserve.config.exception.CustomException;
import org.diner.dinerreserve.domain.ReservationSlot;
import org.diner.dinerreserve.domain.Restaurant;
import org.diner.dinerreserve.domain.RestaurantTable;
import org.diner.dinerreserve.dto.restaurant.RestaurantCreateRequest;
import org.diner.dinerreserve.dto.restaurant.RestaurantResponse;
import org.diner.dinerreserve.repository.ReservationSlotRepository;
import org.diner.dinerreserve.repository.RestaurantRepository;
import org.diner.dinerreserve.repository.RestaurantTableRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ReservationSlotRepository reservationSlotRepository;
    private final RestaurantTableRepository restaurantTableRepository;

    @Transactional
    public void register(RestaurantCreateRequest request) {
        if (restaurantRepository.existsByNameAndAddress(request.getName(), request.getAddress())) {
            throw new CustomException("이미 등록된 식당입니다.");
        }

        LocalDateTime now = LocalDateTime.now();

        Restaurant restaurant = new Restaurant();
        restaurant.setName(request.getName());
        restaurant.setAddress(request.getAddress());
        restaurant.setPhoneNumber(request.getPhoneNumber().replace("-", ""));
        restaurant.setDescription(request.getDescription());
        restaurant.setOpeningTime(request.getOpeningTime());
        restaurant.setClosingTime(request.getClosingTime());
        restaurant.setCategory(request.getCategory());
        restaurant.setActive(true);
        restaurant.setCreatedAt(now);
        restaurant.setUpdatedAt(now);

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        createSlotsForRestaurant(savedRestaurant);
        createDefaultTables(savedRestaurant);
    }

    public Page<RestaurantResponse> findAll(Pageable pageable) {
        return restaurantRepository.findAll(pageable).map(RestaurantResponse::new);
    }

    public Page<RestaurantResponse> findByNameContaining(String name,Pageable pageable) {
        return restaurantRepository.findByNameContaining(name, pageable).map(RestaurantResponse::new);
    }

    public Optional<RestaurantResponse> findById(Long id) {
        return restaurantRepository.findById(id).map(RestaurantResponse::new);
    }

    @Transactional
    public void updateRestaurant(Long id, RestaurantCreateRequest request) {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new CustomException("식당을 찾을 수 없습니다."));

        boolean timeChanged =
                !restaurant.getOpeningTime().equals(request.getOpeningTime()) ||
                        !restaurant.getClosingTime().equals(request.getClosingTime());

        restaurant.setName(request.getName());
        restaurant.setAddress(request.getAddress());
        restaurant.setPhoneNumber(request.getPhoneNumber().replace("-", ""));
        restaurant.setDescription(request.getDescription());
        restaurant.setOpeningTime(request.getOpeningTime());
        restaurant.setClosingTime(request.getClosingTime());
        restaurant.setCategory(request.getCategory());
        restaurant.setUpdatedAt(LocalDateTime.now());

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        if (timeChanged) {
            reservationSlotRepository.deleteByRestaurantId(id);
            createSlotsForRestaurant(restaurant);
        }

    }


    @Transactional
    public void deactivateRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new CustomException("식당을 찾을 수 없습니다."));
        restaurant.deactivate();
        restaurantRepository.save(restaurant);
        log.info("식당이 비활성화 되었습니다.");
    }

    @Transactional
    public Page<RestaurantResponse> searchByNameOrAddress(String keyword, Pageable pageable) {
        return restaurantRepository.findByNameContainingOrAddressContaining(keyword, keyword, pageable).map(RestaurantResponse::new);
    }


    private void createSlotsForRestaurant(Restaurant restaurant) {
        LocalTime currentTime = restaurant.getOpeningTime();
        LocalTime closingTime = restaurant.getClosingTime();

        while (currentTime.isBefore(closingTime)) {
            ReservationSlot slot = new ReservationSlot();
            slot.setRestaurantId(restaurant.getId());
            slot.setSlotTime(currentTime);
            slot.setActive(true);
            slot.setCreatedAt(LocalDateTime.now());
            slot.setUpdatedAt(LocalDateTime.now());

            reservationSlotRepository.save(slot);

            currentTime = currentTime.plusHours(1);
        }
    }

    private void createDefaultTables(Restaurant restaurant) {
        createTable(restaurant.getId(), "2인석-1", 2);
        createTable(restaurant.getId(), "2인석-2", 2);
        createTable(restaurant.getId(), "4인석-1", 4);
        createTable(restaurant.getId(), "4인석-2", 4);
        createTable(restaurant.getId(), "4인석-3", 4);
        createTable(restaurant.getId(), "6인석-1", 6);
    }

    private void createTable(Long restaurantId, String name, int capacity) {
        LocalDateTime now = LocalDateTime.now();

        RestaurantTable table = new RestaurantTable();
        table.setRestaurantId(restaurantId);
        table.setTableName(name);
        table.setCapacity(capacity);
        table.setActive(true);
        table.setCreatedAt(now);
        table.setUpdatedAt(now);

        restaurantTableRepository.save(table);
    }


    public Page<RestaurantResponse> findByActiveTrue(Pageable pageable) {
        Page<Restaurant> restaurants = restaurantRepository.findByActiveTrue(pageable);
        return restaurants.map(RestaurantResponse::new);
    }

    @Transactional
    public void delete(Long id) {
        restaurantRepository.deleteById(id);
    }


}
