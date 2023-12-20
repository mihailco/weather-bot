package com.example.weatherservice.scheduler;

import com.example.weatherservice.entity.Locations;
import com.example.weatherservice.entity.UserEntity;
import com.example.weatherservice.service.UserService;
import com.example.weatherservice.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.handh.kafka.Topics;
import ru.handh.kafka.events.WeatherResponceEvent;

@Component
@RequiredArgsConstructor
public class WeatherScheduler {
    private final UserService userService;
    private final KafkaTemplate<Long, WeatherResponceEvent> responceEventKafkaTemplate;
    private final WeatherService weatherService;

    @Scheduled(cron = "0 0 7 * * *")
    public void sendMorningMessage() {
        int page = 0;
        int pageSize = 10;

        Page<UserEntity> usersPage;
        do {
            usersPage = userService.getAllUsers(page, pageSize);
            for (UserEntity user : usersPage.getContent()) {

                for (Locations l : user.getLocations()) {
                    var w = weatherService.getWeather(l.getLon(), l.getLat());

                    var weatherResponseEvent = WeatherResponceEvent.builder()
                            .userId(user.getUserId())
                            .chatId(user.getChatId())
                            .weather(w)
                            .build();

                    responceEventKafkaTemplate.send(Topics.WEATHER, weatherResponseEvent);
                }
            }
            page++;
        } while (usersPage.hasNext());

    }
}
