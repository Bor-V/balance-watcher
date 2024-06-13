package ru.tokenwoken.dto;

import lombok.*;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class To {

    private String ens_domain_name;

    private String hash;

    private String implementation_name;

    private Boolean is_contract;

    private Boolean is_verified;

    private String metadata;

    private String name;

    private ArrayList<String> private_tags;

    private ArrayList<String> public_tags;

    private ArrayList<String> watchlist_names;
}
