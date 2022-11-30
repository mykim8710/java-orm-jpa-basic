package jpa.basic.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter @Setter
@Entity
@Table(name = "ADDRESS2")
public class AddressEntity {
    @Id @GeneratedValue
    @Column(name = "ADDRESS2_ID")
    private Long id;

    private Address2 address;

    public AddressEntity() {
    }

    public AddressEntity(String city, String street, String zipcode) {
        this.address = new Address2(city, street, zipcode);
    }
}
