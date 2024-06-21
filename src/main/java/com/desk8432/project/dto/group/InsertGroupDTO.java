package com.desk8432.project.dto.group;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsertGroupDTO {
    private String name;
    private String creator;
    private String image_url;
    private String content;
    private String created_at;
    private String updated_at;


}
