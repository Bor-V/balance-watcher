package ru.balancewatcher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.balancewatcher.dto.ValueDataDtoResponse;
import ru.balancewatcher.dto.kaspa.Transaction;
import ru.balancewatcher.mapper.ValueDataMapper;
import ru.balancewatcher.model.CoinName;
import ru.balancewatcher.model.ValueData;
import ru.balancewatcher.repo.ValueDataRepo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service(value = "kaspa")
@RequiredArgsConstructor
public class KaspaBalanceService implements BalanceService {

    private final ValueDataMapper valueDataMapper;
    private final ValueDataRepo valueDataRepo;
    private final KaspaClient kaspaClient;

    private LocalDateTime parseLocalTimeFromSeconds(Long seconds) {
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = Instant.ofEpochMilli(seconds);
        return instant.atZone(zoneId).toLocalDateTime();
    }


    @Override
    public List<ValueDataDtoResponse> getValueData(String address) {
       List<Transaction> transactions = kaspaClient.getTransactions(address);
       List<ValueData> valueDataList = new ArrayList<>();
       transactions.forEach(transaction -> {
           ValueData valueData = new ValueData();
           transaction.getOutputs().stream().filter(output -> output.getScript_public_key_address().equals(address))
                   .forEach(output -> {
               valueData.setReceivedTime(parseLocalTimeFromSeconds(transaction.getBlock_time()));
               valueData.setCoinName(CoinName.KASPA);
               valueData.setReceivedValue(String.valueOf(output.getAmount()));
               valueDataList.add(valueData);
           });
       });
        return valueDataMapper.toValueDataDtoResponse(valueDataList);
    }

    @Override
    public String checkAddress(String address) {
        return "";
    }
}
