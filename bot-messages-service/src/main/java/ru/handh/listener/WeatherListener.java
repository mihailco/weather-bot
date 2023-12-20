package ru.handh.listener;

import com.orgyflame.springtelegrambotapi.api.method.BotApiMethod;
import com.orgyflame.springtelegrambotapi.api.method.send.SendMessage;
import com.orgyflame.springtelegrambotapi.api.service.TelegramApiService;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.handh.kafka.KafkaGroupIds;
import ru.handh.kafka.Topics;
import ru.handh.kafka.events.WeatherResponceEvent;
import ru.handh.service.WeatherService;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherListener {
    private final WeatherService weatherService;
    private final TelegramApiService telegramApiService;

    @KafkaListener(topics = Topics.WEATHER, groupId = KafkaGroupIds.TELEGRAM_BOT)
    public void onWeather(WeatherResponceEvent event) {
        log.info("recieved {}", event);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(event.getChatId()));

        var weather = event.getWeather().getMain();
        String res = String.format("Город: %s\nТемпература колеблется от %s и до %s, ощущается как %s \nВлажность: %s\nДавление: %s",
                event.getWeather().getName(),
                weather.getTemp_min(), weather.getTemp_max(), weather.getFeels_like(),
                weather.getHumidity(),
                weather.getPressure()


        );
        sendMessage.setText(res);
        send(sendMessage);
    }


    private void send(BotApiMethod botApiMethod) {
        try {
            telegramApiService.sendApiMethod(botApiMethod).subscribe();
        } catch (TelegramApiValidationException e) {
            e.printStackTrace();
        }
    }

}
