package twosemestr;

/**
 * Created by User on 15.04.2017.
 */
public class MailMessage implements Message<String> {
    String from;
    String to;
    String content;

    public MailMessage(String from, String to, String content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }

    @Override
    public String getFrom() {
        return from;
    }

    @Override
    public String getTo() {
        return to;
    }

    @Override
    public String getContent() {
        return content;
    }
}