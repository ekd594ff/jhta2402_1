package com.desk8432.project.dto;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDTO {
    private String username;
    private String originalName;
    private String fileName;
    private String location;
}
