package com.sc.security.securitycontroller;

import com.sc.security.securityenum.Authentication_Status;
import com.sc.security.securitymodel.AuthResponse;
import com.sc.security.securitymodel.LoginAuthRequest;
import com.sc.security.securitymodel.SignAuthRequest;
import com.sc.security.securitymodel.User;
import com.sc.security.securityservice.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginAuthRequest loginAuthRequest){
        try {
            String jwtToken = authService.login(loginAuthRequest.getUsername(), loginAuthRequest.getPassword());
            AuthResponse authResponse = new AuthResponse(jwtToken, Authentication_Status.LOGIN_SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(authResponse);
        }catch (Exception e){
            AuthResponse authResponse = new AuthResponse(null, Authentication_Status.LOGIN_FAILED);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(authResponse);
        }
    }
    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignAuthRequest signAuthRequest){
        try {
            String jwtToken = authService.signup(signAuthRequest.getName(), signAuthRequest.getUsername(), signAuthRequest.getPassword());
            AuthResponse authResponse = new AuthResponse(jwtToken, Authentication_Status.USER_CREATED_SUCCESSFULLY);
            return ResponseEntity.status(HttpStatus.OK).body(authResponse);
        }catch (Exception e){
            AuthResponse authResponse = new AuthResponse(null, Authentication_Status.USER_NOT_CREATED);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(authResponse);
        }
    }
}
