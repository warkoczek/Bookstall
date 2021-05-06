package pl.warkoczewski.Bookstall.catalog.infrastructure;

import org.springframework.stereotype.Repository;
import pl.warkoczewski.Bookstall.catalog.domain.Book;
import pl.warkoczewski.Bookstall.catalog.domain.CatalogRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryCatalogRepository implements CatalogRepository {

    private final Map<Long, Book> storage = new ConcurrentHashMap<>();
    private final AtomicLong NEXT_ID_VALUE = new AtomicLong(1);

    public MemoryCatalogRepository() {

    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Book save(Book book){
        if(book.getId() != null){
            storage.put(book.getId(), book);
        }else {
            Long nextId = getNext();
            book.setId(nextId);
            storage.put(nextId, book);
        }
        return book;
    }

    @Override
    public void removeById(Long id) {
        storage.remove(id);
    }

    private long getNext() {
        return NEXT_ID_VALUE.getAndIncrement();
    }
}
