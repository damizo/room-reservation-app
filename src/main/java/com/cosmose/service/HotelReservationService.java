package com.cosmose.service;

import com.cosmose.dto.ReservationDTO;
import com.cosmose.dto.ReservationPeriodDTO;
import com.cosmose.entity.Customer;
import com.cosmose.entity.Reservation;
import com.cosmose.entity.ReservationStatus;
import com.cosmose.entity.Room;
import com.cosmose.exception.DomainEntityNotFoundException;
import com.cosmose.exception.LogicValidationException;
import com.cosmose.mapper.ReservationMapper;
import com.cosmose.repository.CustomerRepository;
import com.cosmose.repository.ReservationRepository;
import com.cosmose.repository.RoomRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Created by damian on 25.08.18.
 */
@Service
@Transactional
public class HotelReservationService implements ReservationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HotelReservationService.class);

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private ReservationCalculatorService reservationCalculator;

    @Autowired
    private DateValidationService dateValidationService;

    @Override
    public ReservationDTO reserve(Long customerId, Long roomId, ReservationPeriodDTO periodDTO) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new DomainEntityNotFoundException(String.format("Customer with id %d does not exist", customerId)));
        LOGGER.info("Customer has been found: {}", customer);

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new DomainEntityNotFoundException(String.format("Room with id %d does not exist", roomId)));
        LOGGER.info("Room has been found: {}", room);

        validateBeforeReservation(periodDTO, roomId);

        Reservation reservation = reservationMapper.toDomain(periodDTO, room, customer);
        LOGGER.info("Built reservation: {}", reservation);

        Integer accommodationPrice = reservationCalculator.calculate(reservation.getPeriodFromDate(),
                reservation.getPeriodToDate(),
                room.getDailyPrice());
        LOGGER.info("Accommodation price calculated: {}", accommodationPrice);

        reservation = fillAdditionalFields(reservation, accommodationPrice, ReservationStatus.CONFIRMED);
        Reservation persistedReservation = reservationRepository.save(reservation);
        LOGGER.info("Persisted reservation: {}", persistedReservation);

        return reservationMapper.fromDomain(persistedReservation);
    }

    @Override
    public ReservationDTO cancel(Long customerId, Long reservationId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new DomainEntityNotFoundException(String.format("Customer with id %d does not exist", customerId)));
        LOGGER.info("Customer has been found: {}", customer);

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new DomainEntityNotFoundException(String.format("Reservation with id %d does not exist", reservationId)));
        LOGGER.info("Reservation has been found: {}", reservation);

        if (!customer.getReservations().contains(reservation)) {
            throw new LogicValidationException(String.format("Customer with id %d has not assigned to reservation with id %d", customerId, reservationId));
        }

        validateReservationBeforeCancellation(reservation.getId(), reservation.getStatus());

        reservation.setStatus(ReservationStatus.CANCELED);
        LOGGER.info("Reservation status has changed: {}", reservation);

        return reservationMapper.fromDomain(reservation);
    }

    @Override
    public ReservationDTO check(Long customerId, Long reservationId) {
        //TODO: findByIdAndCustomerId
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new DomainEntityNotFoundException("Customer with id %d does not exist", customerId));
        LOGGER.info("Customer has been found: {}", customer);

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new DomainEntityNotFoundException(String.format("Reservation with id %d does not exist",
                        reservationId)));
        LOGGER.info("Reservation has been found: {}", reservation);

        if (!customer.getReservations().contains(reservation)) {
            throw new LogicValidationException(String.format("Customer with id %d has not assigned to reservation with id %d",
                    customerId, reservationId));
        }

        return reservationMapper.fromDomain(reservation);
    }

    private Reservation fillAdditionalFields(Reservation reservation, Integer accommodationPrice,
                                             ReservationStatus reservationStatus) {
        reservation.setTotalPrice(accommodationPrice);
        reservation.setStatus(reservationStatus);
        return reservation;
    }

    private void validateReservationBeforeCancellation(Long reservationId, ReservationStatus status) {
        LOGGER.info("Before cancellation, reservation status: {}", status);
        if (status == ReservationStatus.CANCELED) {
            throw new LogicValidationException(String.format("Reservation with id %s is already canceled", reservationId));
        }
    }

    private void validateBeforeReservation(ReservationPeriodDTO periodDTO, Long roomId) {
        if (StringUtils.isBlank(periodDTO.getPeriodFromDate()) || StringUtils.isBlank(periodDTO.getPeriodToDate())) {
            throw new LogicValidationException("Reservation has to contain period fromDate and toDate");
        }

        LocalDateTime fromDate = LocalDateTime.parse(periodDTO.getPeriodFromDate());
        LocalDateTime toDate = LocalDateTime.parse(periodDTO.getPeriodToDate());
        LOGGER.info("Before reservation, period: {} and {}", fromDate, toDate);

        if (fromDate.isAfter(toDate)) {
            throw new LogicValidationException("fromDate must be older than toDate while reservation");
        }

        if (DAYS.between(fromDate, toDate) == 0) {
            throw new LogicValidationException("Room must be reserved on at least one day");
        }

        LocalDateTime now = LocalDateTime.now();

        if (fromDate.isBefore(now) || toDate.isBefore(now)) {
            throw new LogicValidationException("While reservation both dates cannot be set up in the past");
        }

        dateValidationService.checkDatesConflicts(periodDTO.getPeriodFromDate(), periodDTO.getPeriodToDate(), roomId);

        LOGGER.info("Dates went through validation");
    }


}
