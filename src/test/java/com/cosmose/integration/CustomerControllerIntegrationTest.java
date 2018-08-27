package com.cosmose.integration;

import com.cosmose.dto.*;
import com.cosmose.entity.ReservationStatus;
import com.cosmose.rest.CustomerController;
import com.cosmose.rest.ExceptionHandlerControllerAdvice;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static com.cosmose.integration.ExpectedModelPopulator.buildHotelIbisDetails;
import static com.cosmose.integration.ExpectedModelPopulator.buildHotelRezydentDetails;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by damian on 24.08.18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Sql(scripts = "classpath:data.sql")
public class CustomerControllerIntegrationTest extends TestIntegrationHelper {

    @Autowired
    private CustomerController customerResource;

    @Autowired
    private ExceptionHandlerControllerAdvice exceptionHandlerControllerAdvice;

    private static AccommodationPlaceDetailsDTO hotelRezydentDetails, hotelIbisDetails;

    private MockMvc mockMvc;

    @BeforeClass
    public static void build() {
        hotelRezydentDetails = buildHotelRezydentDetails();
        hotelIbisDetails = buildHotelIbisDetails();
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(customerResource)
                .setControllerAdvice(exceptionHandlerControllerAdvice)
                .build();
    }


    @Test
    public void shouldRegisterCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO(
                1L,
                "Jacob",
                "Nowak",
                "551334656",
                "jacob@gmailc.com");

        String json = extractJson(customerDTO);

        this.mockMvc.perform(post("/api/v1/customers")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReserveRoom() throws Exception {
        ReservationPeriodDTO roomReservationPeriodDTO = new ReservationPeriodDTO(
                resolveDate(15, 12, 2038, 11),
                resolveDate(19, 12, 2038, 11));

        ReservationDTO reservationDTO = new ReservationDTO(
                10L,
                resolveDate(15, 12, 2038, 11),
                resolveDate(19, 12, 2038, 11),
                400,
                ReservationStatus.CONFIRMED,
                hotelIbisDetails);

        String jsonRequest = extractJson(roomReservationPeriodDTO);
        String jsonResponse = extractJson(reservationDTO);

        this.mockMvc.perform(post("/api/v1/customers/{customerId}/reservations/rooms/{roomId}/reserve",
                "1", "1")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonResponse));
    }

    @Test
    public void shouldCancelRoomReservation() throws Exception {
        Long customerId = 1L;
        Long reservationId = 5L;

        ReservationDTO reservationDTO = new ReservationDTO(
                reservationId,
                resolveDate(4, 6, 2035, 11),
                resolveDate(16, 6, 2035, 11),
                2860,
                ReservationStatus.CANCELED,
                buildHotelIbisDetails()
        );

        String jsonResponse = extractJson(reservationDTO);

        this.mockMvc.perform(put("/api/v1/customers/{customerId}/reservations/{reservationId}/cancel",
                customerId, reservationId)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));
    }

    @Test
    public void shouldCheckRoomReservation() throws Exception {
        Long customerId = 1L;
        Long reservationId = 5L;

        ReservationDTO reservationDTO = new ReservationDTO(
                reservationId,
                resolveDate(4, 6, 2035, 11),
                resolveDate(16, 6, 2035, 11),
                2860,
                ReservationStatus.CONFIRMED,
                hotelIbisDetails);

        String jsonResponse = extractJson(reservationDTO);

        this.mockMvc.perform(get("/api/v1/customers/{customerId}/reservations/{reservationId}/check",
                customerId, reservationId)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));
    }

    @Test
    public void shouldNotReserveRoomWhenOneOfDatesIsPast() throws Exception {
        String periodFromDate = resolveDate(15, 12, 2015, 11);
        String periodToDate = resolveDate(19, 12, 2025, 11);

        ReservationPeriodDTO roomReservationPeriodDTO = new ReservationPeriodDTO(
                periodFromDate,
                periodToDate);

        ErrorDTO expectedErrorDTO = new ErrorDTO("While reservation both dates cannot be set up in the past");

        String jsonRequest = extractJson(roomReservationPeriodDTO);
        String jsonResponse = extractJson(expectedErrorDTO);

        this.mockMvc.perform(post("/api/v1/customers/{customerId}/reservations/rooms/{roomId}/reserve",
                "1", "1")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isConflict())
                .andExpect(content().json(jsonResponse));
    }

    @Test
    public void shouldNotReserveRoomWhenFromDateIsAfterToDate() throws Exception {
        String periodFromDate = resolveDate(19, 12, 2020, 11);
        String periodToDate = resolveDate(16, 12, 2020, 11);

        ReservationPeriodDTO roomReservationPeriodDTO = new ReservationPeriodDTO(
                periodFromDate,
                periodToDate);

        ErrorDTO expectedErrorDTO = new ErrorDTO("fromDate must be older than toDate while reservation");

        String jsonRequest = extractJson(roomReservationPeriodDTO);
        String jsonResponse = extractJson(expectedErrorDTO);

        this.mockMvc.perform(post("/api/v1/customers/{customerId}/reservations/rooms/{roomId}/reserve",
                "1", "1")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isConflict())
                .andExpect(content().json(jsonResponse));
    }

    @Test
    public void shouldNotReserveRoomWhenFromDateAndToDateAreInTheSameDay() throws Exception {
        String periodFromDate = resolveDate(19, 12, 2020, 11);
        String periodToDate = resolveDate(19, 12, 2020, 12);

        ReservationPeriodDTO roomReservationPeriodDTO = new ReservationPeriodDTO(
                periodFromDate,
                periodToDate);

        ErrorDTO expectedErrorDTO = new ErrorDTO("Room must be reserved on at least one day");

        String jsonRequest = extractJson(roomReservationPeriodDTO);
        String jsonResponse = extractJson(expectedErrorDTO);

        this.mockMvc.perform(post("/api/v1/customers/{customerId}/reservations/rooms/{roomId}/reserve",
                "1", "1")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isConflict())
                .andExpect(content().json(jsonResponse));
    }

}
