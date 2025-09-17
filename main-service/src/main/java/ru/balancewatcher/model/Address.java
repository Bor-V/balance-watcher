package ru.balancewatcher.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long address_id;

    @Column(name = "address_name")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "coin_name")
    private CoinName coinName;
}
