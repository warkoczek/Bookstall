package pl.warkoczewski.Bookstall.order.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Recipient {

    String name;
    String phone;
    String street;
    String city;
    String zipCode;
    String email;
}
