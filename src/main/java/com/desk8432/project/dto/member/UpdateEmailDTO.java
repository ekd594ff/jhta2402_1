package com.desk8432.project.dto.member;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateEmailDTO {
    private String username;
    private String email;
}
