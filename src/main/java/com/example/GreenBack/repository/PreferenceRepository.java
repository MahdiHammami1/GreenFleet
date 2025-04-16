package com.example.GreenBack.repository;

import com.example.GreenBack.entity.Preference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferenceRepository extends JpaRepository< Preference,Long> {
}
