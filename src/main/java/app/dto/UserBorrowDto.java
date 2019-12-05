package app.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class UserBorrowDto {

    private Integer id;
    private LocalDate start;
    private LocalDate end;
    private Integer publication_id;
    private String title;
    private String publisher;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public Integer getPublication_id() {
        return publication_id;
    }

    public void setPublication_id(Integer book_id) {
        this.publication_id = book_id;
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



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBorrowDto that = (UserBorrowDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(start, that.start) &&
                Objects.equals(end, that.end) &&
                Objects.equals(publication_id, that.publication_id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(publisher, that.publisher);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, start, end, publication_id, title, publisher);
    }

    @Override
    public String toString() {
        return "UserBorrowDto{" +
                "id=" + id +
                ", start=" + start +
                ", end=" + end +
                ", book_id=" + publication_id +
                ", title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                '}';
    }
}
