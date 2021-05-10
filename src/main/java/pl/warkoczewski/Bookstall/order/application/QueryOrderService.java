package pl.warkoczewski.Bookstall.order.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.warkoczewski.Bookstall.order.application.port.QueryOrderUseCase;
import pl.warkoczewski.Bookstall.order.domain.Order;
import pl.warkoczewski.Bookstall.order.domain.OrderRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class QueryOrderService implements QueryOrderUseCase {

    private final OrderRepository orderRepository;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }
}
