package com.desk8432.project.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DuplicateDTO {
    private String username;
    private String nickname;
    private String email;
}
