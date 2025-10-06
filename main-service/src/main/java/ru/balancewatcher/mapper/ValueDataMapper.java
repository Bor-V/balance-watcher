package ru.balancewatcher.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.balancewatcher.dto.ValueDataDtoResponse;
import ru.balancewatcher.dto.ValueDataDtoResponseWithCount;
import ru.balancewatcher.model.ValueData;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ValueDataMapper {

    @Mapping(target = "coinName", source = "coinName")
    List<ValueDataDtoResponse> toValueDataDtoResponse(List<ValueData> valueDataList);

    default ValueDataDtoResponseWithCount toValueDtoResponseWithCount(List<ValueDataDtoResponse> valueDataDtoResponseList, Long count) {
        ValueDataDtoResponseWithCount valueDataDtoResponseWithCount = new ValueDataDtoResponseWithCount();
        valueDataDtoResponseWithCount.setValueDataDtoResponseList(valueDataDtoResponseList);
        valueDataDtoResponseWithCount.setCount(count);
        return valueDataDtoResponseWithCount;
    }
}
