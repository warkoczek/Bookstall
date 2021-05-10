package pl.warkoczewski.Bookstall.order.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
public class Order {

     private Long id;
     private List<OrderItem> items;
     private Recipient recipient;
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

     public BigDecimal totalPrice(){
         return items.stream().map(orderItem -> orderItem.getBook().getPrice().multiply(new BigDecimal(orderItem.getQuantity())))
                 .reduce(BigDecimal.ZERO, BigDecimal::add);
     }



}
