package pl.warkoczewski.Bookstall.order.domain;

import lombok.*;
import pl.warkoczewski.Bookstall.jpa.BaseEntity;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Recipient extends BaseEntity {

    String name;
    String phone;
    String street;
    String city;
    String zipCode;
    String email;

}
