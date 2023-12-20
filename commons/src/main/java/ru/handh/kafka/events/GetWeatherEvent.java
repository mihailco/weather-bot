package ru.handh.kafka.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder()
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GetWeatherEvent extends TelegramEvents{
}
