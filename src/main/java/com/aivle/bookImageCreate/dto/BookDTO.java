package com.aivle.bookImageCreate.dto;

import com.aivle.bookImageCreate.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

public class BookDTO {
    // 신규 도서 생성
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        private String title;
        private String content;
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

        private String title;
        private String content;
        private String image_url;
    }




}

