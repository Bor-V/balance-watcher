package ru.balancewatcher.dto.explorer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private String blockHash;
    private String blockNumber;
    private String confirmations;
    private String contractAddress;
    private String cumulativeGasUsed;
    private String from;
    private String gas;
    private String gasPrice;
    private String gasUsed;
    private String hash;
    private String input;
    private String isError;
    private String nonce;
    private String timeStamp;
    private String to;
    private String transactionIndex;
    private String txreceipt_status;
    private String value;
}
