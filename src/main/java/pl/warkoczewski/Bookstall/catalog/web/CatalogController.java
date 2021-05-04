package pl.warkoczewski.Bookstall.catalog.web;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.warkoczewski.Bookstall.catalog.application.port.CatalogUseCase;
import pl.warkoczewski.Bookstall.catalog.domain.Book;

import java.util.List;
@RequestMapping("/catalog")
@RestController
@AllArgsConstructor
public class CatalogController {

    private final CatalogUseCase catalog;

    @GetMapping
    public List<Book> getAll(){
        return catalog.findAll();
    }

    @GetMapping(params = "title")
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAllFiltered(@RequestParam() String title){
        return catalog.findByTitle(title);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return catalog.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

