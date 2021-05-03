package pl.warkoczewski.Bookstall.catalog.application.port;

import lombok.Builder;
import lombok.Value;
import pl.warkoczewski.Bookstall.catalog.domain.Book;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface CatalogUseCase {

    List<Book> findAll();

    Optional<Book> findOneByTitleAndAuthor(String title, String author);

    List<Book> findByAuthor(String author);

    List<Book> findByTitle(String title);

    Optional<Book> findOneByTitle(String title);

    void addBook(CreateBookCommand bookCommand);

    void removeById(Long id);

    UpdateBookResponse updateBook(UpdateBookCommand bookCommand);

    @Value
    class CreateBookCommand{
        String title;
        String author;
        Integer year;
        BigDecimal price;
    }

    @Value
    @Builder
    class UpdateBookCommand{
         Long id;
         String title;
         String author;
         Integer year;
         BigDecimal price;

        public Book updateFields(Book book) {
            if(title != null){
                book.setTitle(title);
            }
            if(author != null){
                book.setAuthor(author);
            }
            if((year != null)){
                book.setYear(year);
            }
            if((price != null)){
                book.setPrice(price);
            }
            return book;
        }
    }

    @Value
    class UpdateBookResponse{
        public static UpdateBookResponse SUCCESS = new UpdateBookResponse(true, Collections.emptyList());
        boolean success;
        List<String> errors;
    }
}
