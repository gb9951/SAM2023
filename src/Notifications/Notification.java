package Notifications;

import Templates.Template;
import Users.User;

import java.util.ArrayList;
import java.util.Date;

public class Notification {
    private Date date;
    private Template template;
    ArrayList<User> sendToUsers;

    public Notification(Date date, Template template, ArrayList<User> sendToUsers) {
        this.date = date;
        this.template = template;
        this.sendToUsers = sendToUsers;
    }

    public Notification() {

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public ArrayList<User> getSendToUsers() {
        return sendToUsers;
    }

    public void setSendToUsers(ArrayList<User> sendToUsers) {
        this.sendToUsers = sendToUsers;
    }
}
