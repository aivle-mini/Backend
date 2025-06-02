package com.aivle.bookImageCreate.mapper;
import com.aivle.bookImageCreate.domain.Book;
import com.aivle.bookImageCreate.dto.BookDTO;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookResponseMapper {
    BookDTO.Response entityToResponse(Book book);
    List<BookDTO.Response> booksToResponses(List<Book> books);
}
