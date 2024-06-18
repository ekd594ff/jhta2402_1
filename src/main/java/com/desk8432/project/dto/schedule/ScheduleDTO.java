package com.desk8432.project.dto.schedule;


import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleDTO {
    private int id;
    private int groupID;
    private String groupname;
    private String editor;
    private String title;
    private String content;
    private Date startDate;
    private Date endDate;
}
