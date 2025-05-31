package com.aivle.bookImageCreate.service;

import com.aivle.bookImageCreate.domain.Book;
import com.aivle.bookImageCreate.domain.User;
import com.aivle.bookImageCreate.dto.BookDTO;
import com.aivle.bookImageCreate.exception.BaseException;
import com.aivle.bookImageCreate.exception.BookErrorCode;
import com.aivle.bookImageCreate.mapper.BookControlMapper;
import com.aivle.bookImageCreate.mapper.BookResponseMapper;
import com.aivle.bookImageCreate.repository.BookRepository;
import com.aivle.bookImageCreate.repository.BookService;
import com.aivle.bookImageCreate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookControlMapper mapper;
    private final BookResponseMapper responseMapper;

    // 모든 도서 조회 yml
    public List<BookDTO.Response> findBooks() {
        List<Book> books = bookRepository.findAll();
        System.out.println(books);
        System.out.println(responseMapper.booksToResponses(books));
        return responseMapper.booksToResponses(books);
    }

    // 특정 도서 조회 yml
    public BookDTO.Response findBook(Long id) {
        Book book = findVerifiredBook(id);
        return responseMapper.entityToResponse(book);
    }

    // 도서 삭제 yml
    public BookDTO.Response deleteBook(Long id) {
        Book book = findVerifiredBook(id);
        boolean deleted = Boolean.TRUE.equals(book.getIs_deleted());

        book.setIs_deleted(!deleted); // 토글
        return responseMapper.entityToResponse(book);
    }

    // 데이터 유무 검증 yml
    public Book findVerifiredBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> BaseException.type(BookErrorCode.NOT_FOUND_BOOK));
    }

    //추가로직
    public Book createBook(BookDTO.Post dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Book book = mapper.toEntity(dto);
        book.setUser(user);
        return bookRepository.save(book);
    }

    //수정로직
    public Book updateBook(Long id, BookDTO.Put dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("책을 찾을 수 없습니다."));
        book.setTitle(dto.getTitle());
        book.setContent(dto.getContent());
        book.setImage_url(dto.getImage_url());
        return bookRepository.save(book);
    }
}
