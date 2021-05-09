package pl.warkoczewski.Bookstall.order.application.port;

import pl.warkoczewski.Bookstall.order.domain.Order;

import java.util.List;
import java.util.Optional;

public interface QueryOrderUseCase {

    List<Order> findAll();

    Optional<Order> findById(Long id);
}
