package com.cosmose.repository;

import com.cosmose.entity.Reservation;
import com.cosmose.entity.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by damian on 25.08.18.
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{

    Stream<Reservation> findByRoomIdAndStatus(Long roomId, ReservationStatus status);
}
