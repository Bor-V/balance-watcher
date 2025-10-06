package ru.balancewatcher.service;

import org.springframework.stereotype.Service;
import ru.balancewatcher.dto.ValueDataDtoResponse;
import ru.balancewatcher.dto.ValueDataDtoResponseWithCount;

import java.util.List;

@Service
public interface BalanceService {

//    List<ValueDataDtoResponse> getValueData(String address);

    ValueDataDtoResponseWithCount getValueDataWithCount(String address);
}
