package com.project.project.dto;

import com.project.project.domain.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

public class BookDTO {
    // 신규 도서 생성
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        @NotBlank()
        @NotNull()
        private String title;

        @NotBlank()
        @NotNull()
        private String content;

        @NotBlank()
        @NotNull
        private String image_url;

        private Long userId;
    }

    // 조회(GET)
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String title;
        private String content;
        private User user;
        private String created_at;
        private String updated_at;
    }

    // 도서 수정 (PUT)
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Put {
        @NotBlank()
        @NotNull()
        private String title;

        @NotBlank()
        @NotNull()
        private String content;

        @NotBlank()
        @NotNull
        private String image_url;
    }




}
