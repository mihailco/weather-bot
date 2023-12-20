package com.example.weatherservice.service;

import com.example.weatherservice.entity.Locations;
import com.example.weatherservice.repository.LocationsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationsService {

    private final LocationsRepository locationsRepository;

    @Transactional
    public List<Locations> getByUserId(long userId){
        return locationsRepository.findByUserId(userId);
    }


    public List<Locations> getAllLocations() {
        return locationsRepository.findAll();
    }

    public Locations getLocationById(Long id) {
        return locationsRepository.findById(id).orElse(null);
    }

    public Locations createLocation(Locations location) {
        return locationsRepository.save(location);
    }

    public Locations updateLocation(Long id, Locations updatedLocation) {
        if (locationsRepository.existsById(id)) {
            updatedLocation.setId(id);
            return locationsRepository.save(updatedLocation);
        }
        return null;
    }

    public void deleteLocation(Long id) {
        locationsRepository.deleteById(id);
    }

}
