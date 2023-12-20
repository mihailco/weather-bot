package com.example.weatherservice.service;

import com.example.weatherservice.config.WeatherProperties;
import com.example.weatherservice.feign.WeatherClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.handh.model.Root;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherProperties weatherProperties;
    private final WeatherClient weatherClient;

    public Root getWeather(double lon, double lat) {
        return weatherClient.getWeather(lon, lat, weatherProperties.getApiKey(), "metric");
    }
}
