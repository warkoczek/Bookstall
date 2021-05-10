package pl.warkoczewski.Bookstall.order.application.port;

import lombok.*;
import pl.warkoczewski.Bookstall.catalog.application.port.CatalogUseCase;
import pl.warkoczewski.Bookstall.catalog.application.port.CatalogUseCase.UpdateBookResponse;
import pl.warkoczewski.Bookstall.order.domain.Order;
import pl.warkoczewski.Bookstall.order.domain.OrderItem;
import pl.warkoczewski.Bookstall.order.domain.OrderStatus;
import pl.warkoczewski.Bookstall.order.domain.Recipient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;

public interface ManageOrderUseCase {

    PlaceOrderResponse placeOrder(PlaceOrderCommand command);
    void updateOrder(Long id, OrderStatus status);
    void deleteOrderById(Long id);

    @Builder
    @Value
    @AllArgsConstructor
    class PlaceOrderCommand{
        @Singular
        List<OrderItem> items;
        Recipient recipient;
    }

    @Value
    class PlaceOrderResponse  {
        boolean success;
        Long orderId;
        List<String> errors;

        public static PlaceOrderResponse success(Long orderId) {
            return new PlaceOrderResponse(true, orderId, emptyList());
        }

        public static PlaceOrderResponse failure(String...errors) {
            return new PlaceOrderResponse(false, null, Arrays.asList(errors) );
        }

    }

    @Data
    @AllArgsConstructor
    class UpdateOrderResponse{
        public static UpdateOrderResponse SUCCESS = new UpdateOrderResponse(true, Collections.emptyList());
        boolean success;
        List<String> errors;

    }
}
