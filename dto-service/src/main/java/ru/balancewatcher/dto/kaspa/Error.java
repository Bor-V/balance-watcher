package ru.balancewatcher.dto.kaspa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error {

    private List<Detail> detail;
}
