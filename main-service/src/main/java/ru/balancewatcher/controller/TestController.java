package ru.balancewatcher.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.balancewatcher.dto.ValueDataDtoResponse;
import ru.balancewatcher.dto.ValueDataDtoResponseWithCount;
import ru.balancewatcher.service.BalanceService;

import java.util.List;


@RestController
@Slf4j
public class TestController {

    private final BalanceService octaBalanceService;

    private final BalanceService kaspaBalanceService;

    private final BalanceService etcBalanceService;


    public TestController(@Qualifier("octa")BalanceService octaBalanceService,
                          @Qualifier("kaspa")BalanceService kaspaBalanceService,
                          @Qualifier("etc")BalanceService etcBalanceService) {
        this.octaBalanceService = octaBalanceService;
        this.kaspaBalanceService = kaspaBalanceService;
        this.etcBalanceService = etcBalanceService;
    }

//    @GetMapping("/{address}/octa")
//    public List<ValueDataDtoResponse> getOctaValueData(@PathVariable String address) {
//        log.info("GET: /{}/value-data", address);
//        return octaBalanceService.getValueData(address);
//    }

    @GetMapping("/{address}/octa")
    public ValueDataDtoResponseWithCount getOctaValueData(@PathVariable String address) {
        log.info("GET: /{}/octa", address);
        return octaBalanceService.getValueDataWithCount(address);
    }

//    @GetMapping("/{address}/etc")
//    public List<ValueDataDtoResponse> getEtcValueData(@PathVariable String address) {
//        log.info("GET: /{}/etc", address);
//        return etcBalanceService.getValueData(address);
//    }

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

//    @GetMapping("/{address}/kaspa")
//    public List<ValueDataDtoResponse> getKaspaResponse(@PathVariable String address) {
//        log.info("GET: /{}/kaspa", address);
//        return kaspaBalanceService.getValueData(address);
//    }

    @GetMapping("/{address}/etc")
    public ValueDataDtoResponseWithCount getEtcValueDataWIthCount(@PathVariable String address) {
        log.info("GET: /{}/etc", address);
        return etcBalanceService.getValueDataWithCount(address);
    }

    @GetMapping("/{address}/kaspa")
    public ValueDataDtoResponseWithCount getKaspaResponseWithCount(@PathVariable String address) {
        log.info("GET: /{}/kaspa", address);
        return kaspaBalanceService.getValueDataWithCount(address);
    }
}
