package com.example.weatherservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    private Long userId;
    private Long chatId;
    private String username;
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    List<Locations> locations = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ScheduledTime> scheduledTimes = new ArrayList<>();

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", chatId=" + chatId +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

