package pl.warkoczewski.Bookstall.catalog.application;

import org.springframework.stereotype.Service;
import pl.warkoczewski.Bookstall.catalog.application.port.CatalogUseCase;
import pl.warkoczewski.Bookstall.catalog.db.BookJpaRepository;
import pl.warkoczewski.Bookstall.catalog.domain.Book;
import pl.warkoczewski.Bookstall.upload.application.port.UploadUseCase;
import pl.warkoczewski.Bookstall.upload.application.port.UploadUseCase.SaveUploadCommand;
import pl.warkoczewski.Bookstall.upload.domain.Upload;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
class CatalogService implements CatalogUseCase {

    private final BookJpaRepository bookJpaRepository;
    private final UploadUseCase upload;

    public CatalogService(BookJpaRepository bookJpaRepository, UploadUseCase upload) {
        this.bookJpaRepository = bookJpaRepository;
        this.upload = upload;
    }

    @Override
    public Optional<Book> findById(Long id){
        return bookJpaRepository.findById(id);
    }

    @Override
    public List<Book> findByTitle(String title){
        return bookJpaRepository.findAll()
                .stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());

    }

    @Override
    public Optional<Book> findOneByTitle(String title) {
        return bookJpaRepository.findAll().stream().filter(book -> book.getTitle().contains(title)).findFirst();
    }

    @Override
    public Optional<Book> findOneByAuthor(String author) {
        return bookJpaRepository.findAll()
                .stream().filter(book -> book.getTitle().toLowerCase().contains(author.toLowerCase()))
                .findFirst();
    }

    @Override
    public Book addBook(CreateBookCommand command) {
        Book book = command.toBook();
        return bookJpaRepository.save(book);
    }

    @Override
    public void removeById(Long id) {
        bookJpaRepository.deleteById(id);
    }

    @Override
    public UpdateBookResponse updateBook(UpdateBookCommand bookCommand) {
        return bookJpaRepository.findById(bookCommand.getId())
                .map(book -> {
                    Book updatedBook = bookCommand.updateFields(book);
                    bookJpaRepository.save(updatedBook);
                    return UpdateBookResponse.SUCCESS;
                })
                .orElseGet(() -> new UpdateBookResponse(false, Collections.singletonList("Book with id: " + bookCommand.getId() + " was not found")));
    }

    @Override
    public void updateBookCover(UpdateBookCoverCommand command){
        int bytes = command.getFile().length;
        System.out.println("Pan Tadeusz " + command.getFilename() + " has bytes: " + bytes);
        bookJpaRepository.findById(command.getId()).ifPresent(
                book -> {
                    SaveUploadCommand saveUploadCommand = new SaveUploadCommand(command.getFilename()
                            , command.getFile(), command.getContentType());
                    Upload savedUpload = upload.save(saveUploadCommand);
                    book.setCoverId(savedUpload.getId());
                    bookJpaRepository.save(book);
                }
        );
    }

    @Override
    public List<Book> findAll() {
        return bookJpaRepository.findAll();
    }

    @Override
    public Optional<Book> findOneByTitleAndAuthor(String title, String author) {
        return bookJpaRepository.findAll()
                .stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .findFirst();
    }

    @Override
    public List<Book> findByTitleAndAuthor(String title, String author) {
        return bookJpaRepository.findAll().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByAuthor(String author){
        return bookJpaRepository.findAll()
                .stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public void removeBookCover(Long id) {
         bookJpaRepository.findById(id)
                 .ifPresent(book -> {
                     if(book.getCoverId() != null) {
                         upload.removeById(book.getCoverId());
                         book.setCoverId(null);
                         bookJpaRepository.save(book);
                     }
                 });

    }
}
