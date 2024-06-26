package com.desk8432.project.dto.member;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginMemberDTO {

    private String username;
    private String nickname;
    private String email;
    private String imageUrl;
    private String introduction;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
