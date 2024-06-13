package ru.tokenwoken.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tokenwoken.dto.MainModel;
import ru.tokenwoken.dto.Transaction;


import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class StatisticClient {

    private final WebClient webClient;

    @Autowired
    public StatisticClient(@Value("${stats-service.url}")String url) {
        this.webClient = WebClient.builder().baseUrl(url).build();
    }

    private Mono<MainModel> getBody(String address) {
        return this.webClient.get().uri(uriBuilder -> uriBuilder
                        .path(address + "/transactions")
                        .queryParamIfPresent("filter", Optional.of("to"))
                        .build())
                .retrieve().bodyToMono(MainModel.class);
    }

    public List<Transaction> getTransactions(String address) {
        return getBody(address)
                .blockOptional()
                .orElseThrow()
                .getItems()
                .stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp))
                .toList();
    }
}