package com.example.GreenBack.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleId;

    private int licenceNumber;
    private String brand;
    private String model;
    private int numberOfSeat;
    private Date registrationDate;
    private String pictureUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
}
