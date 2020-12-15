package com.system.kupon.entity;

import lombok.*;
import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "company")
public class Company extends Client{
    private String name;
    private String imageURL;
}
