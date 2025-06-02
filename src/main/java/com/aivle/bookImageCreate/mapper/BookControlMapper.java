package com.aivle.bookImageCreate.mapper;


import com.aivle.bookImageCreate.domain.Book;
import com.aivle.bookImageCreate.dto.BookDTO;
import org.springframework.stereotype.Component;

@Component
public class BookControlMapper {
    public Book toEntity(BookDTO.Post dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setContent(dto.getContent());
        book.setImage_url(dto.getImage_url());
        book.setIs_deleted(false);
        return book;
    }
}
