package com.system.kupon.entity;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "company")
public class Company extends Client{
    private String name;
    private String imageURL;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Coupon> coupons;

    public void add(Coupon coupon) {
        coupon.setCompany((this));
        coupons.add(coupon);
    }
}
