package pl.warkoczewski.Bookstall.catalog.infrastructure;

import org.springframework.stereotype.Repository;
import pl.warkoczewski.Bookstall.catalog.domain.Book;
import pl.warkoczewski.Bookstall.catalog.domain.CatalogRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Repository
public class MemoryCatalogRepository implements CatalogRepository {

    private final Map<Long, Book> storage = new ConcurrentHashMap<>();

    public MemoryCatalogRepository() {
        storage.put(1l, new Book(1l, "Pan Tadeusz", "Adam Mickiewicz", 1834));
        storage.put(2l, new Book(2l, "Ogniem i mieczem", "Henryk Sienkiewicz", 1884));
        storage.put(3l, new Book(3l, "Chłopi", "Władysław Reymont", 1904));
        storage.put(4l, new Book(4l, "Pan Wołodyjowski", "Henryk Sienkiewicz", 1899));
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(storage.values());
    }
}
