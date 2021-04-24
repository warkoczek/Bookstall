package pl.warkoczewski.Bookstall;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.warkoczewski.Bookstall.catalog.application.CatalogController;
import pl.warkoczewski.Bookstall.catalog.domain.Book;
import pl.warkoczewski.Bookstall.catalog.domain.CatalogService;

import java.util.List;

@SpringBootApplication
public class BookstallOnlineStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstallOnlineStoreApplication.class, args);
	}


}
