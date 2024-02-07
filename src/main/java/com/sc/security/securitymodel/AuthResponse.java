package com.sc.security.securitymodel;

import com.sc.security.securityenum.Authentication_Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AuthResponse {
    private String token;
    private Authentication_Status authStatus;


}
