package ru.tokenwoken.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tokenwoken.service.StatisticClient;

@Configuration
public class StatClientConfig {


    @Value("${stats-service.url}")
    private String serverUrl;

    @Bean
    public StatisticClient getClient() {
        return new StatisticClient(serverUrl);
    }
}
