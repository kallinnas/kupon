package com.system.kupon.entity;

import lombok.*;
import javax.persistence.*;

@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = false)
public abstract class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
