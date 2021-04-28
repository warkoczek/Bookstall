package pl.warkoczewski.Bookstall.catalog.application.port;

import lombok.AllArgsConstructor;
import lombok.Value;
import pl.warkoczewski.Bookstall.catalog.domain.Book;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface CatalogUseCase {

    List<Book> findAll();

    Optional<Book> findOneByTitleAndAuthor(String title, String author);

    List<Book> findByAuthor(String author);

    List<Book> findByTitle(String title);

    void addBook(CreateBookCommand bookCommand);

    void removeById(Long id);

    UpdateBookResponse updateBook(UpdateBookCommand bookCommand);

    @Value
    class CreateBookCommand{
        String title;
        String author;
        int year;
    }

    @Value
    class UpdateBookCommand{
        Long id;
        String title;
        String author;
        int year;

    }

    @Value
    class UpdateBookResponse{
        public static UpdateBookResponse SUCCESS = new UpdateBookResponse(true, Collections.emptyList());
        boolean success;
        List<String> errors;
    }
}
