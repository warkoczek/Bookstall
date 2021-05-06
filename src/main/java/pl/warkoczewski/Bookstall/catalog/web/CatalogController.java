package pl.warkoczewski.Bookstall.catalog.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.warkoczewski.Bookstall.catalog.application.port.CatalogUseCase;
import pl.warkoczewski.Bookstall.catalog.application.port.CatalogUseCase.CreateBookCommand;
import pl.warkoczewski.Bookstall.catalog.application.port.CatalogUseCase.UpdateBookCommand;
import pl.warkoczewski.Bookstall.catalog.domain.Book;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.net.URI;
import java.util.*;

@RequestMapping("/catalog")
@RestController
@AllArgsConstructor
public class CatalogController {

    private final CatalogUseCase catalog;

    @GetMapping
    public List<Book> getAll(
            @RequestParam Optional<String> title
            , @RequestParam Optional<String> author
    ){
        if(title.isPresent() && author.isPresent()){
            return catalog.findByTitleAndAuthor(title.get(), author.get());
        } else if(title.isPresent()){
            return catalog.findByTitle(title.get());
        } else if(author.isPresent()){
            return catalog.findByAuthor(author.get());
        }
        return catalog.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return catalog.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<?> addBook(@Valid @RequestBody RestBookCommand command){
        Book book = catalog.addBook(command.toCreateCommand());
        return ResponseEntity.created(createBookUri(book)).build();
    }

    private URI createBookUri(Book book){
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + book.getId().toString()).build().toUri();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateBook(@PathVariable Long id, @RequestBody RestBookCommand command){
        CatalogUseCase.UpdateBookResponse response = catalog.updateBook(command.toUpdateCommand(id));
        if(!response.isSuccess()){
            List<String> errors = response.getErrors();
            String message = String.join(", ", errors);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id){
        catalog.removeById(id);
    }

    @Data
    private static class RestBookCommand {
        @NotBlank
        private String title;
        @NotBlank
        private String author;
        @NotNull
        private Integer year;
        @NotNull
        @DecimalMin("0.00")
        private BigDecimal price;

        CreateBookCommand toCreateCommand(){
            return new CreateBookCommand(title, author, year, price);
        }

        UpdateBookCommand toUpdateCommand(Long id){
            return new UpdateBookCommand(id, title, author, year, price);
        }
    }
}

