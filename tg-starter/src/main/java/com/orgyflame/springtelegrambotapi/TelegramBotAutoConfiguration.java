package com.orgyflame.springtelegrambotapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orgyflame.springtelegrambotapi.api.service.DefaultTelegramApiService;
import com.orgyflame.springtelegrambotapi.api.service.TelegramApiService;
import com.orgyflame.springtelegrambotapi.bot.BotUpdateHandlerService;
import com.orgyflame.springtelegrambotapi.bot.DefaultBotUpdateHandlerService;
import com.orgyflame.springtelegrambotapi.bot.MyTelegramLongPollingHandler;
import com.orgyflame.springtelegrambotapi.bot.TelegramBotListener;
import com.orgyflame.springtelegrambotapi.bot.container.BotApiMappingContainer;
import com.orgyflame.springtelegrambotapi.bot.container.BotCallbackQueryContainer;
import com.orgyflame.springtelegrambotapi.bot.container.DefaultBotApiMappingContainer;
import com.orgyflame.springtelegrambotapi.bot.container.DefaultBotCallbackQueryContainer;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@ComponentScan
@EnableConfigurationProperties(TelegramBotProperties.class)
public class TelegramBotAutoConfiguration {
    @Autowired
    private TelegramBotProperties telegramBotProperties;

    @Bean
    @ConditionalOnMissingBean
    public TelegramApiService telegramApiService() {
        return new DefaultTelegramApiService(telegramBotProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public BotApiMappingContainer botApiMappingContainer() {
        return new DefaultBotApiMappingContainer();
    }

    @Bean
    @ConditionalOnMissingBean
    public BotCallbackQueryContainer botCallbackQueryContainer() {
        return new DefaultBotCallbackQueryContainer();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean({TelegramApiService.class, BotApiMappingContainer.class})
    public TelegramBotListener telegramBotListener(ApplicationContext context, BotApiMappingContainer botApiMappingContainer) {
        return new TelegramBotListener(context, telegramBotProperties, botApiMappingContainer);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean({BotCallbackQueryContainer.class, BotApiMappingContainer.class})
    public BotUpdateHandlerService botUpdateHandlerService(
            BotCallbackQueryContainer botCallbackQueryContainer,
            BotApiMappingContainer botApiMappingContainer,
            TelegramApiService telegramApiService,
            ModelMapper modelMapper
    ) {
        return new DefaultBotUpdateHandlerService(botCallbackQueryContainer, botApiMappingContainer, telegramApiService, modelMapper);
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
//              .setSkipNullEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        return mapper;
    }

    @SneakyThrows
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean({BotCallbackQueryContainer.class, BotApiMappingContainer.class})
    public BotSession registerBot(
            MyTelegramLongPollingHandler myTelegramLongPollingHandler
    ) {
        return new TelegramBotsApi(DefaultBotSession.class).registerBot(myTelegramLongPollingHandler);
    }

//    @Bean
//    @ConditionalOnBean(BotUpdateHandlerService.class)
//    public RouterFunction<ServerResponse> routeWebhookCallback(BotUpdateHandlerService botUpdateHandlerService) {
//
//        return RouterFunctions
//                .route(POST(telegramBotProperties.getCallbackMapping()),
//                        serverRequest -> serverRequest.bodyToMono(Update.class)
//                                .doOnNext(botUpdateHandlerService::handle)
//                                .then(ServerResponse.ok().build())
//                );
//    }
}
