package pl.warkoczewski.Bookstall.order.domain;

import lombok.Value;
import pl.warkoczewski.Bookstall.catalog.domain.Book;

@Value
public class OrderItem {

    Book book;
    Integer quantity;


}
