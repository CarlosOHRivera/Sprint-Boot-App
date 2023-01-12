package com.oh.springbootApp.ServiceImpl;

import com.oh.springbootApp.Repository.UserRepository;
import com.oh.springbootApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    //public Iterable<User> getAllUsers() {
    public Iterable getAllUsers() {
        return userRepository.findAll();
    }
}
