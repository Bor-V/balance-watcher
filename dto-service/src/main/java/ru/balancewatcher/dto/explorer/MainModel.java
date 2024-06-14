package ru.balancewatcher.dto.explorer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainModel {

    private String message;

    private List<Result> result;

    private String status;
}
