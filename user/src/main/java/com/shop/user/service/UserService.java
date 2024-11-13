package com.shop.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.user.model.User;
import com.shop.user.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public User saveUser(User order){
        return userRepository.save(order);
    }

    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

    public Optional<User> getUserWithPassword(String email, String password){
        List<User> userList = userRepository.getUserWithPassword(email,password);
        if(userList.size() == 1){
            return Optional.of(userList.getFirst());
        }else{
            return Optional.empty();
        }
    }
}
