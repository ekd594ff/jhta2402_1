package com.desk8432.project.dto.image;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InputImageUrlDTO {
    private String username;
    private long group_id;
    private String image_url;
    private String originalName;
    private String fileName;
    private String location;
}
