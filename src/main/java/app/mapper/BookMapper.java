package app.mapper;

import app.dto.BookDto;
import app.model.Book;

public class BookMapper {


    public static BookDto toDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setAuthor(book.getAuthor());
        dto.setIsbn(book.getIsbn());
        dto.setPages(book.getPages());
        dto.setPublisher(book.getPublisher());
        dto.setTitle(book.getTitle());
        dto.setYear(book.getYear());
        return dto;
    }

    public static Book toEntity(BookDto bookDto) {
        Book entity = new Book();
        entity.setId(bookDto.getId());
        entity.setAuthor(bookDto.getAuthor());
        entity.setIsbn(bookDto.getIsbn());
        entity.setPages(bookDto.getPages());
        entity.setPublisher(bookDto.getPublisher());
        entity.setTitle(bookDto.getTitle());
        entity.setYear(bookDto.getYear());
        return entity;
    }
}
