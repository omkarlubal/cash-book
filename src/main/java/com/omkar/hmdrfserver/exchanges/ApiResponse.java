package com.omkar.hmdrfserver.exchanges;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    @NotNull
    private boolean success;

    @NotNull
    private String msg;
}
