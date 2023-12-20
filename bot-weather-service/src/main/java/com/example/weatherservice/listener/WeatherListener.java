package com.example.weatherservice.listener;

import com.example.weatherservice.entity.Locations;
import com.example.weatherservice.service.LocationsService;
import com.example.weatherservice.service.UserService;
import com.example.weatherservice.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.handh.kafka.KafkaGroupIds;
import ru.handh.kafka.Topics;
import ru.handh.kafka.events.GetWeatherEvent;
import ru.handh.kafka.events.WeatherResponceEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherListener {
    private final UserService userService;
    private final LocationsService locationsService;
    private final WeatherService weatherService;

    private final KafkaTemplate<Long, WeatherResponceEvent> responceEventKafkaTemplate;


    @KafkaListener(topics = Topics.GETWEATHER, groupId = KafkaGroupIds.WEATHER)
    public void onGetWeather(GetWeatherEvent event) {
        log.info("recieved {}", event);

        var locations = userService.getLocationsById(event.getUserId());
        for (Locations l : locations) {
            var w = weatherService.getWeather(l.getLon(), l.getLat());

            var weatherResponceEvent = WeatherResponceEvent.builder()
                    .userId(event.getUserId())
                    .chatId(event.getChatId())
                    .weather(w)
                    .build();

            responceEventKafkaTemplate.send(Topics.WEATHER, weatherResponceEvent);
        }


    }


}
