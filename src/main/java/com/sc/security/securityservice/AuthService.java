package com.sc.security.securityservice;

import com.sc.security.securitymodel.AuthResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    public String login(String username, String password);
    public String signup(String name,String username, String password);
}
