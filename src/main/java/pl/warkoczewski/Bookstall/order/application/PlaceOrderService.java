package pl.warkoczewski.Bookstall.order.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.warkoczewski.Bookstall.order.application.port.PlaceOrderUseCase;
import pl.warkoczewski.Bookstall.order.domain.OrderRepository;

@Service
@RequiredArgsConstructor
public class PlaceOrderService implements PlaceOrderUseCase {

    private final OrderRepository repository;


    @Override
    public PlaceOrderResponse placeOrder(PlaceOrderCommand command) {
        return null;
    }
}
