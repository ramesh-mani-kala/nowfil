package com.turf.playarena.dto;

import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.List;

@Data
public class BookingDto {

    private int tid;
    private String turfName;
    private String phoneNumber;
    private String sqft;
    private double pricePerHour;
    private String location;
//    private int bid;
    private List<String> availableSlots;
}
