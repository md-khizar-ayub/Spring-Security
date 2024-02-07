package com.sc.security.securityserviceimpl;

import com.sc.security.securitymodel.User;
import com.sc.security.securityrepository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {


    private final UserRepository userRepositoty;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUsername = userRepositoty.findByUsername(username);
       if(optionalUsername.isPresent()){
           return new UserDetailsImpl(optionalUsername.get());
       }
       else {
           throw new UsernameNotFoundException("User Name not Found");
       }


    }
}
