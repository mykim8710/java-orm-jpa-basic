package jpa.basic.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Member4Product {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER$_PRODUCT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER4_ID")
    private Member4 member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    private int count;
    private int price;
    private LocalDateTime orderDateTime;
}
