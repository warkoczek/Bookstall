package pl.warkoczewski.Bookstall.order.application.port;

import pl.warkoczewski.Bookstall.order.domain.Order;

import java.util.List;

public interface QueryOrderUseCase {

    List<Order> findAll();
}
