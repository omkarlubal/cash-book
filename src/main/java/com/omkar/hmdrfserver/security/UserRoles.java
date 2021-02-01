package com.omkar.hmdrfserver.security;

public enum UserRoles {
    SUPER_USER("SUPER_USER"),
    ADMIN("ADMIN"),
    READER("USER");

    private String value;

    // this is always private
    UserRoles(String value) {this.value = value;}

    public String getValue() {
        return this.value;
    }
}
