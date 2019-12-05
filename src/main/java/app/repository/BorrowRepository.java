package app.repository;

import app.model.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface BorrowRepository extends JpaRepository<Borrow, Integer> {
    Optional<Borrow> findByPublication_idAndEndIsNull(Integer book_id);
}
