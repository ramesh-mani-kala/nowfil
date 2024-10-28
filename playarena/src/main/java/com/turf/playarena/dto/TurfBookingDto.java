package com.turf.playarena.dto;

import com.turf.playarena.pojo.TimeSlot;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class TurfBookingDto {

    @Enumerated(EnumType.STRING)
    private TimeSlot timeslot;
    private int turfDetailsId;
}
