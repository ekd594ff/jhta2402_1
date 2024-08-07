package com.desk8432.project.dto.member;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPageDTO {
    private String username;
    private String nickname;
    private String email;
    private String image_url;
    private String introduction;
    private LocalDate created_at;
    private LocalDate updated_at;
}
