package pl.warkoczewski.Bookstall.order.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.warkoczewski.Bookstall.order.application.port.ManageOrderUseCase;
import pl.warkoczewski.Bookstall.order.db.OrderJpaRepository;
import pl.warkoczewski.Bookstall.order.domain.Order;
import pl.warkoczewski.Bookstall.order.domain.OrderStatus;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class ManageOrderService implements ManageOrderUseCase {

    private final OrderJpaRepository repository;

    @Override
    public PlaceOrderResponse placeOrder(PlaceOrderCommand command) {
        Order order = Order.builder()
                .items(command.getItems())
                .recipient(command.getRecipient())
                .build();
        Order savedOrder = repository.save(order);
        return PlaceOrderResponse.success(savedOrder.getId());
    }

    public void updateOrder(Long id, OrderStatus status) {
         repository.findById(id)
                .map(order -> {
                    order.setStatus(status);
                    repository.save(order);
                    return UpdateOrderResponse.SUCCESS;
                }).orElse(new UpdateOrderResponse(false
                , Collections.singletonList("Order with given id: " + id + " could not be updated")));
    }

    @Override
    public void deleteOrderById(Long id) {
        repository.deleteById(id);
    }

}
