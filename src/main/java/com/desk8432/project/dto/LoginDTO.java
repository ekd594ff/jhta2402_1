package com.desk8432.project.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDTO {

    private String username;
    private String password;
}
