package pl.warkoczewski.Bookstall.order.domain;


import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pl.warkoczewski.Bookstall.jpa.BaseEntity;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
public class Order extends BaseEntity {

     @OneToMany(cascade = CascadeType.ALL)
     @JoinColumn(name = "order_id")
     private List<OrderItem> items;
     @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
     private Recipient recipient;
     @Builder.Default
     @Enumerated(EnumType.STRING)
     private OrderStatus status = OrderStatus.NEW;
     @CreatedDate
     private LocalDateTime createdAt;

     @LastModifiedDate
     private LocalDateTime updatedAt;

     public void updateStatus(OrderStatus newStatus){
          this.status = status.updateOrderStatus(newStatus);
     }

}
