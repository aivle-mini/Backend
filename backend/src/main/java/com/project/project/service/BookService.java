package com.project.project.service;

import com.project.project.domain.Book;
import com.project.project.domain.User;
import com.project.project.dto.BookDTO;
import com.project.project.mapper.BookControlMapper;
import com.project.project.repository.BookRepository;
import com.project.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookControlMapper mapper;

    public Book createBook(BookDTO.Post dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Book book = mapper.toEntity(dto);
        book.setUser(user);
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, BookDTO.Put dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("책을 찾을 수 없습니다."));
        book.setTitle(dto.getTitle());
        book.setContent(dto.getContent());
        book.setImage_url(dto.getImage_url());
        return bookRepository.save(book);
    }
}
