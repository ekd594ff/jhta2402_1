package com.desk8432.project.dto.group;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowRequestDTO {

    private String username;
    private Long groupID;
}
