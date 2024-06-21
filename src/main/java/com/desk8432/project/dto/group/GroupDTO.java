package com.desk8432.project.dto.group;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupDTO {
    private Long id;
    private String groupname;
    private String creator;
    private String content;
    private String imageUrl;
}
