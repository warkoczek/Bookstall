package pl.warkoczewski.Bookstall.order.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.warkoczewski.Bookstall.order.application.port.ManipulateOrderUseCase;
import pl.warkoczewski.Bookstall.order.domain.Order;
import pl.warkoczewski.Bookstall.order.domain.OrderRepository;

@Service
@RequiredArgsConstructor
public class ManipulateOrderService implements ManipulateOrderUseCase {

    private final OrderRepository repository;

    @Override
    public PlaceOrderResponse placeOrder(PlaceOrderCommand command) {
        Order order = Order.builder()
                .items(command.getItems())
                .recipient(command.getRecipient())
                .build();
        Order savedOrder = repository.save(order);
        return PlaceOrderResponse.success(savedOrder.getId());
    }
}
