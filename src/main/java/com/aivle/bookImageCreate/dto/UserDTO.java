package com.aivle.bookImageCreate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserDTO {

    @Getter
    @Setter
    public static class Register {
        private String email;
        private String passwd;
        private String name;
    }

    @Getter
    @Setter
    public static class Login {
        private String username;
        private String passwd;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String username;
        private String email;
    }
}