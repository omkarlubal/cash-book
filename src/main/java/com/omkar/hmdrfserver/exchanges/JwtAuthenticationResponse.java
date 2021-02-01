package com.omkar.hmdrfserver.exchanges;

import java.io.Serializable;

public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final boolean success;
    private final String jwttoken;

    public JwtAuthenticationResponse(boolean success, String jwttoken) {
        this.jwttoken = jwttoken;
        this.success = success;
    }

    public String getToken() {
        return this.jwttoken;
    }

    public boolean isSuccess() {
        return success;
    }
}
