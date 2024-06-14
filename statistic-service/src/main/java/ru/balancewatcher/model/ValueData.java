package ru.balancewatcher.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "value_data")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValueData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "value_id")
    private Long valueId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "received_time")
    private LocalDateTime receivedTime;

    @Column(name = "received_value")
    private String receivedValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "coin_name")
    private CoinName coinName;
}
