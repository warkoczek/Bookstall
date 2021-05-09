package pl.warkoczewski.Bookstall;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.warkoczewski.Bookstall.catalog.application.port.CatalogUseCase;
import pl.warkoczewski.Bookstall.catalog.application.port.CatalogUseCase.UpdateBookResponse;
import pl.warkoczewski.Bookstall.catalog.domain.Book;
import pl.warkoczewski.Bookstall.order.application.port.PlaceOrderUseCase;
import pl.warkoczewski.Bookstall.order.application.port.QueryOrderUseCase;
import pl.warkoczewski.Bookstall.order.domain.OrderItem;
import pl.warkoczewski.Bookstall.order.domain.Recipient;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static pl.warkoczewski.Bookstall.order.application.port.PlaceOrderUseCase.*;

@Component
public class ApplicationStartup implements CommandLineRunner {

    private final CatalogUseCase catalog;
    private final QueryOrderUseCase queryOrder;
    private final PlaceOrderUseCase placeOrder;
    private final String title;
    private final Long limit;
    private final String author;

    public ApplicationStartup(CatalogUseCase catalog, QueryOrderUseCase queryOrder, PlaceOrderUseCase placeOrder
            , @Value("${bookstall.catalog.book.title}") String title
    , @Value("${bookstall.catalog.book.limit}") Long limit
    , @Value("${bookstall.catalog.book.author}") String author) {
        this.catalog = catalog;
        this.queryOrder = queryOrder;
        this.placeOrder = placeOrder;
        this.title = title;
        this.limit = limit;
        this.author = author;
    }

    @Override
    public void run(String... args) {
        dataInit();
        searchCatalog();
        placeOrder();
    }

    private void placeOrder() {
        Book pan_tadeusz = catalog.findOneByTitle("Pan Tadeusz").orElseThrow(() -> new IllegalStateException("Can not find book with such title"));
        Book chlopi = catalog.findOneByTitle("Chłopi").orElseThrow(() -> new IllegalStateException("Can not find book with such title"));
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
                .item(new OrderItem(pan_tadeusz, 16))
                .item(new OrderItem(chlopi, 7))
                .recipient(recipient)
                .build();
        PlaceOrderResponse response = placeOrder.placeOrder(command);
        System.out.println("Created order with id: " + response.getOrderId());

        queryOrder.findAll().forEach(order -> {
            System.out.println("GOT ORDER WITH TOTAL PRICE: " + order.totalPrice() + " DETAILS: " + order);
        });

    }

    private void searchCatalog() {
        findByTitle();
        findAndUpdate();
        findByTitle();
    }

    private void dataInit() {

        catalog.addBook( new CatalogUseCase.CreateBookCommand( "Pan Tadeusz", "Adam Mickiewicz", 1834, new BigDecimal(2)));
        catalog.addBook( new CatalogUseCase.CreateBookCommand( "Ogniem i mieczem", "Henryk Sienkiewicz", 1884, new BigDecimal(23)));
        catalog.addBook( new CatalogUseCase.CreateBookCommand( "Chłopi", "Władysław Reymont", 1904, new BigDecimal(45)));
        catalog.addBook( new CatalogUseCase.CreateBookCommand( "Pan Wołodyjowski", "Henryk Sienkiewicz", 1899, new BigDecimal(55)));
    }

    private void findByTitle() {
        List<Book> byTitle = catalog.findByTitle(title);
        byTitle.forEach(System.out::println);
    }

    private void findAndUpdate() {
        catalog.findOneByTitleAndAuthor("Pan Tadeusz", "Adam Mickiewicz")
                .ifPresent(book -> {
                    CatalogUseCase.UpdateBookCommand command = CatalogUseCase.UpdateBookCommand.builder()
                            .id(book.getId())
                            .title("Pan Tadeusz, czyli ostatni zajazd na Litwie")
                            .build();
                    UpdateBookResponse updateBookResponse = catalog.updateBook(command);
                    System.out.println("Updating book result: " + updateBookResponse.isSuccess());
                });
    }
}
