package com.desk8432.project.dto.group;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupDTO {
    private int id;
    private String groupname;
    private String imageUrl;
}
