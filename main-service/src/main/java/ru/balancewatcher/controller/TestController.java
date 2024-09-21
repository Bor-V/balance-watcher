package ru.balancewatcher.controller;
import lombok.extern.slf4j.Slf4j;
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

    private final BalanceService octaBalanceService;

    private final BalanceService kaspaBalanceService;


    public TestController(@Qualifier("explorer")BalanceService octaBalanceService,
                          @Qualifier("kaspa")BalanceService kaspaBalanceService ) {
        this.octaBalanceService = octaBalanceService;
        this.kaspaBalanceService = kaspaBalanceService;
    }

    @GetMapping("/{address}/octa")
    public List<ValueDataDtoResponse> getValueData(@PathVariable String address) {
        log.info("GET: /{}/value-data", address);
        return octaBalanceService.getValueData(address);
    }

//    @GetMapping("/{address}/value")
//    public List<ValueDataDtoResponse> getValueDataExplorer(@PathVariable String address) {
//        log.info("GET: /{}/value", address);
//        return balanceService1.getValueData(address);
//    }

//    @GetMapping("/{address}/")
//    public String checkAddress(@PathVariable String address) {
//        log.info("GET: /{}", address);
//        return octaBalanceService.checkAddress(address);
//    }

    @GetMapping("/{address}/kaspa")
    public List<ValueDataDtoResponse> getKaspaResponse(@PathVariable String address) {
        log.info("GET: /{}/kaspa", address);
        return kaspaBalanceService.getValueData(address);
    }
}
