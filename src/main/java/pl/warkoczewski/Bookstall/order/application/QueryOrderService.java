package pl.warkoczewski.Bookstall.order.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.warkoczewski.Bookstall.catalog.db.BookJpaRepository;
import pl.warkoczewski.Bookstall.catalog.domain.Book;
import pl.warkoczewski.Bookstall.order.application.port.QueryOrderUseCase;
import pl.warkoczewski.Bookstall.order.db.OrderJpaRepository;
import pl.warkoczewski.Bookstall.order.domain.Order;
import pl.warkoczewski.Bookstall.order.domain.OrderItem;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class QueryOrderService implements QueryOrderUseCase {

    private final OrderJpaRepository orderRepository;
    private final BookJpaRepository catalogRepository;

    @Override
    public List<RichOrder> findAll() {
        return orderRepository.findAll().stream()
                .map(this::toRichOrder)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RichOrder> findById(Long id) {
        return orderRepository.findById(id).map(this::toRichOrder);
    }

    private RichOrder toRichOrder(Order order){
        List<RichOrderItem> richItems = toRichOrderItems(order.getItems());
        return new RichOrder(
                order.getId(),
                order.getStatus(),
                richItems,
                order.getRecipient(),
                order.getCreatedAt()
        );
    }

    private List<RichOrderItem> toRichOrderItems(List<OrderItem> items){
        return items.stream()
                .map(orderItem -> {
                    Book book = catalogRepository
                            .findById(orderItem.getBookId())
                            .orElseThrow(() -> new IllegalStateException("Unable to find book with ID:" + orderItem.getBookId()));
                    return new RichOrderItem(book, orderItem.getQuantity());
                })
                .collect(Collectors.toList());
    }
}
