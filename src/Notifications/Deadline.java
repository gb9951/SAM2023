package Notifications;

import Templates.Template;
import Users.User;

import java.util.ArrayList;
import java.util.Date;

public class Deadline extends Notification {

    private EventType eventType;


    public Deadline(Date date, Template template, ArrayList<User> sendToUsers, EventType eventType) {
        super(date, template, sendToUsers);
        this.eventType = eventType;
    }


    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
}

