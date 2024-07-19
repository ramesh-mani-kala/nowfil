package com.turf.playarena.pojo;

import jakarta.persistence.*;
import lombok.Data;



@Entity
@Table(name = "turf_details")
@Data
public class TurfDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String turfName;
    private String phoneNumber;
    private String sqft;
    private double pricePerHour;
    private String location;

}
