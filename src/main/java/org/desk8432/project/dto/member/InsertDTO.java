package org.desk8432.project.dto.member;

import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsertDTO {
    private String username;
    private String nickname;
    private String email;
    private String password;
    private String introduction;
}
