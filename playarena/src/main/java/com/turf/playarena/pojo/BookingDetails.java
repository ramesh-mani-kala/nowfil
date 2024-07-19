package com.turf.playarena.pojo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class BookingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bid;

    @Enumerated(EnumType.STRING)
    private TimeSlot timeslot;
    private int turfDetailsId;

}
