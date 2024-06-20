package com.desk8432.project.dto.group;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupViewResponseDTO {

    private List<SearchGroupDTO> myGroups;
    private List<SearchGroupDTO> followGroups;
}
