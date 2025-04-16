package com.example.GreenBack.repository;

import com.example.GreenBack.entity.Stopover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopoverRepository extends JpaRepository<Stopover,Long> {
}
