package jpa.basic.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter @Setter
@Entity
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "PRODUCT_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

//    @ManyToMany(mappedBy = "products")
//    private List<Member4> members = new ArrayList<>();
    @OneToMany(mappedBy = "product")
    private List<Member4Product> memberProducts = new ArrayList<>();
}
