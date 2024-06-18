package com.desk8432.project.dto.schedule;


import lombok.*;

import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateScheduleDTO {
    private int id;
    private int groupID;
    private String editor;
    private String title;
    private String content;
    private Date startDate;
    private Date endDate;
    private String groupname;
}
