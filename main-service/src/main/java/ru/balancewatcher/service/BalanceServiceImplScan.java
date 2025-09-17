//package ru.balancewatcher.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import ru.balancewatcher.dto.ValueDataDtoResponse;
//import ru.balancewatcher.dto.scan.Transaction;
//import ru.balancewatcher.mapper.ValueDataMapper;
//import ru.balancewatcher.model.CoinName;
//import ru.balancewatcher.model.ValueData;
//import ru.balancewatcher.repo.ValueDataRepo;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service(value = "scan")
//@RequiredArgsConstructor
//public class BalanceServiceImplScan implements BalanceService {
//
//    private final ValueDataRepo valueDataRepo;
//    private final ValueDataMapper valueDataMapper;
//    private final StatisticClientScan statisticClientScan;
//
//    private LocalDateTime parseLocalDateTimeFromStringWithZ(Transaction transaction) {
//        return LocalDateTime.parse(transaction.getTimestamp().replace("Z", ""));
//    }
//
//    private String parseLocalDateTimeWithZ(Transaction transaction) {
//        return LocalDateTime.parse(transaction.getTimestamp()
//                .replace("Z", "")).plusHours(3).toString();
//    }
//
//    @Override
//    public List<ValueDataDtoResponse> getValueData(String address) {
//        List<Transaction> transactions = statisticClientScan.getTransactionsFromOctaScan(address);
//        transactions.forEach(transaction ->
//                transaction.setTimestamp(parseLocalDateTimeWithZ(transaction)));
//        if (valueDataRepo.findAllOrderByReceived().isEmpty()) {
//            transactions.forEach(transaction -> {
//                ValueData valueData = new ValueData();
//                valueData.setCoinName(CoinName.OCTA);
//                valueData.setReceivedValue(transaction.getValue());
//                valueData.setReceivedTime(parseLocalDateTimeFromStringWithZ(transaction));
//                valueDataRepo.save(valueData);
//            });
//        } else {
//            List<LocalDateTime> receivedTimes = valueDataRepo.getAllTimeStamps();
//            transactions.forEach(transaction -> {
//                if (!receivedTimes.contains(parseLocalDateTimeFromStringWithZ(transaction))) {
//                    ValueData valueData = new ValueData();
//                    valueData.setCoinName(CoinName.OCTA);
//                    valueData.setReceivedValue(transaction.getValue());
//                    valueData.setReceivedTime(parseLocalDateTimeFromStringWithZ(transaction));
//                    valueDataRepo.save(valueData);
//                }
//            });
//        }
//        List<ValueData> valueData = valueDataRepo.findAllOrderByReceived();
//        return valueDataMapper.toValueDataDtoResponse(valueData);
//    }
//
//    @Override
//    public String checkAddress(String address) {
//        return null;
//    }
//}
