package pl.warkoczewski.Bookstall.catalog.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.warkoczewski.Bookstall.catalog.domain.Author;

public interface AuthorsJpaRepository extends JpaRepository<Author, Long> {
}
