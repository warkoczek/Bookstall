package pl.warkoczewski.Bookstall.order.domain;


import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
public class Order {

     private Long id;
     private List<OrderItem> items;
     private Recipient recipient;
     private OrderStatus status;
     private LocalDateTime createdAt;

     public BigDecimal totalPrice(){
         return items.stream().map(orderItem -> orderItem.getBook().getPrice().multiply(new BigDecimal(orderItem.getQuantity())))
                 .reduce(BigDecimal.ZERO, BigDecimal::add);
     }



}
