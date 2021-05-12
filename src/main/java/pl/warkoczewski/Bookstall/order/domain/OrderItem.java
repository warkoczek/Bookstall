package pl.warkoczewski.Bookstall.order.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import pl.warkoczewski.Bookstall.catalog.domain.Book;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue
    Long id;
    Long bookId;
    Integer quantity;

    public OrderItem(Long bookId, Integer quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
    }
}
