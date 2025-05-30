package com.project.project.repository;

import com.project.project.domain.Book;
import com.project.project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
}
