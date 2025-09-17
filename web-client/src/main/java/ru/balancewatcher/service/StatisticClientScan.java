//package ru.balancewatcher.service;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//import ru.balancewatcher.dto.scan.MainModel;
//import ru.balancewatcher.dto.scan.Transaction;
//
//import java.util.Comparator;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class StatisticClientScan {
//
//    private final WebClient webClient;
//
//    @Autowired
//    public StatisticClientScan(@Value("${stats-service.url1}")String url) {
//        this.webClient = WebClient.builder().baseUrl(url).build();
//    }
//
//    private Mono<MainModel> getBodyFromOctaScan(String address) {
//        return this.webClient.get().uri(uriBuilder -> uriBuilder
//                        .path(address + "/transactions")
//                        .queryParamIfPresent("filter", Optional.of("to"))
//                        .build())
//                .retrieve().bodyToMono(MainModel.class);
//    }
//
//    public List<Transaction> getTransactionsFromOctaScan(String address) {
//        return getBodyFromOctaScan(address)
//                .blockOptional()
//                .orElseThrow()
//                .getItems()
//                .stream()
//                .sorted(Comparator.comparing(Transaction::getTimestamp))
//                .toList();
//    }
//}