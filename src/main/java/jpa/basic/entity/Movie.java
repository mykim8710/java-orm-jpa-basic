package jpa.basic.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter @Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("Movie")
public class Movie extends Item {
    private String director;
    private String actor;
}
