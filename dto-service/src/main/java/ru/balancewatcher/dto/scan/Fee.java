package ru.balancewatcher.dto.scan;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Fee {

    private String type;

    private String value;

}
