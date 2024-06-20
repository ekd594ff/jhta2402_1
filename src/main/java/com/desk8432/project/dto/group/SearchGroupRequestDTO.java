package com.desk8432.project.dto.group;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchGroupRequestDTO {

    private String searchFilter;
    private String searchValue;
    private int page;
}
