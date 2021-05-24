package pl.warkoczewski.Bookstall;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.warkoczewski.Bookstall.catalog.application.port.CatalogUseCase;
import pl.warkoczewski.Bookstall.catalog.application.port.CatalogUseCase.CreateBookCommand;
import pl.warkoczewski.Bookstall.catalog.db.AuthorsJpaRepository;
import pl.warkoczewski.Bookstall.catalog.domain.Author;
import pl.warkoczewski.Bookstall.catalog.domain.Book;
import pl.warkoczewski.Bookstall.order.application.port.ManageOrderUseCase;
import pl.warkoczewski.Bookstall.order.application.port.QueryOrderUseCase;
import pl.warkoczewski.Bookstall.order.domain.OrderItem;
import pl.warkoczewski.Bookstall.order.domain.Recipient;

import java.math.BigDecimal;
import java.util.Set;

import static pl.warkoczewski.Bookstall.order.application.port.ManageOrderUseCase.*;

@Component
@RequiredArgsConstructor
public class ApplicationStartup implements CommandLineRunner {

    private final CatalogUseCase catalog;
    private final QueryOrderUseCase queryOrder;
    private final ManageOrderUseCase placeOrder;
    private final AuthorsJpaRepository authorRepository;

    @Override
    public void run(String... args) {
        dataInit();
        placeOrder();
    }

    private void placeOrder() {
        Book effectiveJava = catalog.findOneByTitle("Effective Java").orElseThrow(() -> new IllegalStateException("Can not find book with such title"));
        Book javaPuzzlers = catalog.findOneByTitle("Java Puzzlers").orElseThrow(() -> new IllegalStateException("Can not find book with such title"));
        Recipient recipient = Recipient.builder()
                .name("Adam")
                .phone("1111")
                .city("Poznan")
                .street("Jagielly")
                .zipCode("99-322")
                .email("adam.m@yahoo.com")
                .build();
        PlaceOrderCommand  command = PlaceOrderCommand
                .builder()
                .item(new OrderItem(effectiveJava.getId(), 16))
                .item(new OrderItem(javaPuzzlers.getId(), 7))
                .recipient(recipient)
                .build();
        PlaceOrderResponse response = placeOrder.placeOrder(command);
        System.out.println("Created order with id: " + response.getOrderId());

        queryOrder.findAll().forEach(order -> System.out.println("GOT ORDER WITH TOTAL PRICE: " + order.getTotalPrice() + " DETAILS: " + order));

    }

    private void dataInit() {
        Author joshua = new Author("Joshua", "Bloch");
        Author neal = new Author("Neal", "Gafter");
        authorRepository.save(joshua);
        authorRepository.save(neal);
        CreateBookCommand effectiveJava = new CreateBookCommand("Effective Java", Set.of(joshua.getId()), 2005, new BigDecimal(40));
        CreateBookCommand javaPuzzlers = new CreateBookCommand("Java Puzzlers", Set.of(joshua.getId(), neal.getId()), 2011, new BigDecimal(90));
        catalog.addBook(effectiveJava);
        catalog.addBook(javaPuzzlers);
    }

}
