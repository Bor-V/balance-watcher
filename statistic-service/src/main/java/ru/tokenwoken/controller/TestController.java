package ru.tokenwoken.controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.tokenwoken.dto.ValueDataDtoResponse;
import ru.tokenwoken.service.TransactionService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
public class TestController {


    private final TransactionService transactionService;

    @GetMapping("/{address}/value-data")
    public List<ValueDataDtoResponse> getValueData(@PathVariable String address) {
        log.info("GET: /{}/value-data", address);
        return transactionService.getValueData(address);
    }
}
