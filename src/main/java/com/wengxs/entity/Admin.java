package com.wengxs.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Zorg
 * 2020-02-21 12:08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_admin")
public class Admin extends BaseEntity<Long> {

    @Column(unique = true, nullable = false, updatable = false, length = 32)
    private String username;

    @Column(nullable = false, length = 128)
    private String password;

}
