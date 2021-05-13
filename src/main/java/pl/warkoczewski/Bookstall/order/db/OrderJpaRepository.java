package pl.warkoczewski.Bookstall.order.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.warkoczewski.Bookstall.order.domain.Order;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {
}
