package app.dto;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class BorrowDto {

    private Integer id;
    private LocalDate start;
    private LocalDate end;
    private Integer user_id;
    private Integer publication_id;



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

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }


    public Integer getPublication_id() {
        return publication_id;
    }

    public void setPublication_id(Integer publication_id) {
        this.publication_id = publication_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BorrowDto borrowDto = (BorrowDto) o;
        return Objects.equals(id, borrowDto.id) &&
                Objects.equals(start, borrowDto.start) &&
                Objects.equals(end, borrowDto.end) &&
                Objects.equals(user_id, borrowDto.user_id) &&
                Objects.equals(publication_id, borrowDto.publication_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, start, end, user_id,  publication_id);
    }

    @Override
    public String toString() {
        return "BorrowDto{" +
                "id=" + id +
                ", start=" + start +
                ", end=" + end +
                ", user_id=" + user_id +
                ", publication_id=" + publication_id +
                '}';
    }
}
