package com.desk8432.project.dto.schedule;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateScheduleDTO {
    private int id;
    private int group_id;
    private String editor;
    private String title;
    private String content;
    private String startDate;
    private String endDate;
    private String groupname;
}
