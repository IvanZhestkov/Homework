package twosemestr.informatics;

/**
 * Created by User on 15.04.2017.
 */
public interface Message<T> {
    String getFrom();

    String getTo();

    T getContent();
}
