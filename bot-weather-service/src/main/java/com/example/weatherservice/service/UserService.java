package com.example.weatherservice.service;

import com.example.weatherservice.entity.Locations;
import com.example.weatherservice.entity.UserEntity;
import com.example.weatherservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public Page<UserEntity> getAllUsers(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return userRepository.findAll(pageRequest);
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    @Transactional
    public List<Locations> getLocationsById(Long id) {
        var us = userRepository.findById(id).orElse(null);
        if (us!=null){
            return us.getLocations();
        }
        return new ArrayList<>();
    }

    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    public UserEntity updateUser(Long id, UserEntity updatedUser) {
        if (userRepository.existsById(id)) {
            updatedUser.setUserId(id);
            return userRepository.save(updatedUser);
        }
        return null;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
