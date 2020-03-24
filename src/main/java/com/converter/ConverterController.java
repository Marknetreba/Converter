package com.converter;


import models.ConversionRequest;
import models.ConversionResponse;
import models.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;
import repositories_entities.RatesRepo;

import javax.persistence.EntityManagerFactory;

@RestController
public class ConverterController {

    /*
    REPOSITORY WHERE WE CAN GET EXCHANGE RATES, IF ALL EXTERNAL PROVIDERS ARE DOWN.
     */
    @Autowired
    RatesRepo ratesRepo;

    /*
     EXTERNAL PROVIDERS ENDPOINTS COULD BE STORES IN H2 DB TOO.
     */
    String exchangeURI = "https://api.exchangeratesapi.io/latest?base=EUR";
    String secondExchangeURI = "https://api.exchangerate-api.com/v4/latest/EUR";

    public Currency getRates(String uri) {
        RestTemplate request = new RestTemplate();
        Currency currency;

        ResponseEntity<Currency> ans = request.getForEntity(uri, Currency.class);

        if (ans.getStatusCodeValue() == 200){
           currency = ans.getBody();
        }
        else {
            ans = request.getForEntity(secondExchangeURI, Currency.class);
            currency = ans.getBody();
        }

        return currency;
    }

    public ConversionResponse buildConversionResponse(ConversionRequest request, Currency currency) {

        double rate = currency.getRates().get(request.getTo());
        double converted = request.getAmount()*rate;

        return new ConversionResponse(request.getFrom(), request.getTo(), request.getAmount(), converted);
    }

    /*
     GET ALL EXCHANGE RATES FROM EXTERNAL PROVIDERS.
     */
    @GetMapping("/exchange_rate")
    public Mono<Currency> exchangeRate() {

        Currency response = new Currency();
        try {
            response = getRates(exchangeURI);

        } catch (Exception e) {
            System.out.println(e);
        }

        return Mono.just(response);
    }

    /*
    @PreAuthorize("hasRole('User')")
     */
    @PostMapping("/currency/convert")
    public Mono<ConversionResponse> convert(@RequestBody ConversionRequest request) {

        Currency currency = getRates(exchangeURI);
        ConversionResponse response = buildConversionResponse(request, currency);

        return Mono.just(response);
    }

}
