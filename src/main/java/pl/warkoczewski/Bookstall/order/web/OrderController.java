package pl.warkoczewski.Bookstall.order.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.warkoczewski.Bookstall.order.application.port.QueryOrderUseCase;
import pl.warkoczewski.Bookstall.order.domain.Order;
import pl.warkoczewski.Bookstall.order.domain.OrderRepository;

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
    @GetMapping
    public ResponseEntity<Order> getOrder(@PathVariable Long id){
        return queryOrderService.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }
    //post orders
    //put orders id status
    //delete orders id
}
