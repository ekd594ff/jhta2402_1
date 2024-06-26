package com.desk8432.project.dto.member;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateImageUrlDTO {
    private String username;
    private String imageUrl;
    private String originalName;
    private String fileName;
    private String location;
    private String imgFolderPath;
    private String uploadUrl;
}
