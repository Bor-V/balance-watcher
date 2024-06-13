package ru.tokenwoken.dto;

import lombok.*;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Transaction {

    private String timestamp;

    private Fee fee;

    private String gas_limit;

    private Double block;

    private String status;

    private String method;

    private Double confirmations;

    private Double type;

    private String exchange_rate;

    private To to;

    private String tx_burnt_fee;

    private String max_fee_per_gas;

    private String result;

    private String hash;

    private String gas_price;

    private String priority_fee;

    private String base_fee_per_gas;

    private From from;

    private String token_transfers;

    private ArrayList<String> tx_types;

    private String gas_used;

    private String created_contract;

    private Double position;

    private Double nonce;

    private Boolean has_error_in_internal_txs;

    private ArrayList<String> actions;

    private String decoded_input;

    private String token_transfers_overflow;

    private String raw_input;

    private String value;

    private String max_priority_fee_per_gas;

    private String revert_reason;

    private ArrayList<String> confirmation_duration;

    private String tx_tag;
}
