package com.system.kupon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "company")
public class Company extends Client {

    /* provides one id arg constructor for method empty
    that used in services to return not found(non-exist) entity */
    @NonNull
    private long id;
    private String name;
    private String imageURL;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Coupon> coupons = new ArrayList<>();

    public void add(Coupon coupon) {
        coupon.setCompany((this));
        coupons.add(coupon);
    }

    public static Company empty() {
        return new Company(NO_ID);
    }

}
