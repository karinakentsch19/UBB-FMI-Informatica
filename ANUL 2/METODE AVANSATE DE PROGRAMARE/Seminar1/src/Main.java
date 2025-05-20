import java.time.LocalDateTime;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
//    public static void main(String[] args) {
//        MessageTask task1 = new MessageTask("1", "abc", "mesaj1", "A", "B", LocalDateTime.now());
//        MessageTask task2 = new MessageTask("2", "abc", "mesaj2", "B", "C", LocalDateTime.now());
//        MessageTask task3 = new MessageTask("3", "abc", "mesaj3", "C", "A", LocalDateTime.now());
//        MessageTask[] tasks = new MessageTask[]{task1, task2, task3};
//
//        for(MessageTask task: tasks){
//            System.out.println(task);
//        }
//    }

//    public static void main(String[] args) {
//        MessageTask task1 = new MessageTask("1", "abc", "mesaj1", "A", "B", LocalDateTime.now());
//        MessageTask task2 = new MessageTask("2", "abc", "mesaj2", "B", "C", LocalDateTime.now());
//        MessageTask task3 = new MessageTask("3", "abc", "mesaj3", "C", "A", LocalDateTime.now());
//        MessageTask[] tasks = new MessageTask[]{task1, task2, task3};
//
//        StrategyTaskRunner taskRunner = new StrategyTaskRunner(Strategy.valueOf(args[0]));
//        for(Task task: tasks){
//            taskRunner.addTask(task);
//        }
//
//        taskRunner.executeAll();
//    }

    public static void main(String[] args) {
        MessageTask task1 = new MessageTask("1", "abc", "mesaj1", "A", "B", LocalDateTime.now());
        MessageTask task2 = new MessageTask("2", "abc", "mesaj2", "B", "C", LocalDateTime.now());
        MessageTask task3 = new MessageTask("3", "abc", "mesaj3", "C", "A", LocalDateTime.now());
        MessageTask[] tasks = new MessageTask[]{task1, task2, task3};

        StrategyTaskRunner taskRunner = new StrategyTaskRunner(Strategy.valueOf(args[0]));
        for(Task task: tasks){
            taskRunner.addTask(task);
        }
        PrinterTaskRunner printerTaskRunner = new PrinterTaskRunner(taskRunner);
        PrinterTaskRunner printerTaskRunner1 = new PrinterTaskRunner(printerTaskRunner);
        printerTaskRunner1.executeAll();
    }
}