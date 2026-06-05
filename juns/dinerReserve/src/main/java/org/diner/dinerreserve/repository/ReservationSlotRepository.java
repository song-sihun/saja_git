package org.diner.dinerreserve.repository;

import org.diner.dinerreserve.domain.ReservationSlot;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReservationSlotRepository extends CrudRepository<ReservationSlot, Long>, PagingAndSortingRepository<ReservationSlot, Long> {

    List<ReservationSlot> findByRestaurantId(Long restaurantId);

    @Query("SELECT * FROM reservation_slots WHERE id = :id FOR UPDATE")
    Optional<ReservationSlot> findByIdWithLock(@Param("id") Long id);

    void deleteByRestaurantId(Long id);
}
