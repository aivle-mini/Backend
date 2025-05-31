package com.aivle.bookImageCreate.repository;


import com.aivle.bookImageCreate.domain.Book;
import com.aivle.bookImageCreate.dto.BookDTO;


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

