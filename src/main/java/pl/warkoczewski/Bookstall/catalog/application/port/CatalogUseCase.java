package pl.warkoczewski.Bookstall.catalog.application.port;

import lombok.Value;
import pl.warkoczewski.Bookstall.catalog.domain.Book;

import java.util.List;
import java.util.Optional;

public interface CatalogUseCase {

    List<Book> findAll();

    Optional<Book> findOneByTitleAndAuthor(String title, String author);

    List<Book> findByAuthor(String author);

    List<Book> findByTitle(String title);

    void addBook(CreateBookCommand bookCommand);

    void removeById(Long id);

    void updateBook();

    @Value
    class CreateBookCommand{
        String title;
        String author;
        int year;
    }
}
