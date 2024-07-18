package ru.balancewatcher.dto.kaspa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    private String subnetwork_id;
    private String transaction_id;

    private String hash;
    private String mass;

    private List<String> block_hash;
    private Long block_time;
    private Boolean is_accepted;

    private String accepting_block_hash;
    private Integer accepting_block_blue_score;

    private List<Input> inputs;
    private List<Output> outputs;
}
