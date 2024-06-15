package ru.balancewatcher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.balancewatcher.dto.AddressDtoResponse;
import ru.balancewatcher.dto.ValueDataDtoResponse;
import ru.balancewatcher.dto.explorer.Balance;
import ru.balancewatcher.dto.explorer.Result;
import ru.balancewatcher.exception.NotFoundValidationException;
import ru.balancewatcher.mapper.ValueDataMapper;
import ru.balancewatcher.model.Address;
import ru.balancewatcher.model.CoinName;
import ru.balancewatcher.model.ValueData;
import ru.balancewatcher.repo.AddressRepo;
import ru.balancewatcher.repo.ValueDataRepo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service(value = "explorer")
@RequiredArgsConstructor
public class BalanceServiceImplExplorer implements BalanceService {

    private final ValueDataRepo valueDataRepo;
    private final ValueDataMapper valueDataMapper;
    private final StatisticClientExplorer statisticClientExplorer;
    private final AddressRepo addressRepo;

    private LocalDateTime parseLocalDateTimeFromSeconds(Result result) {
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = Instant.ofEpochSecond(Long.parseLong(result.getTimeStamp()));
        return instant.atZone(zoneId).toLocalDateTime();
    }

    @Override
    public List<ValueDataDtoResponse> getValueData(String address) {
        List<Result> results = statisticClientExplorer.getTransactionsFromOctaExplorer(address);
        if (valueDataRepo.findAllOrderByReceived().isEmpty()) {
            results.forEach(result -> {
                ValueData valueData = new ValueData();
                valueData.setCoinName(CoinName.OCTA);
                valueData.setReceivedValue(result.getValue());
                valueData.setReceivedTime(parseLocalDateTimeFromSeconds(result));
                valueDataRepo.save(valueData);
            });
        } else {
            List<LocalDateTime> receivedTimes = valueDataRepo.getAllTimeStamps();
            results.forEach(result -> {
                if (!receivedTimes.contains(parseLocalDateTimeFromSeconds(result))) {
                    ValueData valueData = new ValueData();
                    valueData.setCoinName(CoinName.OCTA);
                    valueData.setReceivedValue(result.getValue());
                    valueData.setReceivedTime(parseLocalDateTimeFromSeconds(result));
                    valueDataRepo.save(valueData);
                }
            });
        }
        List<ValueData> valueData = valueDataRepo.findAllOrderByReceived();
        return valueDataMapper.toValueDataDtoResponse(valueData);
    }

    @Override
    public String checkAddress(String address) {
        Balance balance = statisticClientExplorer.getBalance(address);
        if (balance.getMessage().equals("OK")) {
            Address address1 = new Address();
            address1.setAddress(address);
            address1.setCoinName(CoinName.OCTA);
            addressRepo.save(address1);
            return "address save";
        } else {
            throw new NotFoundValidationException("address not found");
        }
    }
}
