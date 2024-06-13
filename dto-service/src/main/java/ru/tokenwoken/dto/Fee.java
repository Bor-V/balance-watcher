package ru.tokenwoken.dto;


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
