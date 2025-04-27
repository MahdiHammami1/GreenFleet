package com.example.GreenBack.entity;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    private Double latitude;

    private Double longitude;

    private String name;

}

