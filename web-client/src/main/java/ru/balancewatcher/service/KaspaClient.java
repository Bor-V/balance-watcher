package ru.balancewatcher.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Flux;
import ru.balancewatcher.dto.kaspa.Transaction;
import java.util.*;

@Component
public class KaspaClient {

    private final WebClient webClient;

    public KaspaClient(@Value("${kaspa.server.url}") String serverUrl) {
        this.webClient = WebClient.builder().baseUrl(serverUrl).build();
    }

    public Flux<Transaction> getBodyFromServerResponse(String address) {
        try {
            return this.webClient.get().uri(uriBuilder -> uriBuilder
                    .path(address + "/full-transactions")
                    .queryParamIfPresent("limit", Optional.of("50"))
                    .queryParamIfPresent("offset", Optional.of("0"))
                    .queryParamIfPresent("resolve_previous_outpoints", Optional.of("no"))
                    .build()).retrieve().bodyToFlux(Transaction.class);
        } catch (Exception e) {
            return Flux.error(e);
        }
    }

    public List<Transaction> getTransactions(String address) throws WebClientRequestException {
        return getBodyFromServerResponse(address).toStream().toList();
    }
}
