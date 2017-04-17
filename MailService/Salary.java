package twosemestr;

/**
 * Created by User on 15.04.2017.
 */
public class Salary implements Message<Integer> {
    String from;
    String to;
    int salary;

    public Salary(String from, String to, int salary) {
        this.from = from;
        this.to = to;
        this.salary = salary;
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
    public Integer getContent() {
        return salary;
    }
}
