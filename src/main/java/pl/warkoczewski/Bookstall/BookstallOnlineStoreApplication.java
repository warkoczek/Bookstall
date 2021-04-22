package pl.warkoczewski.Bookstall;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class BookstallOnlineStoreApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BookstallOnlineStoreApplication.class, args);
	}

	@Override
	public void run(String... args) {
		CatalogService catalogService = new CatalogService();
		List<Book> books = catalogService.findByTitle("Pan Tadeusz");
		books.forEach(System.out::println);

	}
}
