package app.dto;

import java.util.Objects;

public class BookDto implements PublicationDto {

    private Integer id;
    private String title;
    private String publisher;
    private Integer year;
    private String author;
    private int pages;
    private String isbn;
    private final String type = "BOOK";


    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", year=" + year +
                ", author='" + author + '\'' +
                ", pages=" + pages +
                ", isbn='" + isbn + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDto bookDto = (BookDto) o;
        return pages == bookDto.pages &&
                Objects.equals(id, bookDto.id) &&
                Objects.equals(title, bookDto.title) &&
                Objects.equals(publisher, bookDto.publisher) &&
                Objects.equals(year, bookDto.year) &&
                Objects.equals(author, bookDto.author) &&
                Objects.equals(isbn, bookDto.isbn) &&
                Objects.equals(type, bookDto.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, publisher, year, author, pages, isbn, type);
    }
}
