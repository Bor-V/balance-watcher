package ru.tokenwoken.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tokenwoken.dto.Transaction;
import ru.tokenwoken.dto.ValueDataDtoResponse;
import ru.tokenwoken.mapper.ValueDataMapper;
import ru.tokenwoken.model.CoinName;
import ru.tokenwoken.model.ValueData;
import ru.tokenwoken.repo.ValueDataRepo;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final StatisticClient statisticClient;
    
    private final ValueDataRepo valueDataRepo;

    private final ValueDataMapper valueDataMapper;



    public List<ValueDataDtoResponse> getValueData(String address) {
        List<Transaction> transactions = statisticClient.getTransactions(address);
        transactions.forEach(transaction ->
                        transaction.setTimestamp(LocalDateTime.parse(transaction
                                        .getTimestamp().replace("Z", ""))
                                .plusHours(3).toString()));
        if (valueDataRepo.findAllOrderByReceived().isEmpty()) {
        transactions.forEach(transaction -> {
            ValueData valueData = new ValueData();
            valueData.setCoinName(CoinName.OCTA);
            valueData.setReceivedValue(transaction.getValue());
            valueData.setReceivedTime(LocalDateTime.parse(transaction.getTimestamp().replace("Z", "")));
            valueDataRepo.save(valueData);
        });
        } else {
            List<LocalDateTime> receivedTimes = valueDataRepo.getAllTimeStamps();
            transactions.forEach(transaction -> {
                if (!receivedTimes.contains(LocalDateTime.parse(transaction.getTimestamp().replace("Z", "")))) {
                    ValueData valueData = new ValueData();
                    valueData.setCoinName(CoinName.OCTA);
                    valueData.setReceivedValue(transaction.getValue());
                    valueData.setReceivedTime(LocalDateTime.parse(transaction.getTimestamp().replace("Z", "")));
                    valueDataRepo.save(valueData);
                }
            });
        }
        List<ValueData> valueData = valueDataRepo.findAllOrderByReceived();
        return valueDataMapper.toValueDataDtoResponse(valueData);
    }
}
