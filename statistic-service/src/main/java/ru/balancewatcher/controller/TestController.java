package ru.balancewatcher.controller;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.mbeans.SparseUserDatabaseMBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.balancewatcher.dto.ValueDataDtoResponse;
import ru.balancewatcher.service.BalanceService;

import java.util.List;


@RestController
@Slf4j
public class TestController {

    private final BalanceService balanceService;

    private final BalanceService balanceService1;

    private final BalanceService kaspaBalanceService;


    public TestController(@Qualifier("explorer")BalanceService balanceService,
                          @Qualifier("scan")BalanceService balanceService1,
                          @Qualifier("kaspa")BalanceService kaspaBalanceService ) {
        this.balanceService = balanceService;
        this.balanceService1 = balanceService1;
        this.kaspaBalanceService = kaspaBalanceService;
    }

    @GetMapping("/{address}/value-data")
    public List<ValueDataDtoResponse> getValueData(@PathVariable String address) {
        log.info("GET: /{}/value-data", address);
        return balanceService.getValueData(address);
    }

    @GetMapping("/{address}/value")
    public List<ValueDataDtoResponse> getValueDataExplorer(@PathVariable String address) {
        log.info("GET: /{}/value", address);
        return balanceService1.getValueData(address);
    }

    @GetMapping("/{address}/")
    public String checkAddress(@PathVariable String address) {
        log.info("GET: /{}", address);
        return balanceService.checkAddress(address);
    }

    @GetMapping("/{address}/kaspa")
    public List<ValueDataDtoResponse> getKaspaResponse(@PathVariable String address) {
        log.info("GET: /{}/kaspa", address);
        return kaspaBalanceService.getValueData(address);
    }
}
