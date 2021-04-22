package pl.warkoczewski.Bookstall;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class CatalogService {

    private final Map<Long, Book> storage = new ConcurrentHashMap<>();

    public CatalogService() {
        storage.put(1l, new Book(1l, "Pan Tadeusz", "Adam Mickiewicz", 1834));
        storage.put(2l, new Book(2l, "Ogniem i mieczem", "Henryk Sienkiewicz", 1884));
        storage.put(3l, new Book(3l, "Chłopi", "Władysław Reymont", 1904));
    }

    List<Book> findByTitle(String title){
        return storage.values()
                .stream()
                .filter(book -> book.getTitle().startsWith(title))
                .collect(Collectors.toList());

    }
}
