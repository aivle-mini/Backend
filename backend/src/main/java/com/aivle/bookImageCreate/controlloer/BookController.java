package com.aivle.bookImageCreate.controlloer;

import com.aivle.bookImageCreate.domain.Book;
import com.aivle.bookImageCreate.dto.BookDTO;
import com.aivle.bookImageCreate.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;

    //bckang
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookDTO.Post dto) {
        return ResponseEntity.ok(bookService.createBook(dto));
    }

    //bckang
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody BookDTO.Put dto) {
        return ResponseEntity.ok(bookService.updateBook(id, dto));
    }

    // 전체 도서 조회 ymlee
    @GetMapping
    public List<BookDTO.Response> getBooks() {
        return bookService.findBooks();
    }

    // 특정 도서 조회 ymlee
    @GetMapping("/{bookId}")
    public BookDTO.Response getBook(@PathVariable("bookId") Long id) {
        return bookService.findBook(id);
    }

    // 삭제 ymlee
    @DeleteMapping("/{bookId}")
    public BookDTO.Response deleteBook(@PathVariable("bookId") Long id) {
        return bookService.deleteBook(id);
    }

}