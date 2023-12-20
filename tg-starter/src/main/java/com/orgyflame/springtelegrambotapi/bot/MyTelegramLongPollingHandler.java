package com.orgyflame.springtelegrambotapi.bot;

import com.orgyflame.springtelegrambotapi.TelegramBotProperties;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Slf4j
@Component
public class MyTelegramLongPollingHandler extends TelegramLongPollingBot {
    private final BotUpdateHandlerService botUpdateHandlerService;
    private final TelegramBotProperties telegramBotProperties;
    private final ModelMapper modelMapper;

    public MyTelegramLongPollingHandler(BotUpdateHandlerService botUpdateHandlerService,
                                        TelegramBotProperties telegramBotProperties, ModelMapper modelMapper) {
        super(new DefaultBotOptions(), telegramBotProperties.getToken());
        this.telegramBotProperties = telegramBotProperties;
        this.botUpdateHandlerService = botUpdateHandlerService;
        this.modelMapper = modelMapper;
    }


    @Override
    public void onUpdateReceived(Update update) {
        var upd = modelMapper.map(update, com.orgyflame.springtelegrambotapi.api.object.Update.class);
        botUpdateHandlerService.handle(upd);
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    @Override
    public String getBotUsername() {
        return telegramBotProperties.getUsername();
    }

    @Override
    public void onRegister() {
        super.onRegister();
        log.info("register");

    }
}
