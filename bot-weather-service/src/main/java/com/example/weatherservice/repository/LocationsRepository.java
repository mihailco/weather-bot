package com.example.weatherservice.repository;

import com.example.weatherservice.entity.Locations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationsRepository extends JpaRepository<Locations, Long> {

    @Query("select Locations from Locations where user.userId = ?1")
    List<Locations> findByUserId(long userId);
}
