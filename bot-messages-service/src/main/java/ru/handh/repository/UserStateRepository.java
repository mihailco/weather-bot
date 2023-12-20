package ru.handh.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.handh.entity.UserState;

import java.time.LocalDateTime;

public interface UserStateRepository extends JpaRepository< UserState, Long> {
    @Transactional
    void deleteAllByExpirationTimeBefore(LocalDateTime localDateTime);
}
