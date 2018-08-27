package com.cosmose.rest;

import com.cosmose.dto.RoomDTO;
import com.cosmose.dto.SearchRoomDTO;
import com.cosmose.service.SearchRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by damian on 24.08.18.
 */
@RestController
@RequestMapping(value = "/api/v1/search/rooms")
public class SearchRoomController {

    @Autowired
    private SearchRoomService roomService;

    @PostMapping
    public ResponseEntity<List<RoomDTO>> search(@RequestBody SearchRoomDTO searchRoomDTO) {
        List<RoomDTO> rooms = roomService.search(searchRoomDTO);
        return ResponseEntity.ok(rooms);
    }
}
