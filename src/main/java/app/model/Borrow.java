package app.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate start;
    private LocalDate end;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "publication_id")
    private Publication publication;


    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }



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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Borrow borrowBook = (Borrow) o;
        return Objects.equals(id, borrowBook.id) &&
                Objects.equals(start, borrowBook.start) &&
                Objects.equals(end, borrowBook.end) &&
                Objects.equals(user, borrowBook.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, start, end, user);
    }

    @Override
    public String toString() {
        return "BorrowBook{" +
                "id =" + id +
                ", start =" + start +
                ", end =" + end +
                ", user_id =" + user.getId() +
                ", publication_id =" + publication.getId()+
                '}';
    }
}
