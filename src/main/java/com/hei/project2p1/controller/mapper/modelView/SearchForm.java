package com.hei.project2p1.controller.mapper.modelView;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchForm {
    private String lastName;
    private String firstName;
    private String gender;
    private String function;
}
