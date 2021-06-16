package pl.warkoczewski.Bookstall.jpa;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode
public class BaseEntity {

    private final String uuid = UUID.randomUUID().toString();
}
