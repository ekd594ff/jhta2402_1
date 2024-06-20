package org.desk8432.project.dto.member;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateNicknameDTO {
    private String username;
    private String nickname;
}
