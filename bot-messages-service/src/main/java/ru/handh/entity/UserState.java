
package ru.handh.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.handh.stateMachine.states.States;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserState {
    @Id
    private Long userId;
    private States state;
    private LocalDateTime expirationTime= LocalDateTime.now();
}
