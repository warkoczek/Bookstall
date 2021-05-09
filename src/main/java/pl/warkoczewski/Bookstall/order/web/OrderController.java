package pl.warkoczewski.Bookstall.order.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.warkoczewski.Bookstall.order.application.port.QueryOrderUseCase;
import pl.warkoczewski.Bookstall.order.domain.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final QueryOrderUseCase queryOrderService;
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

        return null;
    }
    //put orders id status
    //delete orders id

     static class CreateOrderCommand{
        private OrderItem item;
        private Recipient recipient;
        private OrderStatus status;
        private LocalDateTime createAt;
    }
}
