package com.cosmose.integration;

import com.cosmose.dto.AccommodationPlaceDetailsDTO;
import com.cosmose.dto.RoomDTO;
import com.cosmose.dto.SearchRoomDTO;
import com.cosmose.rest.ExceptionHandlerControllerAdvice;
import com.cosmose.rest.SearchRoomController;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.cosmose.integration.ExpectedModelPopulator.buildHotelIbisDetails;
import static com.cosmose.integration.ExpectedModelPopulator.buildHotelRezydentDetails;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by damian on 26.08.18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Sql(scripts = "classpath:data.sql")
public class SearchRoomControllerIntegrationTest extends TestIntegrationHelper {

    @Autowired
    private SearchRoomController searchController;

    @Autowired
    private ExceptionHandlerControllerAdvice exceptionHandlerControllerAdvice;

    private MockMvc mockMvc;

    private static AccommodationPlaceDetailsDTO hotelRezydentDetails, hotelIbisDetails;

    @BeforeClass
    public static void build() {
        hotelRezydentDetails = buildHotelRezydentDetails();
        hotelIbisDetails = buildHotelIbisDetails();
    }

    @Before
    public void init() throws Exception {
        mockMvc = MockMvcBuilders
                .standaloneSetup(searchController)
                .setControllerAdvice(exceptionHandlerControllerAdvice)
                .build();
    }

    @Test
    public void shouldFindFourRoomsBetweenParticularPrice() throws Exception {
        SearchRoomDTO criteriaDTO = SearchRoomDTO.builder()
                .periodFromDate(resolveDate(1, 6, 2035, 11))
                .periodToDate(resolveDate(2, 6, 2035, 11))
                .dailyPriceFrom(10)
                .dailyPriceTo(260)
                .city("war")
                .build();

        List<RoomDTO> expectedRooms =
                Arrays.asList(new RoomDTO(1L,
                                100,
                                155,
                                hotelIbisDetails),
                        new RoomDTO(2L,
                                130,
                                156,
                                hotelIbisDetails),
                        new RoomDTO(3L,
                                260,
                                170,
                                hotelIbisDetails)
                );

        String jsonRequest = extractJson(criteriaDTO);
        String jsonResponse = extractJson(expectedRooms);

        mockMvc.perform(post("/api/v1/search/rooms")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse))
                .andDo(print());

    }

    @Test
    public void shouldFindCheapRoomWithCanceledReservation() throws Exception {
        SearchRoomDTO criteriaDTO = SearchRoomDTO.builder()
                .periodFromDate(resolveDate(18, 6, 2035, 11))
                .periodToDate(resolveDate(22, 6, 2035, 11))
                .dailyPriceTo(90)
                .city("Sop")
                .build();

        List<RoomDTO> expectedRooms = Arrays.asList(new RoomDTO(4L,
                80,
                10,
                hotelRezydentDetails)
        );


        String jsonRequest = extractJson(criteriaDTO);
        String jsonResponse = extractJson(expectedRooms);

        mockMvc.perform(post("/api/v1/search/rooms")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse))
                .andDo(print());

    }

    @Test
    public void shouldFindExpensiveRoomWithBetweenPeriod() throws Exception {
        SearchRoomDTO criteriaDTO = SearchRoomDTO.builder()
                .periodFromDate(resolveDate(26, 7, 2035, 11))
                .periodToDate(resolveDate(29, 7, 2035, 11))
                .dailyPriceFrom(200)
                .dailyPriceTo(600)
                .city("war")
                .build();

        List<RoomDTO> expectedRooms = Arrays.asList(new RoomDTO(3L,
                260,
                170,
                hotelIbisDetails)
        );

        String jsonRequest = extractJson(criteriaDTO);
        String jsonResponse = extractJson(expectedRooms);

        mockMvc.perform(post("/api/v1/search/rooms")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse))
                .andDo(print());

    }

    @Test
    public void shouldNotFindRoomWhenIsAlreadyReservedInPeriod() throws Exception {
        SearchRoomDTO criteriaDTO = SearchRoomDTO.builder()
                .periodFromDate(resolveDate(8, 6, 2035, 11))
                .periodToDate(resolveDate(9, 6, 2035, 11))
                .dailyPriceFrom(200)
                .dailyPriceTo(600)
                .city("Warsaw")
                .build();

        List<RoomDTO> expectedRooms = Collections.emptyList();

        String jsonRequest = extractJson(criteriaDTO);
        String jsonResponse = extractJson(expectedRooms);

        mockMvc.perform(post("/api/v1/search/rooms")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse))
                .andDo(print());
    }

    private Page<RoomDTO> createExpectedRooms(List<RoomDTO> room) {
        return new PageImpl<>(room);
    }
}
