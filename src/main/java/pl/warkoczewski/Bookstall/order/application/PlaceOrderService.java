package pl.warkoczewski.Bookstall.order.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.warkoczewski.Bookstall.order.application.port.PlaceOrderUseCase;
import pl.warkoczewski.Bookstall.order.domain.Order;
import pl.warkoczewski.Bookstall.order.domain.OrderRepository;
import pl.warkoczewski.Bookstall.order.domain.OrderStatus;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PlaceOrderService implements PlaceOrderUseCase {

    private final OrderRepository repository;


    @Override
    public PlaceOrderResponse placeOrder(PlaceOrderCommand command) {

        Order order = Order.builder()
                .items(command.getItems())
                .recipient(command.getRecipient())
                .build();
        Order save = repository.save(order);
        return PlaceOrderResponse.success(save.getId());
    }
}
