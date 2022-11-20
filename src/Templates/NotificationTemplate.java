package Templates;

public class NotificationTemplate extends Template {

    private String message;

    public NotificationTemplate(int templateId, String templateName, String templateDescription, String message) {
        super(templateId, templateName, templateDescription);
        this.message = message;
    }
}
