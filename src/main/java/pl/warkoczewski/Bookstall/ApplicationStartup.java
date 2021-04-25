package pl.warkoczewski.Bookstall;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.warkoczewski.Bookstall.catalog.application.CatalogController;
import pl.warkoczewski.Bookstall.catalog.domain.Book;

import java.util.List;
@Component

public class ApplicationStartup implements CommandLineRunner {

    private final CatalogController catalogController;
    private final String title;

    public ApplicationStartup(CatalogController catalogController, @Value("${bookstall.catalog.book.title}") String title) {
        this.catalogController = catalogController;
        this.title = title;
    }

    @Override
    public void run(String... args) {
        List<Book> books = catalogController.findByTitle("Pan");
        books.forEach(System.out::println);

    }
}
