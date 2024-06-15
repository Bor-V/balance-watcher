package ru.balancewatcher.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDtoResponse {

    private Long address_id;
    private String address_name;
    private String coin_name;
}
