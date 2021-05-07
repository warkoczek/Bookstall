package pl.warkoczewski.Bookstall.catalog.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.warkoczewski.Bookstall.catalog.application.port.CatalogUseCase;
import pl.warkoczewski.Bookstall.catalog.domain.Book;
import pl.warkoczewski.Bookstall.catalog.domain.CatalogRepository;
import pl.warkoczewski.Bookstall.upload.application.UploadService;
import pl.warkoczewski.Bookstall.upload.application.port.UploadUseCase;
import pl.warkoczewski.Bookstall.upload.application.port.UploadUseCase.SaveUploadCommand;
import pl.warkoczewski.Bookstall.upload.domain.Upload;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
class CatalogService implements CatalogUseCase {

    private final CatalogRepository catalogRepository;
    private final UploadUseCase upload;

    public CatalogService(@Qualifier("memoryCatalogRepository") CatalogRepository catalogRepository, UploadUseCase upload) {
        this.catalogRepository = catalogRepository;
        this.upload = upload;
    }

    @Override
    public Optional<Book> findById(Long id){
        return catalogRepository.findById(id);
    }

    @Override
    public List<Book> findByTitle(String title){
        return catalogRepository.findAll()
                .stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());

    }

    @Override
    public Optional<Book> findOneByTitle(String title) {
        return catalogRepository.findAll().stream().filter(book -> book.getTitle().contains(title)).findFirst();
    }

    @Override
    public Optional<Book> findOneByAuthor(String author) {
        return catalogRepository.findAll()
                .stream().filter(book -> book.getTitle().toLowerCase().contains(author.toLowerCase()))
                .findFirst();
    }

    @Override
    public Book addBook(CreateBookCommand command) {
        Book book = command.toBook();
        return catalogRepository.save(book);
    }

    @Override
    public void removeById(Long id) {
        catalogRepository.removeById(id);
    }

    @Override
    public UpdateBookResponse updateBook(UpdateBookCommand bookCommand) {
        return catalogRepository.findById(bookCommand.getId())
                .map(book -> {
                    Book updatedBook = bookCommand.updateFields(book);
                    catalogRepository.save(updatedBook);
                    return UpdateBookResponse.SUCCESS;
                })
                .orElseGet(() -> new UpdateBookResponse(false, Collections.singletonList("Book with id: " + bookCommand.getId() + " was not found")));
    }

    @Override
    public void updateBookCover(UpdateBookCoverCommand command){
        int bytes = command.getFile().length;
        System.out.println("Pan Tadeusz " + command.getFilename() + " has bytes: " + bytes);
        catalogRepository.findById(command.getId()).ifPresent(
                book -> {
                    SaveUploadCommand saveUploadCommand = new SaveUploadCommand(command.getFilename()
                            , command.getFile(), command.getContentType());
                    Upload savedUpload = upload.save(saveUploadCommand);
                    book.setCoverId(savedUpload.getId());
                    catalogRepository.save(book);
                }
        );
    }

    @Override
    public List<Book> findAll() {
        return catalogRepository.findAll();
    }

    @Override
    public Optional<Book> findOneByTitleAndAuthor(String title, String author) {
        return catalogRepository.findAll()
                .stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .findFirst();
    }

    @Override
    public List<Book> findByTitleAndAuthor(String title, String author) {
        return catalogRepository.findAll().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByAuthor(String author){
        return catalogRepository.findAll()
                .stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }
}
