package com.example.GreenBack.entity;

import com.example.GreenBack.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User passenger;
    @ManyToOne
    @JoinColumn(name = "ride_id")
    private Ride ride;
    private String feedback;
    private Double rating;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    private String pickupLocation;


}
