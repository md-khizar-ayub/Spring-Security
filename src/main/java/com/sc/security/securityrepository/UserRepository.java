package com.sc.security.securityrepository;

import com.sc.security.securitymodel.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String > {


    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);


//    @Query("SELECT DISTINCT u FROM User u WHERE u.roles LIKE %:role1% OR u.roles LIKE %:role2%")
//    List<User> findByRolesContainingDistinct(String role1, String role2);
}
