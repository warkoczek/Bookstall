package pl.warkoczewski.Bookstall.order.application;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pl.warkoczewski.Bookstall.catalog.application.port.CatalogUseCase;
import pl.warkoczewski.Bookstall.catalog.application.port.CatalogUseCase.UpdateBookResponse;
import pl.warkoczewski.Bookstall.order.application.port.PlaceOrderUseCase;
import pl.warkoczewski.Bookstall.order.domain.Order;
import pl.warkoczewski.Bookstall.order.domain.OrderRepository;
import pl.warkoczewski.Bookstall.order.domain.OrderStatus;

import java.util.Arrays;
import java.util.Collections;

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
        Order savedOrder = repository.save(order);
        return PlaceOrderResponse.success(savedOrder.getId());
    }

    @Override
    public void updateOrder(Long id, OrderStatus status) {
         repository.findById(id)
                .map(order -> {
                    order.setStatus(status);
                    repository.save(order);
                    return UpdateOrderResponse.SUCCESS;
                }).orElse(new UpdateOrderResponse(false
                , Collections.singletonList("Order with given id: " + id + " could not be updated")));
    }

}
