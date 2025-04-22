package com.example.GreenBack.dto;

import lombok.*;

import java.util.Date;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {
    private Long vehicleId;
    private int licenceNumber;
    private String brand;
    private String model;
    private int numberOfSeat;
    private Date registrationDate;
    private String pictureUrl;
    private Long ownerId;
}