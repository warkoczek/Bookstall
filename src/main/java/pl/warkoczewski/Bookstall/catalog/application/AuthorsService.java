package pl.warkoczewski.Bookstall.catalog.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.warkoczewski.Bookstall.catalog.application.port.AuthorsUseCase;
import pl.warkoczewski.Bookstall.catalog.db.AuthorsJpaRepository;
import pl.warkoczewski.Bookstall.catalog.domain.Author;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorsService implements AuthorsUseCase {

    private final AuthorsJpaRepository authorsRepository;

    @Override
    public List<Author> findAll() {
        return authorsRepository.findAll();
    }
}
