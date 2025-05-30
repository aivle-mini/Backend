package com.project.project.service;

import com.project.project.domain.Book;
import com.project.project.domain.User;
import com.project.project.dto.BookDTO;
import com.project.project.exception.BaseException;
import com.project.project.exception.BookErrorCode;
import com.project.project.mapper.BookControlMapper;
import com.project.project.mapper.BookResponseMapper;
import com.project.project.repository.BookRepository;
import com.project.project.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BookService {
    // 모든 도서 조회 yml
    public List<BookDTO.Response> findBooks();

    // 특정 도서 조회 yml
    public BookDTO.Response findBook(Long id);

    // 도서 삭제 yml
    public BookDTO.Response deleteBook(Long id);

    // 데이터 유무 검증 yml
    public Book findVerifiredBook(Long id);

    //추가로직
    public Book createBook(BookDTO.Post dto);

    //수정로직
    public Book updateBook(Long id, BookDTO.Put dto);
}
