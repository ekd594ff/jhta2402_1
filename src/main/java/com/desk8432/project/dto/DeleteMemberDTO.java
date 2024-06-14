package com.desk8432.project.dto;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteMemberDTO {
    private String username;
    private String password;
}
