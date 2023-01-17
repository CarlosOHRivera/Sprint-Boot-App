package com.oh.springbootApp.Service;

import com.oh.springbootApp.Entity.User;

public interface UserService {
    public Iterable<User> getAllUsers();

   public User createUser(User user) throws Exception;
   public User getUserById(Long id) throws Exception;
}
