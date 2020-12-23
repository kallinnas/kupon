package com.system.kupon.entity;

import lombok.*;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String password;

    @Any(metaColumn = @Column(name = "role"))
    @AnyMetaDef(idType = "long", metaType = "int",
            metaValues = {
                    @MetaValue(value = "1", targetEntity = Customer.class),
                    @MetaValue(value = "2", targetEntity = Company.class),
            })
    @JoinColumn(name = "client_id")
    private Client client;

    public User(String email, String password, int role) {
        this.id = 0;
        this.email = email;
        this.password = password;
        if (role == 1) {
            this.client = new Customer();
            this.client.setId(0);
        } else if (role == 2) {
            this.client = new Company();
            this.client.setId(0);
        }
    }

}
