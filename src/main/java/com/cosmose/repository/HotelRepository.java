package com.cosmose.repository;

import com.cosmose.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by damian on 25.08.18.
 */
@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>{
}
