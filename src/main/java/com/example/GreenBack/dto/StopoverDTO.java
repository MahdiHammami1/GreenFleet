package com.example.GreenBack.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StopoverDTO {
    private String stopoverStatus;
    private double latitude;
    private double longitude;
    private String name;
}
