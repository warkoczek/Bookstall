package pl.warkoczewski.Bookstall.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Recipient {

    String name;
    String phone;
    String street;
    String city;
    String zipCode;
    String email;
}
