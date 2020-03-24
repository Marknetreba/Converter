package utils;

import com.converter.ConverterWebClient;
import models.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import reactor.core.publisher.Mono;
import repositories_entities.Rates;
import repositories_entities.RatesRepo;

import java.time.Instant;

/*
SCHEDULED TASK THAT COULD BE USED TO SAVE RATES IN DB.
WE DON'T WANT TO SET EXPLICIT ENDPOINTS FOR GETTING AND SAVING RATES IN DB, BECAUSE THIS WILL MAKE OUR DB VULNERABLE.
 */
@EnableAsync
public class SchedulerCacheDB {
    @Autowired
    RatesRepo ratesRepo;

    @Async
    @Scheduled(fixedRate = 1000)
    public void scheduleFixedRateTaskAsync() throws InterruptedException {
        ConverterWebClient webClient = new ConverterWebClient();
        Mono<Currency> currencyMono = webClient.getExchange();

        currencyMono.subscribe(this::cacheDailyRate);
    }

    /*
    TEST METHOD FOR CACHING EXCHANGE RATES, IN CASE WE WON'T BE ABLE TO GET THEN VIA NET.
     */
    public void cacheDailyRate(Currency currency) {
        Rates rates = new Rates();
        rates.setDate(String.valueOf(Instant.now()));
        rates.setRates(String.valueOf(currency.getRates()));

        ratesRepo.save(rates);
    }

}
