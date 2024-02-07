package com.sc.security.securitymodel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SignAuthRequest {
    private String name;
    private String username;
    private String password;

}
