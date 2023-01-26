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
/*
*
* public boolean isLoggedUserADMIN(){
 return loggedUserHasRole("ROLE_ADMIN");
}

public boolean loggedUserHasRole(String role) {
 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 UserDetails loggedUser = null;
 Object roles = null;
 if (principal instanceof UserDetails) {
  loggedUser = (UserDetails) principal;

  roles = loggedUser.getAuthorities().stream()
    .filter(x -> role.equals(x.getAuthority() ))
    .findFirst().orElse(null); //loggedUser = null;
 }
 return roles != null ?true :false;
}
* */
/*
*
* Hola Cristian, revisando el código encontré una nueva novedad que si se mejora, podría servir mucho para todas las personas. Al momento de crear contraseñas nosotros ya las mandamos encriptadas, por tanto cuando tratas de comparar las mismas debemos verificar no con equals sino con matches. Coloco la solución para que cualquiera que la quiera implementar se guie (algunos nombres cambian con respecto a lo que hizo Cristian, porque yo los cambie adrede, pero la logica es igual)


@Service
public class UsuarioServiceImpl implements UsuarioService {

 @Autowired
 UsuarioRepository usuarioRepository;

 @Autowired
 BCryptPasswordEncoder bCryptPasswordEncoder;

 @Autowired
 PasswordEncoder passwordEncoder;






@Override
 public Usuario createUser(Usuario user) throws Exception {



  BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);

  if (checkUsernameAvailable(user) && checkPasswordValid(user) &&  checkEmailAvailable(user)) {

   //modificar el password para que sea seguro
   user.setContrasena(bCryptPasswordEncoder.encode(user.getContrasena()));
           //modificar el password para que sea seguro
           user = usuarioRepository.save(user);


           }
           return user;
           }


@Override
public Usuario changePassword(ChangePasswordForm form) throws Exception {
        Usuario user = getUserById(form.getId());




        //encoder.matches("123456", passwd)

        if ( !isLoggedUserADMIN() && ! passwordEncoder.matches(form.getCurrentPassword(), user.getContrasena())) {

        throw new Exception ("Current Password invalido.");
        }



        if(passwordEncoder.matches(form.getNewPassword(), user.getContrasena())) {
        throw new Exception ("Nuevo debe ser diferente al password actual.");
        }

        if( !form.getNewPassword().equals(form.getConfirmPassword())) {
        throw new Exception ("Nuevo Password y Confirm Password no coinciden.");
        }

        String encodePassword = bCryptPasswordEncoder.encode(form.getNewPassword());
        user.setContrasena(encodePassword);
        return usuarioRepository.save(user);
        }
 */
/*
*
* Buen día compañeros yo tuve muchos problemas para encriptar la contraseña desde el método crear, mi solución que en la clase UserService coloque la instancia
BCryptPasswordEncoder  como:
private final BCryptPasswordEncoder encoder;

Luego la inicialice en un constructor de la clase:

public UserService(){
                      this.encoder = new BCryptPasswordEncoder();
}

Luego ya lo pude aplicar como lo indica Cristian, espero les ayude.
* */
