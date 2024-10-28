package com.turf.playarena.repository;

import com.turf.playarena.pojo.TurfDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurfRepository extends JpaRepository<TurfDetails,Integer> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE turf_details SET available_slots = :availableSlots WHERE id = :turfDetailsId", nativeQuery = true)
    void updateAvailableSlots(@Param("turfDetailsId") int turfDetailsId, @Param("availableSlots") List<String> availableSlots);

    @Query(value = "select * from turf_details where location=:location",nativeQuery = true)
    List<TurfDetails> findByLocation(String location);
}
