package com.system.kupon.entity;

import lombok.*;
import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
public abstract class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    public static final int NO_ID = -1;
}
