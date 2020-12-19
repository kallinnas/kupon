package com.system.kupon.entity;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "customer")
public class Customer extends Client {
    private String firstName;
    private String lastName;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "customer_coupon",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id"))
    @NonNull
    private List<Coupon> coupons;

    public Customer(long id) {
        setId(id);
    }

    public static Customer empty() {
        return new Customer(NO_ID);
    }

}
