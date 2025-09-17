package ru.balancewatcher.dto.kaspa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Outpoint {

    private Integer id;
    private String transaction_id;
    private Integer index;
    private Long amount;
    private String script_public_key;
    private String script_public_key_address;
    private String script_public_key_type;
    private String accepting_block_hash;
}
