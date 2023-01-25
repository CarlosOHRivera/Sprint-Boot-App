package com.oh.springbootApp.ServiceImpl;

import com.oh.springbootApp.Entity.User;
import com.oh.springbootApp.Repository.UserRepository;
import com.oh.springbootApp.Service.UserService;
import com.oh.springbootApp.dto.ChangePasswordForm;
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
        if (user.getConfirmPassword()== null || user.getConfirmPassword().isEmpty() ){
            throw new Exception("Debe confirmar el PassWord");
        }

        if (!user.getPassword().equals(user.getConfirmPassword())){
            throw new Exception("Password no se Confirma,digite de nuevo");
       }
        return true;
    }

    public User getUserById(Long id) throws Exception{
       User user = userRepository.findById(id).orElseThrow(()->  new Exception("El usuario No Existe!!!"));
        return user;
    }

    public User updateUser(User fromUser) throws Exception {
        User toUser = getUserById(fromUser.getId());
        mapUser(fromUser, toUser);
        return userRepository.save(toUser);
    }


    /* Mapeamos todos los campos  excepto el password */
    protected void mapUser(User from,User to) {
        to.setUsername(from.getUsername());
        to.setFirstName(from.getFirstName());
        to.setLastName(from.getLastName());
        to.setEmail(from.getEmail());
        to.setRoles(from.getRoles());
        to.setConfirmPassword(to.getPassword());// He colocado que sean iguales... para el caso de la edición... ya que de lo contrario da error por las validadciones que tiene en la Entity
    }
    @Override
    public void deleteUser(Long id) throws Exception {
        User user = getUserById(id);
        userRepository.deleteById(id);
    }

    @Override
    public User changePassword(ChangePasswordForm form) throws Exception {
        User user = getUserById(form.getId());

        if ( !user.getPassword().equals(form.getCurrentPassword())) {
            throw new Exception ("Password actual NO válido.");
        }

        if( user.getPassword().equals(form.getNewPassword())) {
            throw new Exception ("Nuevo debe ser diferente al password actual.");
        }

        if( !form.getNewPassword().equals(form.getConfirmPassword())) {
            throw new Exception ("Nuevo Password y la Confirmación no coinciden.");
        }

        user.setPassword(form.getNewPassword());
        user.setConfirmPassword(form.getNewPassword());
        return userRepository.save(user);
    }
}
