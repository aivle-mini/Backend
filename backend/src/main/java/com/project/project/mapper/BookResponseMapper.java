package com.project.project.mapper;

import com.project.project.domain.Book;
import com.project.project.dto.BookDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookResponseMapper {
    BookDTO.Response entityToResponse(Book book);

    List<BookDTO.Response> booksToResponses(List<Book> books);

}
