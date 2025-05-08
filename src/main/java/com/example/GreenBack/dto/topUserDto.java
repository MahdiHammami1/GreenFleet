package com.example.GreenBack.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class topUserDto {
    private String firstname;
    private String lastname;
    private int gamificationPoints;
    private String Badge;
    private Double rating;

}
