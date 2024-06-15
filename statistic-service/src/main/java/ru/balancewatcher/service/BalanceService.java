package ru.balancewatcher.service;

import org.springframework.stereotype.Service;
import ru.balancewatcher.dto.AddressDtoResponse;
import ru.balancewatcher.dto.ValueDataDtoResponse;

import java.util.List;

@Service
public interface BalanceService {

    List<ValueDataDtoResponse> getValueData(String address);

    String checkAddress(String address);
}
