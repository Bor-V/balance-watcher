package ru.balancewatcher.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "value_data")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValueData {

    @Id
    @UuidGenerator
    @Column(name = "value_id")
    private String valueId;

    @Column(name = "block_hash")
    private String blockHash;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "received_time")
    private LocalDateTime receivedTime;

    @Column(name = "received_value")
    private String receivedValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "coin_name")
    private CoinName coinName;
}
