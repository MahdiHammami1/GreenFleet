package com.example.GreenBack.entity;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    private double latitude;
    private double longitude;
    private String nameLocation;
}

