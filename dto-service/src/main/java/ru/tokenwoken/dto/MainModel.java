package ru.tokenwoken.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class MainModel {

    private List<Transaction> items;

    private NextPageParams next_page_params;
}
