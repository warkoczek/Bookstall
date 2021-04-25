package pl.warkoczewski.Bookstall.catalog.infrastructure;

import org.springframework.stereotype.Repository;
import pl.warkoczewski.Bookstall.catalog.domain.Book;
import pl.warkoczewski.Bookstall.catalog.domain.CatalogRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BestsellerCatalogRepository implements CatalogRepository {

    private final Map<Long, Book> storage = new ConcurrentHashMap<>();

    public BestsellerCatalogRepository() {
        storage.put(1l, new Book(1l, "Harry Potter i Komnata Tajemnic", "JK Rowling", 1998));
        storage.put(2l, new Book(2l, "Władca Pierścieni: Dwie Wierze", "JRR Tolkien", 1954));
        storage.put(3l, new Book(3l, "Mężczyzni którzy nie nawidzą kobiet", "Stieg Larsson", 2005));
        storage.put(4l, new Book(4l, "Sezon Burz", "Andrzej Sapkowski", 2013));
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(storage.values());
    }
}
