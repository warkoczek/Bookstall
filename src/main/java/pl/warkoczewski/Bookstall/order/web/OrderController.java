package pl.warkoczewski.Bookstall.order.web;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.warkoczewski.Bookstall.catalog.domain.Book;
import pl.warkoczewski.Bookstall.order.application.port.PlaceOrderUseCase;
import pl.warkoczewski.Bookstall.order.application.port.PlaceOrderUseCase.PlaceOrderCommand;
import pl.warkoczewski.Bookstall.order.application.port.PlaceOrderUseCase.PlaceOrderResponse;
import pl.warkoczewski.Bookstall.order.application.port.QueryOrderUseCase;
import pl.warkoczewski.Bookstall.order.domain.*;

import javax.validation.constraints.NotEmpty;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final QueryOrderUseCase queryOrderService;
    private final PlaceOrderUseCase placeOrderService;
    private final OrderRepository orderRepository;
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
    public ResponseEntity<Object> addOrder(@RequestBody CreateOrderCommand command){
        PlaceOrderResponse response = placeOrderService.placeOrder(command.toPlaceOrderCommand());
        return ResponseEntity.created(orderUri(response.getOrderId())).build();
    }
    //put orders id status
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOrder(@PathVariable Long id, @RequestBody UpdateOrderCommand command){
        return queryOrderService.findById(id)
                .map(order -> {
                    order.setStatus(command.getStatus());
                    orderRepository.save(order);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
    //delete orders id

    private URI orderUri(Long orderId) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + orderId.toString()).build().toUri();
    }


    @Data
     private static class CreateOrderCommand{
        List<OrderItemCommand> orderItemCommandList;
        RecipientCommand recipientCommand;

        PlaceOrderCommand toPlaceOrderCommand(){
            List<OrderItem> orderItems = orderItemCommandList.stream()
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
    @Data
    static class UpdateOrderCommand{
         OrderStatus status;
    }
}
