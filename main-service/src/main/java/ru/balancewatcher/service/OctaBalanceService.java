package ru.balancewatcher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.balancewatcher.dto.ValueDataDtoResponse;
import ru.balancewatcher.dto.explorer.Result;
import ru.balancewatcher.mapper.ValueDataMapper;
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
public class OctaBalanceService implements BalanceService {

    private final ValueDataRepo valueDataRepo;
    private final ValueDataMapper valueDataMapper;
    private final OctaClient octaClient;
    private final AddressRepo addressRepo;

    private LocalDateTime parseLocalDateTimeFromSeconds(Result result) {
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = Instant.ofEpochSecond(Long.parseLong(result.getTimeStamp()));
        return instant.atZone(zoneId).toLocalDateTime();
    }

    @Override
    public List<ValueDataDtoResponse> getValueData(String address) {
        List<Result> results = octaClient.getTransactionsFromOctaExplorer(address);
        if (valueDataRepo.findAllOctaDataOrderByReceivedTime().isEmpty()) {
            results.forEach(result -> {
                ValueData valueData = new ValueData();
                valueData.setBlockHash(result.getBlockHash());
                valueData.setCoinName(CoinName.OCTA);
                valueData.setReceivedValue(result.getValue());
                valueData.setReceivedTime(parseLocalDateTimeFromSeconds(result));
                valueDataRepo.save(valueData);
            });
        } else {
            List<String> blockHashes = valueDataRepo.getAllBlockHash();
            results.forEach(result -> {
                if (!blockHashes.contains(result.getBlockHash())) {
                    ValueData valueData = new ValueData();
                    valueData.setBlockHash(result.getBlockHash());
                    valueData.setCoinName(CoinName.OCTA);
                    valueData.setReceivedValue(result.getValue());
                    valueData.setReceivedTime(parseLocalDateTimeFromSeconds(result));
                    valueDataRepo.save(valueData);
                }
            });
        }
        List<ValueData> valueData = valueDataRepo.findAllOctaDataOrderByReceivedTime();
        return valueDataMapper.toValueDataDtoResponse(valueData);
    }
}
