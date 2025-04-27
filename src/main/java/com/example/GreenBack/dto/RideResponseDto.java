package com.example.GreenBack.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RideResponseDto {
    private Long driverId;
    private String driverName;
    private String carName;
    private Double rateDriver;
    private String stopoverName; // li matchet m3ehaaa
    private String distanceBetween;
    private List<String> prefrences;
}
