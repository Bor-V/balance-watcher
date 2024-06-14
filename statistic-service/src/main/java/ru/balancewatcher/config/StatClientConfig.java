package ru.balancewatcher.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.balancewatcher.service.StatisticClientExplorer;
import ru.balancewatcher.service.StatisticClientScan;

@Configuration
@RequiredArgsConstructor
public class StatClientConfig {

    @Value("${stats-service.url1}")
    private String serverUrl;

    @Value("${stats-service.url2}")
    private String serverUrl1;

    @Bean
    public StatisticClientScan getScanClient() {
        return new StatisticClientScan(serverUrl);
    }

    @Bean
    public StatisticClientExplorer getExplorerClient() {
        return new StatisticClientExplorer(serverUrl1);
    }
}
