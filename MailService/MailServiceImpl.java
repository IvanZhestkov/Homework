package twosemestr;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by User on 15.04.2017.
 */
public class MailServiceImpl<T> implements MailService<T> {
    @Override
    public Map<String, List<T>> getMailBox() {
        return null;
    }

    @Override
    public void accept(Message<T> tMessage) {

    }

    @Override
    public Consumer<Message<T>> andThen(Consumer<? super Message<T>> after) {
        return null;
    }
}
