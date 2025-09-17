package ru.balancewatcher.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.balancewatcher.service.DogeClient;
import ru.balancewatcher.service.KaspaClient;
import ru.balancewatcher.service.OctaClient;

@Configuration
@RequiredArgsConstructor
public class ClientConfig {

//    @Value("${stats-service.url1}")
//    private String serverUrl;

    @Value("${octa.server.url}")
    private String octaServerUrl;

    @Value("${kaspa.server.url}")
    private String kaspaServerUrl;

    @Value("https://explorer.dogechain.dog/")
    private String dogeServerUrl;

//    @Bean
//    public StatisticClientScan getScanClient() {
//        return new StatisticClientScan(serverUrl);
//    }

    @Bean
    public OctaClient getOctaClient() {
        return new OctaClient(octaServerUrl);
    }

    @Bean
    public KaspaClient getKaspaClient() {
        return new KaspaClient(kaspaServerUrl);
    }

    @Bean
    public DogeClient getDogeClient() {
        return new DogeClient(dogeServerUrl);
    }
}
