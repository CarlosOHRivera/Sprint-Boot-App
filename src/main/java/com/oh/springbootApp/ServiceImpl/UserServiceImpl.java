package com.oh.springbootApp.ServiceImpl;

import com.oh.springbootApp.Entity.User;
import com.oh.springbootApp.Repository.UserRepository;
import com.oh.springbootApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    //public Iterable<User> getAllUsers() {
    public Iterable getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) throws Exception {
        if (chekUsernameAvailable(user) && checkPasswordValid(user))
        {
           user= userRepository.save(user);
        }
        return user;
    }

    private boolean chekUsernameAvailable(User user) throws Exception {
        Optional<User> userfound= userRepository.findByUsername(user.getUsername());
        if (userfound.isPresent()){
            throw new Exception("Usuario ya se ha ingresado");
        }
        return true;
    }
    private boolean checkPasswordValid(User user) throws Exception {
        if (!user.getPassword().equals(user.getConfirmPassword())){
            throw new Exception("Password no se Confirma,digite de nuevo");
       }
        return true;
    }
}
