package org.diner.dinerreserve.repository;

import org.diner.dinerreserve.config.status.ReservationStatus;
import org.diner.dinerreserve.domain.Reservation;
import org.diner.dinerreserve.dto.reservation.MyReservationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, Long>, PagingAndSortingRepository<Reservation, Long> {
    List<Reservation> findByReservationDateBetween(LocalDateTime start, LocalDateTime end);

    @Query("""
        select r.table_id
        from reservations r
        where r.slot_id = :slotId
        and r.reservation_date = :reservationDate
        and r.status = :status
    """)
    List<Long> findReservedTableIds(
            @Param("slotId") Long slotId,
            @Param("reservationDate") LocalDate reservationDate,
            @Param("status") ReservationStatus status
    );

    boolean existsByUserIdAndSlotIdAndReservationDateAndStatus(
            Long userId,
            Long slotId,
            LocalDate reservationDate,
            ReservationStatus status
    );

    Page<Reservation> findByUserIdOrderByReservationDateDesc(Long userId, Pageable pageable);

    @Query(value = """
        select\s
                    r.id as reservation_id, \s
                    res.name as restaurant_name,\s
                    res.address as restaurant_address,
                    r.table_id,\s
                    r.slot_id,
                    s.slot_time,
                    r.reservation_date,\s
                    r.party_size,\s
                    r.status,\s
                    r.request_message
        from reservations as r\s
        join restaurants res on r.restaurant_id = res.id
        join reservation_slots s on r.slot_id = s.id
        where r.user_id = :userId
        order by r.reservation_date desc
        limit :limit offset :offset
                   \s
       \s"""
    )
    List<MyReservationResponse> findMyReservationsWithRestaurant(
            @Param("userId") Long userId,
            @Param("limit") int limit,
            @Param("offset") long offset
    );

    @Query("select count(*) from reservations where user_id = :userId")
    long countByUserId(@Param("userId") Long userId);

}
