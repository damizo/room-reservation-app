package com.cosmose.repository;

import com.cosmose.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by damian on 25.08.18.
 */
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {


    @Query(nativeQuery = true, value = "SELECT * FROM rooms ro " +
            "  INNER JOIN hotels ho ON ro.hotel_id = ho.id " +
            "  INNER JOIN addresses ad ON ad.id = ho.hotel_address_id " +
            " AND ro.daily_price <= :priceTo " +
            "      AND ro.daily_price >= :priceFrom " +
            "      AND ad.city LIKE %:city% ")
    List<Room> findRooms(@Param("priceFrom") Integer priceFrom,
                                            @Param("priceTo") Integer priceTo,
                                            @Param("city") String city);

}
