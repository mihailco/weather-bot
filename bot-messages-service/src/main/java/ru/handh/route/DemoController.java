package ru.handh.route;


import com.orgyflame.springtelegrambotapi.api.method.AnswerCallbackQuery;
import com.orgyflame.springtelegrambotapi.api.method.BotApiMethod;
import com.orgyflame.springtelegrambotapi.api.method.send.SendMessage;
import com.orgyflame.springtelegrambotapi.api.object.Chat;
import com.orgyflame.springtelegrambotapi.api.object.Message;
import com.orgyflame.springtelegrambotapi.api.object.Update;
import com.orgyflame.springtelegrambotapi.api.object.User;
import com.orgyflame.springtelegrambotapi.api.service.TelegramApiService;
import com.orgyflame.springtelegrambotapi.bot.container.BotCallbackQueryContainer;
import com.orgyflame.springtelegrambotapi.bot.inline.menu.InlineMenu;
import com.orgyflame.springtelegrambotapi.bot.inline.menu.InlineMenuButton;
import com.orgyflame.springtelegrambotapi.bot.mapping.BotController;
import com.orgyflame.springtelegrambotapi.bot.mapping.BotMapping;
import com.orgyflame.springtelegrambotapi.bot.mapping.parameter.ChatParam;
import com.orgyflame.springtelegrambotapi.bot.mapping.parameter.FromParam;
import com.orgyflame.springtelegrambotapi.bot.mapping.parameter.UpdateParam;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import ru.handh.kafka.Topics;
import ru.handh.kafka.events.GetWeatherEvent;
import ru.handh.kafka.events.StartEvent;
import ru.handh.service.WeatherService;

@RequiredArgsConstructor
@BotController
public class DemoController {
    private final TelegramApiService telegramApiService;
    private final BotCallbackQueryContainer botCallbackQueryContainer;
    private final WeatherService weatherService;

    private final KafkaTemplate<Long, StartEvent> startEventKafkaTemplate;
    private final KafkaTemplate<Long, GetWeatherEvent> getWeatherEventKafkaTemplate;


    private void send(BotApiMethod botApiMethod) {
        try {
            telegramApiService.sendApiMethod(botApiMethod).subscribe();
        } catch (TelegramApiValidationException e) {
            e.printStackTrace();
        }
    }


    @BotMapping(value = "")
    public void hello2(@FromParam User user, @ChatParam Chat chat, @UpdateParam Update update) {
        Message message = update.getMessage();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chat.getId()));
        if (message.getLocation() != null) {
            weatherService.recieveCoordinates(update, user);
            sendMessage.setText("Теперь можете попробовать /weather");
            return;
        } else {
            sendMessage.setText("Действий на данную команду нет");
        }

        send(sendMessage);
    }

    @BotMapping(value = "/start", description = "Greets the user")
    public void hello(@FromParam User user, @ChatParam Chat chat) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chat.getId()));
        sendMessage.setText("Привет, это бот позволит получать погоду по расписанию. Отправь мне координаты города");
        send(sendMessage);

        var startEvent = StartEvent.builder()
                .chatId(chat.getId())
                .userId(user.getId())
                .username(user.getUserName())
                .name(user.getFirstName())
                .build();

        startEventKafkaTemplate.send(Topics.START, startEvent);
    }

    @BotMapping(value = "/weather", description = "Получить текущую погоду")
    public void getWeather(@FromParam User user, @ChatParam Chat chat) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chat.getId()));
        sendMessage.setText("Запрос отправлен, ждите");
        send(sendMessage);

        var startEvent = GetWeatherEvent.builder()
                .chatId(chat.getId())
                .userId(user.getId())
                .build();

        getWeatherEventKafkaTemplate.send(Topics.GETWEATHER, startEvent);
    }

    @BotMapping(value = "/inline", description = "test function")
    public void inlineKeyboard(@ChatParam Chat chat) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chat.getId()));
        sendMessage.setText("keyboard:");

        InlineMenu inlineMenu = InlineMenu.builder()
                .button(
                        InlineMenuButton.builder()
                                .text("button 1")
                                .onQueryCallback(this::button1Callback)
                                .build()
                )
                .row()
                .button(
                        InlineMenuButton.builder()
                                .text("button 2")
                                .onQueryCallback(this::button2Callback)
                                .build()
                )
                .build(botCallbackQueryContainer);

        sendMessage.setReplyMarkup(inlineMenu);

        send(sendMessage);
    }

    public AnswerCallbackQuery button1Callback(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        sendMessage.setText("Hello,");
        send(sendMessage);

        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
        answerCallbackQuery.setText("Clicked on button 1");
        return answerCallbackQuery;
    }

    private AnswerCallbackQuery button2Callback(Update update) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
        answerCallbackQuery.setText("Clicked on button 2");
        return answerCallbackQuery;
    }
}