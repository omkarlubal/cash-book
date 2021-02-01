package com.omkar.hmdrfserver.exchanges;

import lombok.Data;

@Data
public class SignUpUser {
    // TODO: remove option to add role/auths, default all to user
    private String username;
    private String email;
    private String password;
    private String roles;
}
