package pl.warkoczewski.Bookstall.catalog.domain;

import java.util.List;
import java.util.Optional;

public interface CatalogRepository {

    List<Book> findAll();

    Optional<Book> findById(Long id);

    void save(Book book);
}
