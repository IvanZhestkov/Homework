package twosemestr;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by User on 15.04.2017.
 */
public interface MailService<T> extends Consumer<Message<T>> {   // добавлен встроенный функц. интерфейс Consumer

    Map<String, List<T>> getMailBox();
}