package pl.warkoczewski.Bookstall.order.domain;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.*;
import pl.warkoczewski.Bookstall.catalog.domain.Book;
import pl.warkoczewski.Bookstall.jpa.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class OrderItem extends BaseEntity {

    Long bookId;
    Integer quantity;

    public OrderItem(Long bookId, Integer quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
    }
}
