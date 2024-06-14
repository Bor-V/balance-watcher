package ru.balancewatcher.dto.scan;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class NextPageParams {

    private Double block_number;

    private String fee;

    private String hash;

    private Double index;

    private String inserted_at;

    private Double items_count;

    private String value;
}
