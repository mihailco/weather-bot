package com.example.weatherservice.repository;

import com.example.weatherservice.entity.ScheduledTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduledTimeRepository extends JpaRepository<ScheduledTime, Long> {
}
