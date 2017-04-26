package twosemestr.informatics;

import java.util.*;

/**
 * Created by User on 15.04.2017.
 */
public class MailServiceImpl<T> implements MailService<T> {

    private Map<String, List<T>> messagesMap = new HashMap<String, List<T>>(){
        @Override
        public List<T> get(Object key) {
            if (this.containsKey(key)) {
                return super.get(key);
            } else {
                return Collections.emptyList();
            }
        }
    };

    @Override
    public Map<String, List<T>> getMailBox() {
        return messagesMap;
    }

    @Override
    public void accept(Message<T> tMessage) {
        List<T> ts = messagesMap.get(tMessage.getTo());
        if (ts.size() == 0) {
            ts = new ArrayList<>();
        }
        ts.add(tMessage.getContent());
        messagesMap.put(tMessage.getTo(), ts);
    }
}
