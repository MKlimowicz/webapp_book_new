package app.repository;

import app.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositoryBook extends JpaRepository<Book, Integer> {
    Optional<Book> findByIsbn(String isbn);
}
