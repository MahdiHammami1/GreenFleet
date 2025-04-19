package com.example.GreenBack.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "rides")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rideId;

    private LocalDate rideDate;
    private LocalTime rideTime;
    private int numberOfSeat;
    private int availableSeats;
    private boolean published;
    private double totalDistance;

    @OneToMany(mappedBy = "ride", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Preference> preferences;

    @OneToMany(mappedBy = "ride", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;

    @OneToMany(mappedBy = "ride", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stopover> stopovers;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User driver;

}

