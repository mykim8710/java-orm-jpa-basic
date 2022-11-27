package jpa.basic.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Member5 {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER5_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String name;

//    private LocalDateTime startDate;
//    private LocalDateTime endDate;
    @Embedded
    private Period workPeriod;

//    private String city;
//    private String street;
//    private String zipCode;

    // 주소1
    @Embedded
    private Address homeAddress;

    // 주소2
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "city", column = @Column(name = "WORK_CITY")),
                         @AttributeOverride(name = "street", column = @Column(name = "WORK_STREET")),
                         @AttributeOverride(name = "zipCode", column = @Column(name = "WORK_ZIPCODE"))})
    private Address workAddress;
}
