package com.converter;

import models.Currency;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ConverterWebClient {

    WebClient client = WebClient.create("http://localhost:8080");

    Mono<Currency> exchange = client
            .get()
            .uri("/exchange_rate")
            .retrieve()
            .bodyToMono(Currency.class);

    public Mono<Currency> getExchange() {
        return exchange;
    }

}
