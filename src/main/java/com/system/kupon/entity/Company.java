package com.system.kupon.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "company")
public class Company extends Client{
    private String name;
    private String imageURL;

    @JsonIgnore
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Coupon> coupons = new ArrayList<>();

    public void add(Coupon coupon) {
        coupon.setCompany((this));
        coupons.add(coupon);
    }
}
