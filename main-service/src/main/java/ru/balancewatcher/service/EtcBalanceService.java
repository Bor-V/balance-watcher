package ru.balancewatcher.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.balancewatcher.dto.ValueDataDtoResponseWithCount;
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

//    @Override
//    public List<ValueDataDtoResponse> getValueData(String address) {
//        List<Result> results = etcClient.getTransactionsFromEtcExplorer(address);
//        if (valueDataRepo.findAllEtcDataOrderByReceivedTime().isEmpty()) {
//            results.forEach(result -> {
//                ValueData valueData = new ValueData();
//                valueData.setTransactionHash(result.getHash());
//                valueData.setCoinName(CoinName.ETC);
////                valueData.setReceivedValue(result.getValue());
//                valueData.setReceivedValue(String.valueOf(BigDecimal.valueOf(Long.parseLong(result.getValue())).movePointLeft(18)));
//                valueData.setReceivedTime(parseLocalDateTimeFromSeconds(result));
//                valueDataRepo.save(valueData);
//            });
//        } else {
//            List<String> blockHashes = valueDataRepo.getAllTransactionHash();
//            results.forEach(result -> {
//                if (!blockHashes.contains(result.getHash())) {
//                    ValueData valueData = new ValueData();
//                    valueData.setTransactionHash(result.getHash());
//                    valueData.setCoinName(CoinName.ETC);
////                    valueData.setReceivedValue(result.getValue());
//                    valueData.setReceivedValue(String.valueOf(BigDecimal.valueOf(Long.parseLong(result.getValue())).movePointLeft(18)));
//                    valueData.setReceivedTime(parseLocalDateTimeFromSeconds(result));
//                    valueDataRepo.save(valueData);
//                }
//            });
//        }
//        List<ValueData> valueData = valueDataRepo.findAllEtcDataOrderByReceivedTime();
//        return valueDataMapper.toValueDataDtoResponse(valueData);
//    }

    @Override
    public ValueDataDtoResponseWithCount getValueDataWithCount(String address) {
        List<Result> results = etcClient.getTransactionsFromEtcExplorer(address);
        if (valueDataRepo.findAllEtcDataOrderByReceivedTime().isEmpty()) {
            results.forEach(this::addValueData);
        } else {
            List<String> blockHashes = valueDataRepo.getAllTransactionHash();
            results.forEach(result -> {
                if (!blockHashes.contains(result.getHash())) {
                    addValueData(result);
                }
            });
        }
        List<ValueData> valueData = valueDataRepo.findAllEtcDataOrderByReceivedTime();
        Long count = valueDataRepo.countByCoinName(CoinName.ETC);
        return valueDataMapper
                .toValueDtoResponseWithCount(valueDataMapper.toValueDataDtoResponse(valueData), count);
    }

    private void addValueData(Result result) {
        ValueData valueData = new ValueData();
        valueData.setTransactionHash(result.getHash());
        valueData.setCoinName(CoinName.ETC);
        valueData.setReceivedValue(String.valueOf(new BigDecimal(result.getValue()).movePointLeft(18)));
        valueData.setReceivedTime(parseLocalDateTimeFromSeconds(result));
        valueDataRepo.save(valueData);
    }
}
