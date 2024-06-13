package ru.tokenwoken.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tokenwoken.dto.ValueDataDtoResponse;
import ru.tokenwoken.model.ValueData;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ValueDataMapper {

    @Mapping(target = "coinName", source = "coinName")
    List<ValueDataDtoResponse> toValueDataDtoResponse(List<ValueData> valueDataList);
}
