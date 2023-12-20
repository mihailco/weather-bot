package ru.handh.stateMachine.states;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.handh.entity.UserState;
import ru.handh.repository.UserStateRepository;

@Service
@RequiredArgsConstructor
public class StateMachineService {
    private final UserStateRepository userStateRepository;

    public void goNext(long userId, UserState userState) {
        UserState s = UserState.builder().userId(userId).build();
        userStateRepository.save(s);
    }
}
