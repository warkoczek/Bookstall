package pl.warkoczewski.Bookstall.order.web;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.warkoczewski.Bookstall.catalog.domain.Book;
import pl.warkoczewski.Bookstall.order.application.port.ManageOrderUseCase;
import pl.warkoczewski.Bookstall.order.application.port.ManageOrderUseCase.PlaceOrderCommand;
import pl.warkoczewski.Bookstall.order.application.port.ManageOrderUseCase.PlaceOrderResponse;
import pl.warkoczewski.Bookstall.order.application.port.QueryOrderUseCase;
import pl.warkoczewski.Bookstall.order.application.port.QueryOrderUseCase.RichOrder;
import pl.warkoczewski.Bookstall.order.db.OrderJpaRepository;
import pl.warkoczewski.Bookstall.order.domain.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final QueryOrderUseCase queryOrderService;
    private final ManageOrderUseCase manageOrderService;
    private static final String message = "List is empty";

    //get orders
    @GetMapping
    public ResponseEntity<Object> getOrders(){
        List<RichOrder> orders = queryOrderService.findAll();
        if(!orders.isEmpty()){
            return ResponseEntity.ok(orders);
        }
        return ResponseEntity.ok(message);
    }
    //get orders/id
    @GetMapping("/{id}")
    public ResponseEntity<RichOrder> getOrder(@PathVariable Long id){
        return queryOrderService.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }
    //post orders
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Object> addOrder(@RequestBody CreateOrderCommand command){
        PlaceOrderResponse response = manageOrderService.placeOrder(command.toPlaceOrderCommand());
        return ResponseEntity.created(orderUri(response.getOrderId())).build();
    }
    private URI orderUri(Long orderId) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + orderId.toString()).build().toUri();
    }
    //put orders id status
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateOrder(@PathVariable Long id, @RequestBody RestUpdateOrderCommand command){
        OrderStatus status = OrderStatus
                .parseString(command.status)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No such status" + command.getStatus()));
        manageOrderService.updateOrder(id, status);
    }
    //delete orders id
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrderById(@PathVariable Long id){
        manageOrderService.deleteOrderById(id);
    }

    @Data
     private static class CreateOrderCommand{
        List<OrderItemCommand> orderItemCommandList;
        RecipientCommand recipientCommand;

        PlaceOrderCommand toPlaceOrderCommand(){
            List<OrderItem> orderItems = orderItemCommandList.stream()
                    .map(orderItemCommand ->
                        new OrderItem(orderItemCommand.getBook().getId(), orderItemCommand.getQuantity())
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
    static class RestUpdateOrderCommand {
         String status;
    }
}
