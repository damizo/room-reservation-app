package com.cosmose.service;

import com.cosmose.dto.RoomDTO;
import com.cosmose.dto.SearchRoomDTO;
import com.cosmose.entity.Room;
import com.cosmose.mapper.RoomMapper;
import com.cosmose.repository.RoomRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by damian on 25.08.18.
 */
@Service
public class SearchRoomService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchRoomService.class);

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private DateValidationService dateValidationService;

    public List<RoomDTO> search(SearchRoomDTO searchRoomDTO) {
        LOGGER.info("Searching room with params: {}", searchRoomDTO);
        searchRoomDTO = fillDefaultValues(searchRoomDTO);
        LOGGER.info("After filled default values: {}", searchRoomDTO);

        List<Room> results = roomRepository.findRooms(searchRoomDTO.getDailyPriceFrom(),
                searchRoomDTO.getDailyPriceTo(),
                searchRoomDTO.getCity());
        LOGGER.info("After first filtering: {}", results);

        if (CollectionUtils.isEmpty(results)) {
            return Collections.EMPTY_LIST;
        }

        return excludeConfirmedReservationsBetweenDates(searchRoomDTO, results)
                .stream()
                .map(room -> roomMapper.fromDomain(room))
                .collect(Collectors.toList());
    }

    private List<Room> excludeConfirmedReservationsBetweenDates(SearchRoomDTO searchRoomDTO, List<Room> rooms) {
        return rooms.stream()
                .filter(room -> dateValidationService.validateReservations(searchRoomDTO.getPeriodFromDate(),
                        searchRoomDTO.getPeriodToDate(), room.getReservations()))
                .distinct()
                .collect(Collectors.toList());
    }

    private SearchRoomDTO fillDefaultValues(SearchRoomDTO searchRoomDTO) {
        if (searchRoomDTO.getDailyPriceFrom() == null) {
            searchRoomDTO.setDailyPriceFrom(0);
        }

        if (searchRoomDTO.getDailyPriceTo() == null) {
            searchRoomDTO.setDailyPriceTo(Integer.MAX_VALUE);
        }

        LocalDateTime now = LocalDateTime.now();

        if (searchRoomDTO.getPeriodFromDate() == null) {
            searchRoomDTO.setPeriodFromDate(now.toString());
        }

        if (searchRoomDTO.getPeriodToDate() == null) {
            searchRoomDTO.setPeriodToDate(now.plusDays(1).toString());
        }

        if (StringUtils.isBlank(searchRoomDTO.getCity())) {
            searchRoomDTO.setCity("");
        }

        return searchRoomDTO;
    }
}
