package com.mjc.school.repository.model.impl;

import lombok.Data;

import javax.persistence.*;
@Entity
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
}
