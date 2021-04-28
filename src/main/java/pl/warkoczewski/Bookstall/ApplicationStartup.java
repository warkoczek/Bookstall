package pl.warkoczewski.Bookstall;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.warkoczewski.Bookstall.catalog.application.port.CatalogUseCase;
import pl.warkoczewski.Bookstall.catalog.domain.Book;

import java.util.List;
@Component
public class ApplicationStartup implements CommandLineRunner {

    private final CatalogUseCase catalog;
    private final String title;
    private final Long limit;
    private final String author;

    public ApplicationStartup(CatalogUseCase catalog, @Value("${bookstall.catalog.book.title}") String title
    , @Value("${bookstall.catalog.book.limit}") Long limit
    , @Value("${bookstall.catalog.book.author}") String author) {
        this.catalog = catalog;
        this.title = title;
        this.limit = limit;
        this.author = author;
    }

    @Override
    public void run(String... args) {
        dataInit();
        List<Book> books = catalog.findByTitle(title);
        books.stream().limit(limit).forEach(System.out::println);
        List<Book> byAuthor = catalog.findByAuthor(author);
        byAuthor.forEach(System.out::println);
    }

    private void dataInit() {

        catalog.addBook( new CatalogUseCase.CreateBookCommand( "Pan Tadeusz", "Adam Mickiewicz", 1834));
        catalog.addBook( new CatalogUseCase.CreateBookCommand( "Ogniem i mieczem", "Henryk Sienkiewicz", 1884));
        catalog.addBook( new CatalogUseCase.CreateBookCommand( "Chłopi", "Władysław Reymont", 1904));
        catalog.addBook( new CatalogUseCase.CreateBookCommand( "Pan Wołodyjowski", "Henryk Sienkiewicz", 1899));
    }
}
