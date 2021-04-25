package pl.warkoczewski.Bookstall.catalog.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogService {

    private final CatalogRepository catalogRepository;

    public CatalogService(@Qualifier("bestsellerCatalogRepository") CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    public List<Book> findByTitle(String title){
        return catalogRepository.findAll()
                .stream()
                .filter(book -> book.getTitle().startsWith(title))
                .collect(Collectors.toList());

    }
}
