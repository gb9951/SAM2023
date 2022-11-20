package Notifications;

import java.time.LocalDateTime;
import java.util.Date;

public class Reminder extends Notification {
    private Date frequency;

    public Reminder(Date frequency) {
        super();
        this.frequency = frequency;
    }
}
