package pl.warkoczewski.Bookstall.catalog.application.port;

import pl.warkoczewski.Bookstall.catalog.domain.Author;

import java.util.List;

public interface AuthorsUseCase {

    List<Author> findAll();
}
