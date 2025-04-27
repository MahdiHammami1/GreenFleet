package com.example.GreenBack.dto;

import com.example.GreenBack.enums.AlternativeType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RideSearchDTO {
    private LocationDTO fromLocation;
    private LocalDate date;
    private LocalTime time;
    private int rayonPossible;
    private AlternativeType alternativeType;
    private CarDTO car;
}
/*
   "fromLocation":{
      "name":"Cite Ibn Khaldoun"
      "latitude":36.826421
      "longitude":10.144960
   }
   "date":"2025-04-25"
   "time":"17:35:00"
   "rayonPossible":5
   "AlternativeType":"CAR"
   "car":{
        "model":"Peugeot 206"
        "age":8
   }
*/
