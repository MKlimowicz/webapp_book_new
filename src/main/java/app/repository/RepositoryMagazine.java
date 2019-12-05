package app.repository;

import app.model.Magazine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositoryMagazine extends JpaRepository<Magazine, Integer> {
    Optional<Magazine> findByTitle(String title);
}
