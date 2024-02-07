package com.sc.security.securityserviceimpl;

import com.sc.security.securitymodel.User;
import com.sc.security.securityrepository.UserRepository;
import com.sc.security.securityservice.AuthService;
import com.sc.security.securityutils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private static final String ROLE_USER = "ROLE_USER";
    private static final boolean DEFAULT_ACTIVE_STATE = true;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return JwtUtils.generateToken(((UserDetails)authentication.getPrincipal()).getUsername());
    }

    @Override
    public String signup(String name, String username, String password) {

        if(userRepository.existsByUsername(username)){
            throw  new RuntimeException("User already exists");
        }

        String encodedPassword =this.passwordEncoder.encode(password);

        User user = User.builder()
                .name(name)
                .password(encodedPassword)
                .username(username)
                .isActive(DEFAULT_ACTIVE_STATE)
                .roles(ROLE_USER)
                .build();

        userRepository.save(user);

        // Generate Token
        String token = JwtUtils.generateToken(username);

        return token;
    }
}
