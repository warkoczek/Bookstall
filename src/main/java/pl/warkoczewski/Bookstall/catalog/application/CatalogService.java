package pl.warkoczewski.Bookstall.catalog.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.warkoczewski.Bookstall.catalog.application.port.CatalogUseCase;
import pl.warkoczewski.Bookstall.catalog.domain.Book;
import pl.warkoczewski.Bookstall.catalog.domain.CatalogRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
class CatalogService implements CatalogUseCase {

    private final CatalogRepository catalogRepository;

    public CatalogService(@Qualifier("memoryCatalogRepository") CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    @Override
    public List<Book> findByTitle(String title){
        return catalogRepository.findAll()
                .stream()
                .filter(book -> book.getTitle().startsWith(title))
                .collect(Collectors.toList());

    }

    @Override
    public void addBook(CreateBookCommand bookCommand) {
        Book book = new Book(bookCommand.getTitle(), bookCommand.getAuthor(), bookCommand.getYear());
        catalogRepository.save(book);
    }

    @Override
    public void removeById(Long id) {

    }

    @Override
    public UpdateBookResponse updateBook(UpdateBookCommand bookCommand) {
        return catalogRepository.findById(bookCommand.getId())
                .map(book -> {
                    book.setTitle(bookCommand.getTitle());
                    book.setAuthor(bookCommand.getAuthor());
                    book.setYear(bookCommand.getYear());
                    catalogRepository.save(book);
                    return UpdateBookResponse.SUCCESS;
                })
                .orElseGet(() -> new UpdateBookResponse(false, Arrays.asList("Book with id: " + bookCommand.getId() + " was not found")));
    }

    @Override
    public List<Book> findAll() {
        return catalogRepository.findAll();
    }

    @Override
    public Optional<Book> findOneByTitleAndAuthor(String title, String author) {
        return catalogRepository.findAll()
                .stream()
                .filter(book -> book.getTitle().startsWith(title))
                .filter(book -> book.getAuthor().startsWith(author))
                .findFirst();
    }

    @Override
    public List<Book> findByAuthor(String author){
        return catalogRepository.findAll()
                .stream()
                .filter(book -> book.getAuthor().startsWith(author))
                .collect(Collectors.toList());
    }
}
