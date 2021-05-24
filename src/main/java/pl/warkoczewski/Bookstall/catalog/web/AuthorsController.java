package pl.warkoczewski.Bookstall.catalog.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.warkoczewski.Bookstall.catalog.application.AuthorsService;
import pl.warkoczewski.Bookstall.catalog.domain.Author;

import java.util.List;

@RestController
@RequestMapping("/authors")
@AllArgsConstructor
public class AuthorsController {

    private final AuthorsService authorsService;

    @GetMapping
    public List<Author> getAllAuthors(){
        return authorsService.findAll();
    }

}
