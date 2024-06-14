package ru.balancewatcher.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.balancewatcher.dto.explorer.MainModel;
import ru.balancewatcher.dto.explorer.Result;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class StatisticClientExplorer {

    private final WebClient webClient;

    public StatisticClientExplorer(@Value("${stats-service.url2}")String serverUrl) {
        this.webClient = WebClient.builder().baseUrl(serverUrl).build();
    }

    private Mono<MainModel> getBodyFromOctaExplorer(String address) {
        return this.webClient.get().uri(uriBuilder -> uriBuilder
                        .queryParamIfPresent("module", Optional.of("account"))
                        .queryParamIfPresent("action", Optional.of("txlist"))
                        .queryParamIfPresent("address", Optional.of(address))
                        .queryParamIfPresent("filter_by", Optional.of("to"))
                        .build())
                .retrieve().bodyToMono(MainModel.class);

    }

    public List<Result> getTransactionsFromOctaExplorer(String address) {
        return getBodyFromOctaExplorer(address)
                .blockOptional()
                .orElseThrow()
                .getResult()
                .stream()
                .sorted(Comparator.comparing(Result::getTimeStamp))
                .toList();
    }
}
