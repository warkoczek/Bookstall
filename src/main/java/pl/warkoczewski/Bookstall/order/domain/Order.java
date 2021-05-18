package pl.warkoczewski.Bookstall.order.domain;


import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


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
@EntityListeners(AuditingEntityListener.class)
public class Order {
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private Long id;
     @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
     @JoinColumn(name = "order_id")
     private List<OrderItem> items;
     private Recipient recipient;
     @Builder.Default
     @Enumerated(EnumType.STRING)
     private OrderStatus status = OrderStatus.NEW;
     @CreatedDate
     private LocalDateTime createdAt;

     @LastModifiedDate
     private LocalDateTime updatedAt;

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
