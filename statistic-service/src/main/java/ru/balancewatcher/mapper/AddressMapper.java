package ru.balancewatcher.mapper;


import org.mapstruct.Mapper;
import ru.balancewatcher.dto.AddressDtoResponse;
import ru.balancewatcher.model.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressDtoResponse toAddressDtoResponse(Address address);
}
