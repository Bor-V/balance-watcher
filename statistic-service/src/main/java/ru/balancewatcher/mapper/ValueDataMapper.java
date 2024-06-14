package ru.balancewatcher.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.balancewatcher.dto.ValueDataDtoResponse;
import ru.balancewatcher.model.ValueData;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ValueDataMapper {

    @Mapping(target = "coinName", source = "coinName")
    List<ValueDataDtoResponse> toValueDataDtoResponse(List<ValueData> valueDataList);
}
