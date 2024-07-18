package ru.balancewatcher.dto.kaspa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Input {

    private Integer id;
    private String transaction_id;
    private Integer index;
    private String previous_outpoint_hash;
    private String previous_outpoint_index;
    private Outpoint previous_outpoint_resolved;
    private String previous_outpoint_address;
    private Long previous_outpoint_amount;
    private String signature_script;
    private String sig_op_count;
}
