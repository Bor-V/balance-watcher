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
public class DogeClient {

    private final WebClient webClient;

    public DogeClient(@Value("${doge.server.url}")String serverUrl) {
        this.webClient = WebClient.builder().baseUrl(serverUrl).build();
    }

    private Mono<MainModel> getBodyFromDogeExplorer(String address) {
        return this.webClient.get().uri(uriBuilder -> uriBuilder
                        .queryParamIfPresent("module", Optional.of("account"))
                        .queryParamIfPresent("action", Optional.of("txlist"))
                        .queryParamIfPresent("address", Optional.of(address))
                        .queryParamIfPresent("filter_by", Optional.of("to"))
                        .build())
                .retrieve().bodyToMono(MainModel.class);
    }

    public List<Result> getTransactionsFromOctaExplorer(String address) {
        MainModel model = getBodyFromDogeExplorer(address).blockOptional().orElseThrow();
        if (model.getMessage().equalsIgnoreCase("OK")) {
            return getBodyFromDogeExplorer(address)
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
