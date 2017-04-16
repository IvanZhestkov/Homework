package twosemestr;

/**
 * Created by User on 15.04.2017.
 */


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Random variables
        String randomFrom = "..."; // Некоторая случайная строка. Можете выбрать ее самостоятельно.
        String randomTo = "...";  // Некоторая случайная строка. Можете выбрать ее самостоятельно.
        int randomSalary = 100;  // Некоторое случайное целое положительное число. Можете выбрать его самостоятельно.

        // Создание списка из трех почтовых сообщений.
        MailMessage firstMessage = new MailMessage(
                "Robert Howard",
                "H.P. Lovecraft",
                "This \"The Shadow over Innsmouth\" story is real masterpiece, Howard!"
        );

        if (!firstMessage.getFrom().equals("Robert Howard")) {
            throw new RuntimeException("Wrong firstMessage from address");
        }
        if (!firstMessage.getTo().equals("H.P. Lovecraft")) {
            throw new RuntimeException("Wrong firstMessage to address");
        }
        if (!firstMessage.getContent().endsWith("Howard!")) {
            throw new RuntimeException("Wrong firstMessage content ending");
        }

        MailMessage secondMessage = new MailMessage(
                "Jonathan Nolan",
                "Christopher Nolan",
                "Брат, почему все так хвалят только тебя, когда практически все сценарии написал я. Так не честно!"
        );

        MailMessage thirdMessage = new MailMessage(
                "Stephen Hawking",
                "Christopher Nolan",
                "Я так и не понял Интерстеллар."
        );

        List<MailMessage> messages = Arrays.asList(
                firstMessage, secondMessage, thirdMessage
        );

        // Создание почтового сервиса.
        MailService<String> mailService = new MailServiceImpl<>();

        // Обработка списка писем почтовым сервисом
        messages.stream().forEachOrdered(mailService);

        // Получение и проверка словаря "почтового ящика",
        //   где по получателю можно получить список сообщений, которые были ему отправлены
        Map<String, List<String>> mailBox = mailService.getMailBox();

        if (!mailBox.get("H.P. Lovecraft").equals(
                Collections.singletonList(
                        "This \"The Shadow over Innsmouth\" story is real masterpiece, Howard!"
                )
        )) {
            throw new RuntimeException("wrong mailService mailbox content (1)");
        }
        if (!mailBox.get("Christopher Nolan").equals(
                Arrays.asList(
                        "Брат, почему все так хвалят только тебя, когда практически все сценарии написал я. Так не честно!",
                        "Я так и не понял Интерстеллар."
                )
        )) {
            throw new RuntimeException("wrong mailService mailbox content (2)");
        }
        if (mailBox.get(randomTo).equals(Collections.<String>emptyList())) {
            throw new RuntimeException("wrong mailService mailbox content (3)");
        }


        // Создание списка из трех зарплат.
        Salary salary1 = new Salary("Facebook", "Mark Zuckerberg", 1);
        Salary salary2 = new Salary("FC Barcelona", "Lionel Messi", Integer.MAX_VALUE);
        Salary salary3 = new Salary(randomFrom, randomTo, randomSalary);

        // Создание почтового сервиса, обрабатывающего зарплаты.
        MailService<Integer> salaryService = new MailServiceImpl<>();

        // Обработка списка зарплат почтовым сервисом
        Arrays.asList(salary1, salary2, salary3).forEach(salaryService);

        // Получение и проверка словаря "почтового ящика",
        //   где по получателю можно получить список зарплат, которые были ему отправлены.
        Map<String, List<Integer>> salaries = salaryService.getMailBox();
        if (!salaries.get(salary1.getTo()).equals(Collections.singletonList(1))) {
            throw new RuntimeException("wrong salaries mailbox content (1)");
        }
        if (!salaries.get(salary2.getTo()).equals(Collections.singletonList(Integer.MAX_VALUE))) {
            throw new RuntimeException("wrong salaries mailbox content (2)");
        }
        if (!salaries.get(randomTo).equals(Collections.singletonList(randomSalary))) {
            throw new RuntimeException("wrong salaries mailbox content (3)");
        }
    }
}
