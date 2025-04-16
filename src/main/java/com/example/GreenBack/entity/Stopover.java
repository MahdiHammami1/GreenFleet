package com.example.GreenBack.entity;

import com.example.GreenBack.enums.StopoverStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stopovers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stopover {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStopover;

    @Enumerated(EnumType.STRING)
    private StopoverStatus stopoverStatus;

    @Embedded
    private Location location;

    @ManyToOne
    @JoinColumn(name = "ride_id")
    private Ride ride;
}

