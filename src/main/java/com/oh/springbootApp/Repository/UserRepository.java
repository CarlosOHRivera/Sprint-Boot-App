package com.oh.springbootApp.Repository;

import java.util.Optional;

import com.oh.springbootApp.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
/*public interface UserRepository extends CrudRepository<User, Long>{*/

public interface UserRepository extends JpaRepository<User,Long> {

    public Optional findByUsername(String username);

    public Optional findByIdAndPassword(Long id, String password);

}
