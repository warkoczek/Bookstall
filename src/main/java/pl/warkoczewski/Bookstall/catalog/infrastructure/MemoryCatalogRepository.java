package pl.warkoczewski.Bookstall.catalog.infrastructure;

import org.springframework.stereotype.Repository;
import pl.warkoczewski.Bookstall.catalog.domain.Book;
import pl.warkoczewski.Bookstall.catalog.domain.CatalogRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryCatalogRepository implements CatalogRepository {

    private final Map<Long, Book> storage = new ConcurrentHashMap<>();
    private final AtomicLong NEXT_ID_VALUE = new AtomicLong(0);

    public MemoryCatalogRepository() {

    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void save(Book book){
        Long nextId = getNext();
        book.setId(nextId);
        storage.put(nextId, book);
    }

    private long getNext() {
        return NEXT_ID_VALUE.getAndIncrement();
    }
}
