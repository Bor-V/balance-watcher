package ru.balancewatcher.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.balancewatcher.dto.ValueDataDtoResponse;
import ru.balancewatcher.dto.explorer.Result;
import ru.balancewatcher.mapper.ValueDataMapper;
import ru.balancewatcher.model.CoinName;
import ru.balancewatcher.model.ValueData;
import ru.balancewatcher.repo.ValueDataRepo;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service(value = "etc")
@RequiredArgsConstructor
public class EtcBalanceService implements BalanceService {

    private final ValueDataRepo valueDataRepo;
    private final ValueDataMapper valueDataMapper;
    private final EtcClient etcClient;


    private LocalDateTime parseLocalDateTimeFromSeconds(Result result) {
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = Instant.ofEpochSecond(Long.parseLong(result.getTimeStamp()));
        return instant.atZone(zoneId).toLocalDateTime();
    }

    @Override
    public List<ValueDataDtoResponse> getValueData(String address) {
        List<Result> results = etcClient.getTransactionsFromEtcExplorer(address);
        if (valueDataRepo.findAllEtcDataOrderByReceivedTime().isEmpty()) {
            results.forEach(result -> {
                ValueData valueData = new ValueData();
                valueData.setBlockHash(result.getHash());
                valueData.setCoinName(CoinName.ETC);
//                valueData.setReceivedValue(result.getValue());
                valueData.setReceivedValue(String.valueOf(BigDecimal.valueOf(Long.parseLong(result.getValue())).movePointLeft(18)));
                valueData.setReceivedTime(parseLocalDateTimeFromSeconds(result));
                valueDataRepo.save(valueData);
            });
        } else {
            List<String> blockHashes = valueDataRepo.getAllBlockHash();
            results.forEach(result -> {
                if (!blockHashes.contains(result.getHash())) {
                    ValueData valueData = new ValueData();
                    valueData.setBlockHash(result.getHash());
                    valueData.setCoinName(CoinName.ETC);
//                    valueData.setReceivedValue(result.getValue());
                    valueData.setReceivedValue(String.valueOf(BigDecimal.valueOf(Long.parseLong(result.getValue())).movePointLeft(18)));
                    valueData.setReceivedTime(parseLocalDateTimeFromSeconds(result));
                    valueDataRepo.save(valueData);
                }
            });
        }
        List<ValueData> valueData = valueDataRepo.findAllEtcDataOrderByReceivedTime();
        return valueDataMapper.toValueDataDtoResponse(valueData);
    }
}
