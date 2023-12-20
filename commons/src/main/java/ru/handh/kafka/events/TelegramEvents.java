package ru.handh.kafka.events;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class TelegramEvents {
    protected Long userId;
    protected Long chatId;
}
