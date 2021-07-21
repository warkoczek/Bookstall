package pl.warkoczewski.Bookstall.catalog.db;

import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.warkoczewski.Bookstall.catalog.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookJpaRepository extends JpaRepository<Book, Long> {

    @Query("select distinct b from Book b JOIN FETCH b.authors")
    List<Book> findAllEager();

    //List<Book> findByAuthors(String author);
   //List<Book> findByAuthors_firstNameContainsIgnoreCaseOrAuthors_lastNameContainsIgnoreCase(String firstName, String lastName);
   //List<Book> findByTitleAndAuthors(String title, String firstName, String lastName);
   List<Book> findByTitleStartsWithIgnoreCase(String title);
    Optional<Book> findFirstByTitle(String title);

   // @Query("select b from Book b JOIN b.authors a" +
    /*" where " +
            " lower(a.firstName) LIKE lower(concat('%', :name, '%')) " +
            " or lower(a.lastName) LIKE lower(concat('%', :name, '%')) ")*/
    List<Book> findByAuthorsContaining(String name);


}
