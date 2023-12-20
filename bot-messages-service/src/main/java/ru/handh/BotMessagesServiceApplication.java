package ru.handh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
@ComponentScan(basePackages = "com.orgyflame")
public class BotMessagesServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BotMessagesServiceApplication.class, args);
    }

}
