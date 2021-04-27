package pl.warkoczewski.Bookstall;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.warkoczewski.Bookstall.catalog.application.CatalogService;
import pl.warkoczewski.Bookstall.catalog.domain.Book;

import java.util.List;
@Component
public class ApplicationStartup implements CommandLineRunner {

    private final CatalogService catalogService;
    private final String title;
    private final Long limit;
    private final String author;

    public ApplicationStartup(CatalogService catalogService, @Value("${bookstall.catalog.book.title}") String title
    , @Value("${bookstall.catalog.book.limit}") Long limit
    , @Value("${bookstall.catalog.book.author}") String author) {
        this.catalogService = catalogService;
        this.title = title;
        this.limit = limit;
        this.author = author;
    }

    @Override
    public void run(String... args) {
        List<Book> books = catalogService.findByTitle(title);
        books.stream().limit(limit).forEach(System.out::println);
        List<Book> byAuthor = catalogService.findByAuthor(author);
        byAuthor.forEach(System.out::println);
    }
}
