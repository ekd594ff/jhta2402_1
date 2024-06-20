package org.desk8432.project.dto.group;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsertGroupDTO {
    private String name;
    private String creater;
    private String image_url;
    private String content;
    private String created_at;
    private String updated_at;


}
