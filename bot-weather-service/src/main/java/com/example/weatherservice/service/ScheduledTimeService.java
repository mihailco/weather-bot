package com.example.weatherservice.service;

import com.example.weatherservice.entity.ScheduledTime;
import com.example.weatherservice.repository.ScheduledTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduledTimeService {

    private final ScheduledTimeRepository scheduledTimeRepository;

    public List<ScheduledTime> getAllScheduledTimes() {
        return scheduledTimeRepository.findAll();
    }

    public ScheduledTime getScheduledTimeById(Long id) {
        return scheduledTimeRepository.findById(id).orElse(null);
    }

    public ScheduledTime createScheduledTime(ScheduledTime scheduledTime) {
        return scheduledTimeRepository.save(scheduledTime);
    }

    public ScheduledTime updateScheduledTime(Long id, ScheduledTime updatedScheduledTime) {
        if (!scheduledTimeRepository.existsById(id)) {
            return null;
        }
        updatedScheduledTime.setId(id);
        return scheduledTimeRepository.save(updatedScheduledTime);

    }

    public void deleteScheduledTime(Long id) {
        scheduledTimeRepository.deleteById(id);
    }

    // Другие методы, если необходимо
}
