package pl.warkoczewski.Bookstall.catalog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pl.warkoczewski.Bookstall.jpa.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "authors")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Book extends BaseEntity {

    private String title;
    private Integer year;
    private BigDecimal price;
    private Long coverId;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
    @JsonIgnoreProperties("books")
    private Set<Author> authors = new HashSet<>();
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Book(String title, Integer year, BigDecimal price) {
        this.title = title;
        this.year = year;
        this.price = price;
    }

    public void addAuthor(Author author){
        authors.add(author);
        author.getBooks().add(this);
    }

    public void removeAuthor(Author author){
        authors.remove(author);
        author.getBooks().remove(this);
    }

    public void removeAuthors(){
        Book self = this;
        authors.forEach(author -> author.getBooks().remove(self));
        authors.clear();
    }
}
