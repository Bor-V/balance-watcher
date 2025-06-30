package ru.balancewatcher.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.balancewatcher.dto.explorer.MainModel;
import ru.balancewatcher.dto.explorer.Result;
import ru.balancewatcher.exception.NotFoundValidationException;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class EtcClient {

    private final WebClient webClient;

    public EtcClient(@Value("${etc.server.url}")String serverUrl) {
        this.webClient = WebClient.builder().baseUrl(serverUrl).build();
    }

    private Mono<MainModel> getBodyFromEtcExplorer(String address) {
        return this.webClient.get().uri(uriBuilder -> uriBuilder
                        .queryParamIfPresent("module", Optional.of("account"))
                        .queryParamIfPresent("action", Optional.of("txlist"))
                        .queryParamIfPresent("address", Optional.of(address))
                        .queryParamIfPresent("filter_by", Optional.of("to"))
                        .build())
                .retrieve().bodyToMono(MainModel.class);

    }

    public List<Result> getTransactionsFromEtcExplorer(String address) {
        MainModel model = getBodyFromEtcExplorer(address).blockOptional().orElseThrow();
        if (model.getMessage().equalsIgnoreCase("OK")) {
            return getBodyFromEtcExplorer(address)
                    .blockOptional()
                    .orElseThrow()
                    .getResult()
                    .stream()
                    .sorted(Comparator.comparing(Result::getTimeStamp))
                    .toList();
        } else {
            throw new NotFoundValidationException("address not found");
        }
    }


}
