package com.turf.playarena.repository;

import com.turf.playarena.pojo.BookingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingDetails,Integer> {
    List<BookingDetails> findByTurfDetailsId(int id);

    @Query(value = "select * from booking_details where turf_details_id=:turfDetailsId and timeslot=:name",nativeQuery = true)
    BookingDetails getDetails(int turfDetailsId, String name);
}
