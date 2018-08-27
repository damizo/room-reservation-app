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
            "    INNER JOIN hotels ho ON ro.hotel_id = ho.id " +
            "    INNER JOIN addresses ad ON ad.id = ho.hotel_address_id " +
            "    WHERE ro.id NOT IN (" +
            "      SELECT ros.id " +
            "      FROM rooms ros " +
            "        LEFT JOIN reservations re ON ros.id = re.room_id " +
            "        LEFT JOIN hotels ho ON ros.hotel_id = ho.id " +
            "        LEFT JOIN addresses ad ON ad.id = ho.hotel_address_id " +
            "      WHERE " +
            "        re.status = 'CONFIRMED' " +
            "        AND (re.period_from >= :periodFrom AND re.period_from <= :periodTo) " +
            "        OR (re.period_from <= :periodFrom AND re.period_to >= :periodTo) " +
            "        OR (re.period_to >= :periodFrom AND re.period_to <= :periodTo) " +
            "        OR (re.period_from >= :periodFrom AND re.period_to <= :periodTo) " +
            "    ) AND ro.daily_price <= :priceTo " +
            "      AND ro.daily_price >= :priceFrom " +
            "      AND ad.city LIKE %:city%")
    List<Room> findRooms(@Param("city") String city,
                         @Param("periodFrom") String periodFrom,
                         @Param("periodTo") String periodTo,
                         @Param("priceFrom") Integer priceFrom,
                         @Param("priceTo") Integer priceTo);

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
