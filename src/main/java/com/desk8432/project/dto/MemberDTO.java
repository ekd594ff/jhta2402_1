package com.desk8432.project.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {
    private long id;
    private String username;
    private String nickname;
    private String email;
    private String password;
    private String imageUrl;
    private String introduction;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
