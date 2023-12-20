package com.example.weatherservice.listener;

import com.example.weatherservice.entity.Locations;
import com.example.weatherservice.entity.UserEntity;
import com.example.weatherservice.service.LocationsService;
import com.example.weatherservice.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.handh.kafka.KafkaGroupIds;
import ru.handh.kafka.Topics;
import ru.handh.kafka.events.LocationEvent;
import ru.handh.kafka.events.StartEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserListener {
    private final UserService userService;
    private final LocationsService locationsService;
    @PersistenceContext
    private EntityManager entityManager;

    @KafkaListener(topics = Topics.START, groupId = KafkaGroupIds.WEATHER)
    public void onStart(StartEvent event) {
        log.info("recieved {}", event);
        UserEntity user = UserEntity.builder()
                .userId(event.getUserId())
                .chatId(event.getChatId())
                .name(event.getName())
                .username(event.getUsername())
                .build();

        userService.createUser(user);
    }



    @KafkaListener(topics = Topics.ADD_LOCATION, groupId = KafkaGroupIds.WEATHER)
    public void onAddLocation(LocationEvent event) {
        log.info("recieved {}", event);

        UserEntity userEntity = entityManager.getReference(UserEntity.class, event.getUserId());

        Locations locations = Locations.builder()
                .lon(event.getLon())
                .lat(event.getLat())
                .user(userEntity)
                .build();
        locationsService.createLocation(locations);
    }
}
