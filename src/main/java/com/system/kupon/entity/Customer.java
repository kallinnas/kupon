package com.system.kupon.entity;

import lombok.*;
import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer extends Client {
    private String firstName;
    private String lastName;
}
