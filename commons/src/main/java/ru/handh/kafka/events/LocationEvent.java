package ru.handh.kafka.events;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder()
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LocationEvent extends TelegramEvents{
    private double lat;
    private double lon;
}
