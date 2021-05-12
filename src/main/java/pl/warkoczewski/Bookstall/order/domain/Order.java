package pl.warkoczewski.Bookstall.order.domain;


import lombok.*;


import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private Long id;
     @OneToMany
     private List<OrderItem> items;
     private transient Recipient recipient;
     @Builder.Default
     private OrderStatus status = OrderStatus.NEW;
     private LocalDateTime createdAt;

     public Order(List<OrderItem> items, Recipient recipient, OrderStatus status, LocalDateTime createdAt) {
          this.items = items;
          this.recipient = recipient;
          this.status = status;
          this.createdAt = createdAt;
     }

     public Order(List<OrderItem> items, Recipient recipient) {
          this.items = items;
          this.recipient = recipient;
     }
     /*
     public BigDecimal totalPrice() {
          return items.stream().map(orderItem ->
                  orderItem.getBookId().getPrice().multiply(new BigDecimal(orderItem.getQuantity())))

                 .reduce(BigDecimal.ZERO, BigDecimal::add);
     }*/



}
