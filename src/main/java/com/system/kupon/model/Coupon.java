package com.system.kupon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.system.kupon.config.LocalDateToSQLDateAttributeConverter;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder // used in services to create and update coupon
@AllArgsConstructor // builder will works only with this constructor
@RequiredArgsConstructor // constructor with id
@NoArgsConstructor // allows return body of coupon
@Table(name = "coupon")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private long id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "company_id")
    @JsonIgnore
    @ToString.Exclude
    @Setter
    private Company company;

    @ManyToMany(mappedBy = "coupons", cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE})
    @JsonIgnore
    @ToString.Exclude
    private List<Customer> customers = new ArrayList<>();

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

    public static Coupon empty() {
        return new Coupon(-1);
    }

    public boolean similarCoupon(Coupon coupon) {
        if (this.getTitle().equals(coupon.getTitle())
                && this.getCategory() == coupon.getCategory()
                && this.getPrice() == coupon.getPrice()
                && this.getDescription().equals(coupon.getDescription())
                && this.getImageURL().equals(coupon.getImageURL())) {
            return true;
        }
        return false;
    }

    void addCustomer(Customer customer) {
        if (!customers.contains(customer)) customers.add(customer);
    }
}
