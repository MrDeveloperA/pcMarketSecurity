package com.example.pcmarketsecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class PcMarket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @ManyToOne
    private Product product;
    @ManyToOne
    private Review review;
    @ManyToOne
    private Partner partner;
    @ManyToOne
    private Brand brand;
}
