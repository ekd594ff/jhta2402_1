package com.desk8432.project.dto.image;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageUrlDTO {
    private String username;
    private Long group_id;
    private String imageUrl;
    private String originalName;
    private String fileName;
    private String location;
}
