package ru.handh.service;

import com.orgyflame.springtelegrambotapi.api.object.Location;
import com.orgyflame.springtelegrambotapi.api.object.Message;
import com.orgyflame.springtelegrambotapi.api.object.Update;
import com.orgyflame.springtelegrambotapi.api.object.User;
import com.orgyflame.springtelegrambotapi.api.service.TelegramApiService;
import com.orgyflame.springtelegrambotapi.bot.container.BotCallbackQueryContainer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.handh.kafka.Topics;
import ru.handh.kafka.events.LocationEvent;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {
    private final TelegramApiService telegramApiService;
    private final BotCallbackQueryContainer botCallbackQueryContainer;

    private final KafkaTemplate<Long, LocationEvent> locationEventKafkaTemplate;


    public void recieveCoordinates(Update update, User user){
        Message message = update.getMessage();
        Location location = message.getLocation();
        LocationEvent locationEvent = LocationEvent.builder()
                .chatId(update.getMessage().getChatId())
                .userId(user.getId())
                .lat(location.getLatitude())
                .lon(location.getLongitude())
                .build();

        locationEventKafkaTemplate.send(Topics.ADD_LOCATION, locationEvent);
    }
}
