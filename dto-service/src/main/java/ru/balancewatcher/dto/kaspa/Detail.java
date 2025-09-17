package ru.balancewatcher.dto.kaspa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Detail {

    private String msg;
    private String type;
    private List<Object> loc;
}
