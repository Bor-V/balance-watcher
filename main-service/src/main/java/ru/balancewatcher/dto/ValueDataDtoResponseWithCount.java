package ru.balancewatcher.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValueDataDtoResponseWithCount {

   private List<ValueDataDtoResponse> valueDataDtoResponseList;
   private Long count;
}
