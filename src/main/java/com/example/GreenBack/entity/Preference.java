package com.example.GreenBack.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "preferences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long preferenceId;

    private String description;

    @ManyToOne
    @JoinColumn(name = "ride_id")
    private Ride ride;
}
