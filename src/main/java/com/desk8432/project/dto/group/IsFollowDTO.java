package com.desk8432.project.dto.group;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IsFollowDTO {

    private long groupID;
    private String username;
}
