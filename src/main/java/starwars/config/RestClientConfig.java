package starwars.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    private final String swapiUrl;
    private final int connectTimeout;
    private final int readTimeout;

    public RestClientConfig(
            @Value("${swapi.url}") String swapiUrl,
            @Value("${swapi.connect-timeout}") int connectTimeout,
            @Value("${swapi.read-timeout}") int readTimeout) {
        this.swapiUrl = swapiUrl;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    @Bean
    RestClient swapiRestClient(RestClient.Builder builder) {
        return builder.baseUrl(swapiUrl).requestFactory(requestFactory()).build();
    }

    private ClientHttpRequestFactory requestFactory() {
        var factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeout);
        factory.setReadTimeout(readTimeout);
        return factory;
    }
}
