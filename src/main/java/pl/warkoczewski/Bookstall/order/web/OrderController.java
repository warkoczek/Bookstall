package pl.warkoczewski.Bookstall.order.web;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.warkoczewski.Bookstall.catalog.domain.Book;
import pl.warkoczewski.Bookstall.order.application.port.ManipulateOrderUseCase;
import pl.warkoczewski.Bookstall.order.application.port.ManipulateOrderUseCase.PlaceOrderCommand;
import pl.warkoczewski.Bookstall.order.application.port.QueryOrderUseCase;
import pl.warkoczewski.Bookstall.order.domain.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final QueryOrderUseCase queryOrderService;
    private final ManipulateOrderUseCase placeOrderService;
    private static final String message = "List is empty";

    //get orders
    @GetMapping
    public ResponseEntity<Object> getOrders(){
        List<Order> orders = queryOrderService.findAll();
        if(!orders.isEmpty()){
            return ResponseEntity.ok(orders);
        }
        return ResponseEntity.ok(message);
    }
    //get orders/id
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id){
        return queryOrderService.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }
    //post orders
    @PostMapping
    public ResponseEntity<Order> addOrder(@RequestBody CreateOrderCommand command){
        PlaceOrderCommand placeOrderCommand = command.toPlaceOrderCommand();
        ManipulateOrderUseCase.PlaceOrderResponse placeOrderResponse = placeOrderService.placeOrder(placeOrderCommand);
        return null;
    }
    //put orders id status
    //delete orders id
    @Data
     static class CreateOrderCommand{
        List<OrderItemCommand> itemsCommand;
        RecipientCommand recipientCommand;

        PlaceOrderCommand toPlaceOrderCommand(){
            List<OrderItem> orderItems = itemsCommand.stream()
                    .map(orderItemCommand ->
                        new OrderItem(orderItemCommand.getBook(), orderItemCommand.getQuantity())
                    )
                    .collect(Collectors.toList());
            return new PlaceOrderCommand(orderItems, recipientCommand.toRecipient());
        }
    }
    @Data
    static class OrderItemCommand{
         Book book;
         Integer quantity;
    }
    @Data
    static class RecipientCommand{
        String name;
        String phone;
        String street;
        String city;
        String zipCode;
        String email;

        Recipient toRecipient(){
            return new Recipient(name, phone, street, city, zipCode, email);
        }
    }
}
