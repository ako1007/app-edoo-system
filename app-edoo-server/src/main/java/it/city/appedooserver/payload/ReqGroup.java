package it.city.appedooserver.payload;

import it.city.appedooserver.entity.User;
import it.city.appedooserver.entity.enums.Weekday;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ReqGroup {

    private String name;

    private Integer categoryId;

    private Timestamp startHour;

    private Timestamp finishHour;

    private Date lessonStartedDate;

    private Date lessonFinishDate;

    private UUID teacherId;

    private Weekday weekday;

    private List<String> weekdays;
}
