package com.desk8432.project.dto.group;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchGroupDTO {

    private int id;
    private String creator;
    private String name;
    private String image_url;
    private String content;
    private Date created_at;
    private Date updated_at;
}
