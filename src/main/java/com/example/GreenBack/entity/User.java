package com.example.GreenBack.entity;

import com.example.GreenBack.enums.Badge;
import com.example.GreenBack.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phoneNumber;

    @Column(name = "verification_code")
    private String verificationCode;
    @Column(name = "verification_expiration")
    private LocalDateTime verificationCodeExpiresAt;
    @Column(name = "enabled")
    private boolean enabled;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate dateOfBirth;

    private Float rating;
    private Integer gamificationPoints;
    private boolean verified;
    private String profilePictureUrl;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ride> publishedRides;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vehicle> vehicles;

    @OneToMany(mappedBy = "passenger", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;

    @ElementCollection(targetClass = Badge.class)
    @Enumerated(EnumType.STRING)
    private List<Badge> badges;

    public User(String email, String password) {
        this.email=email;
        this.password=password;
    }

    public User(String email, String password, String firstname, String lastname) {
        this.email=email;
        this.password=password;
        this.firstname=firstname;
        this.lastname=lastname;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
