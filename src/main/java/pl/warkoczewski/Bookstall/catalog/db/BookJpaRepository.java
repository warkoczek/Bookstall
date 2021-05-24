package pl.warkoczewski.Bookstall.catalog.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.warkoczewski.Bookstall.catalog.domain.Book;

import java.util.List;

public interface BookJpaRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthors(String author);
}
