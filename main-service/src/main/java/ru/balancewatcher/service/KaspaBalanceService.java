package ru.balancewatcher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.balancewatcher.config.ClientConfig;
import ru.balancewatcher.dto.ValueDataDtoResponseWithCount;
import ru.balancewatcher.dto.kaspa.Transaction;
import ru.balancewatcher.mapper.ValueDataMapper;
import ru.balancewatcher.model.CoinName;
import ru.balancewatcher.model.ValueData;
import ru.balancewatcher.repo.ValueDataRepo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service(value = "kaspa")
@RequiredArgsConstructor
public class KaspaBalanceService implements BalanceService {

    private final ValueDataMapper valueDataMapper;
    private final ValueDataRepo valueDataRepo;
    private final KaspaClient kaspaClient;
    private final ClientConfig config;

    private LocalDateTime parseLocalTimeFromSeconds(Long seconds) {
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = Instant.ofEpochMilli(seconds);
        return instant.atZone(zoneId).toLocalDateTime();
    }

//    @Override
//    public List<ValueDataDtoResponse> getValueData(String address) {
//       List<Transaction> transactions = kaspaClient.getTransactions(address);
//       if (valueDataRepo.findAllKaspaDataOrderByReceivedTime().isEmpty()) {
//           transactions.forEach(transaction -> {
//               addValueData(address, transaction);
//           });
//       } else {
//           List<String> blockHashes = valueDataRepo.getAllTransactionHash();
//           transactions.forEach(transaction ->{
//               if (!blockHashes.contains(transaction.getTransaction_id())) {
//                   addValueData(address, transaction);
//               }
//           });
//       }
//       List<ValueData> valueData = valueDataRepo.findAllKaspaDataOrderByReceivedTime();
////       convertKaspaDataToCorrectValues();
////       List<ValueData> valueDataList = new ArrayList<>();
////       transactions.forEach(transaction -> {
////           ValueData valueData = new ValueData();
////           transaction.getOutputs().stream().filter(output -> output.getScript_public_key_address().equals(address))
////                   .forEach(output -> {
////               valueData.setReceivedTime(parseLocalTimeFromSeconds(transaction.getBlock_time()));
////               valueData.setCoinName(CoinName.KASPA);
////               valueData.setBlockHash(output.getTransaction_id());
////               valueData.setReceivedValue(String.valueOf(output.getAmount()));
////               valueDataList.add(valueData);
////           });
////       });
//       return valueDataMapper.toValueDataDtoResponse(valueData);
//    }

    private void addValueData(String address, Transaction transaction) {
        ValueData valueData = new ValueData();
        transaction.getOutputs().stream().filter(output -> output.getScript_public_key_address().equals(address))
                .forEach(output -> {
                    valueData.setReceivedTime(parseLocalTimeFromSeconds(transaction.getBlock_time()));
                    valueData.setCoinName(CoinName.KASPA);
                    valueData.setTransactionHash(output.getTransaction_id());
                    valueData.setReceivedValue(String.valueOf(Double.parseDouble(String.valueOf(output.getAmount())) / 100_000_000));
                    valueDataRepo.save(valueData);
                });
    }

    @Override
    public ValueDataDtoResponseWithCount getValueDataWithCount(String address) {
        List<Transaction> transactions = kaspaClient.getTransactions(address);
        if (valueDataRepo.findAllKaspaDataOrderByReceivedTime().isEmpty()) {
            transactions.forEach(transaction -> {
                addValueData(address, transaction);
            });
        } else {
            List<String> blockHashes = valueDataRepo.getAllTransactionHash();
            transactions.forEach(transaction ->{
                if (!blockHashes.contains(transaction.getTransaction_id())) {
                    addValueData(address, transaction);
                }
            });
        }
        List<ValueData> valueData = valueDataRepo.findAllKaspaDataOrderByReceivedTime();
        Long count = valueDataRepo.countByCoinName(CoinName.KASPA);
        return valueDataMapper
                .toValueDtoResponseWithCount(valueDataMapper.toValueDataDtoResponse(valueData), count);
    }
}
