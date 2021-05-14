package pl.warkoczewski.Bookstall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BookstallOnlineStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstallOnlineStoreApplication.class, args);
	}


}
