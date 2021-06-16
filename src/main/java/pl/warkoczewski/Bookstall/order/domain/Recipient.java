package pl.warkoczewski.Bookstall.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.warkoczewski.Bookstall.jpa.BaseEntity;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Recipient extends BaseEntity {

    @Id
    @GeneratedValue
    Long id;
    String name;
    String phone;
    String street;
    String city;
    String zipCode;
    String email;

    public Recipient(String name, String phone, String street, String city, String zipCode, String email) {
        this.name = name;
        this.phone = phone;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.email = email;
    }
}
