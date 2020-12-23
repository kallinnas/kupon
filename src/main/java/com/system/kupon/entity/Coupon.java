package com.system.kupon.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.system.kupon.config.LocalDateToSQLDateAttributeConverter;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

import static com.system.kupon.entity.Client.NO_ID;

@Data
@Entity
@NoArgsConstructor // allows return body of coupon
@Table(name = "coupon")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "company_id")
    @JsonIgnore
    @NonNull
    private Company company;

    @ManyToMany(mappedBy = "coupons", cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE})
    @JsonIgnore
    private List<Customer> customers = new ArrayList<>();
    /*insert into customer_coupon (customer_id, coupon_id) values (3, 1)*/

    private String title;
    @Convert(converter = LocalDateToSQLDateAttributeConverter.class)
    private LocalDate startDate;
    @Convert(converter = LocalDateToSQLDateAttributeConverter.class)
    private LocalDate endDate;
    private int category;
    private int amount;
    private double price;
    private String description;
    private String imageURL;

    public Coupon(long id) {
        this.id = id;
    }


    public static Coupon empty() {
        return new Coupon(NO_ID);
    }


}
