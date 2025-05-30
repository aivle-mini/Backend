package com.project.project.contoller;

import com.project.project.domain.Book;
import com.project.project.dto.BookDTO;
import com.project.project.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookDTO.Post dto) {
        return ResponseEntity.ok(bookService.createBook(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody BookDTO.Put dto) {
        return ResponseEntity.ok(bookService.updateBook(id, dto));
    }
}
