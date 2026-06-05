package org.diner.dinerreserve.service;

import lombok.RequiredArgsConstructor;
import org.diner.dinerreserve.domain.ReservationSlot;
import org.diner.dinerreserve.domain.Restaurant;
import org.diner.dinerreserve.repository.ReservationSlotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class ReservationSlotService {
    private final ReservationSlotRepository reservationSlotRepository;

    public Optional<ReservationSlot> findById(long id) {
        return reservationSlotRepository.findById(id);
    }

    public Iterable<ReservationSlot> findAll() {
        return reservationSlotRepository.findAll();
    }

    public List<ReservationSlot> findByRestaurantId(Long restaurantId) {
        return reservationSlotRepository.findByRestaurantId(restaurantId);
    }

    @Transactional
    public ReservationSlot save(ReservationSlot reservationSlot) {
        return reservationSlotRepository.save(reservationSlot);
    }

    @Transactional
    public ReservationSlot update(ReservationSlot reservationSlot) {
        return reservationSlotRepository.save(reservationSlot);
    }

    @Transactional
    public void delete(ReservationSlot reservationSlot) {
        reservationSlotRepository.delete(reservationSlot);
    }

    @Transactional
    public void deleteByRestaurantId(Long restaurantId) {
        reservationSlotRepository.deleteById(restaurantId);
    }

}


