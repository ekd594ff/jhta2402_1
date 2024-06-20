package org.desk8432.project.dto.member;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePasswordDTO {
    private String username;
    private String password;
}
