import com.converter.ConverterApplication;
import com.converter.ConverterController;
import models.ConversionRequest;
import models.ConversionResponse;
import models.Currency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ConverterController.class)
@ContextConfiguration(classes = ConverterApplication.class)

public class ConverterControllerTest {

    @Autowired
    private WebTestClient webClient;


    @Test
    void testConversion() {
        ConversionRequest request = new ConversionRequest("EUR","USD", 3.14);

        webClient
                .post()
                .uri("/currency/convert")
                .body(Mono.just(request), ConversionRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ConversionResponse.class);
    }

    @Test
    void testGetExchangeRates() {

        webClient
                .get()
                .uri("/exchange_rate")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Currency.class);
    }

}
