package ru.handh.kafka.events;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder()
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StartEvent extends TelegramEvents{
    protected String name;
    protected String username;


}
