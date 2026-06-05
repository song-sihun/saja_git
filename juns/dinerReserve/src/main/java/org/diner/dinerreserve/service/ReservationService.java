package org.diner.dinerreserve.service;

import lombok.RequiredArgsConstructor;
import org.diner.dinerreserve.config.exception.CustomException;
import org.diner.dinerreserve.config.status.ReservationStatus;
import org.diner.dinerreserve.domain.*;
import org.diner.dinerreserve.dto.reservation.MyReservationResponse;
import org.diner.dinerreserve.dto.reservation.ReservationRequest;
import org.diner.dinerreserve.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ReservationSlotRepository reservationSlotRepository;
    private final RestaurantTableRepository restaurantTableRepository;
    private final RestaurantRepository restaurantRepository;

    public Optional<Reservation> findById(Long id) {
        return reservationRepository.findById(id);
    }

    public Iterable<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Page<MyReservationResponse> findMyReservations(Long userId, Pageable pageable) {

        List<MyReservationResponse> reservationList = reservationRepository.findMyReservationsWithRestaurant(
                userId,
                pageable.getPageSize(),
                pageable.getOffset()
        );

        long total = reservationRepository.countByUserId(userId);

        return new PageImpl<>(reservationList, pageable, total);

    }

    @Transactional
    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Transactional
    public void update(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    @Transactional
    public void reserve(Long userId, Long restaurantId, ReservationRequest reservationRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("User not found"));
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new CustomException("Restaurant not found"));

        ReservationSlot slot = reservationSlotRepository.findByIdWithLock(reservationRequest.getSlotId())
                .orElseThrow(() -> new CustomException("Slot not found"));

        if(!restaurant.isActive()) {
            throw new CustomException("Restaurant not active");
        }

        if(!slot.isActive()) {
            throw new CustomException("Slot not active");
        }

        if (!slot.getRestaurantId().equals(restaurantId)) {
            throw new CustomException("This slot does not belong to the restaurant");
        }


        boolean alreadyReserved = reservationRepository.existsByUserIdAndSlotIdAndReservationDateAndStatus(
                userId,
                slot.getId(),
                reservationRequest.getReservationDate(),
                ReservationStatus.RESERVED
        );

        if (alreadyReserved) {
            throw new CustomException("Already reserved this slot");
        }


        if (reservationRequest.getReservationDate().isBefore(LocalDate.now())) {
            throw new CustomException("Past date cannot be reserved");
        }

        Integer partySize = reservationRequest.getPartySize();

        List<Long> reservedTableIds = reservationRepository.findReservedTableIds(
                slot.getId(),
                reservationRequest.getReservationDate(),
                ReservationStatus.RESERVED
        );

        RestaurantTable selectedTable = restaurantTableRepository.findAllByRestaurantId(restaurantId)
                .stream()
                .filter(RestaurantTable::isActive)
                .filter(table -> table.getCapacity() >= partySize)
                .filter(table -> !reservedTableIds.contains(table.getId()))
                .min(Comparator.comparing(RestaurantTable::getCapacity))
                .orElseThrow(() -> new CustomException("Available table not found"));

        Reservation reservation = new Reservation();
        reservation.setRestaurantId(restaurantId);
        reservation.setTableId(selectedTable.getId());
        reservation.setSlotId(slot.getId());
        reservation.setUserId(userId);
        reservation.setReservationDate(reservationRequest.getReservationDate());
        reservation.setPartySize(reservationRequest.getPartySize());
        reservation.setRequestMessage(reservationRequest.getRequestMessage());
        reservation.setCreatedAt(LocalDateTime.now());
        reservation.setUpdatedAt(LocalDateTime.now());
        reservation.setStatus(ReservationStatus.RESERVED);

        reservationRepository.save(reservation);
    }


    @Transactional
    public void cancel(Long reservationId, Long userId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        if (!reservation.getUserId().equals(userId)) {
            throw new CustomException("본인의 예약만 취소할 수 있습니다.");
        }

        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new CustomException("이미 취소된 예약입니다.");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        reservation.setUpdatedAt(LocalDateTime.now());
        reservationRepository.save(reservation);
    }
}
