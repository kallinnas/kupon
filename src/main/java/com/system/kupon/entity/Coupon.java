package com.system.kupon.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

import static com.system.kupon.entity.Client.NO_ID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "coupon")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "company_id")
    @JsonIgnore
    private Company company;

    @ManyToMany(mappedBy = "coupons", cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE})
    @JsonIgnore
    private List<Customer> customers;

    private String title;
    private LocalDate startDate;
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
