import java.io.*;
import java.util.*;
import java.ulit.Scanner;

class Task implements Serializable {
    String title;
    String startTime;
    String endTime;
    boolean archived;

    Task(String title, String startTime, String endTime) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.archived = false;
    }
}

public class Main {

    static Scanner sc = new Scanner(System.in);
    static final String FILE_NAME = "tasks.ser";

    public static void main(String[] args) {

        while (true) {
            System.out.println("\nTo Do Task Management\n");
            System.out.println("1. Create Task");
            System.out.println("2. View Task/s");
            System.out.println("3. Update Task");
            System.out.println("4. Archive a Task");

            int menu = getValidMenu(1, 4);

            if (menu == 1) {
                createTask();
            } else if (menu == 2) {
                viewTasks();
            } else if (menu == 3) {
                updateTask();
            } else if (menu == 4) {
                archiveTask();
            }
        }
    }

    static void createTask() {
        ArrayList<Task> tasks = loadTasks();

        sc.nextLine();
        System.out.print("Input the title: ");
        String title = sc.nextLine();

        System.out.print("Input the start time: ");
        String start = sc.nextLine();

        System.out.print("Input the end time: ");
        String end = sc.nextLine();

        tasks.add(new Task(title, start, end));
        saveTasks(tasks);

        System.out.println("Task created successfully!");
    }

    static void viewTasks() {
        ArrayList<Task> tasks = loadTasks();

        if (!hasActiveTask(tasks)) {
            System.out.println("No task available.");
            return;
        }

        System.out.println("TITLE\tSTART TIME\tEND TIME");

        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            if (!t.archived) {
                System.out.println(t.title + "\t" + t.startTime + "\t\t" + t.endTime);
            }
        }
    }

    static void updateTask() {
        ArrayList<Task> tasks = loadTasks();

        if (!hasActiveTask(tasks)) {
            System.out.println("No task available to update.");
            return;
        }

        showTasksWithId(tasks);

        System.out.print("Input task ID to update: ");
        int id = getValidTaskId(1, countActiveTasks(tasks)) - 1;

        Task task = getTaskByActiveId(tasks, id);

        System.out.println("What information do you want to update?");
        System.out.println("1. Title");
        System.out.println("2. START TIME");
        System.out.println("3. END TIME");

        int choice = getValidMenu(1, 3);
        sc.nextLine();

        if (choice == 1) {
            System.out.print("Input the new title: ");
            task.title = sc.nextLine();
        } else if (choice == 2) {
            System.out.print("Input the new start time: ");
            task.startTime = sc.nextLine();
        } else if (choice == 3) {
            System.out.print("Input the new end time: ");
            task.endTime = sc.nextLine();
        }

        saveTasks(tasks);
        System.out.println("Task updated successfully!");
    }

    static void archiveTask() {
        ArrayList<Task> tasks = loadTasks();

        if (!hasActiveTask(tasks)) {
            System.out.println("No task available.");
            return;
        }

        showTasksWithId(tasks);

        System.out.print("Input task ID to archive: ");
        int id = getValidTaskId(1, countActiveTasks(tasks)) - 1;

        Task task = getTaskByActiveId(tasks, id);
        task.archived = true;

        saveTasks(tasks);
        System.out.println("Task archived successfully!");
    }

    static boolean hasActiveTask(ArrayList<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            if (!tasks.get(i).archived) {
                return true;
            }
        }
        return false;
    }

    static int countActiveTasks(ArrayList<Task> tasks) {
        int count = 0;
        for (int i = 0; i < tasks.size(); i++) {
            if (!tasks.get(i).archived) {
                count++;
            }
        }
        return count;
    }

    static void showTasksWithId(ArrayList<Task> tasks) {
        System.out.println("ID\tTITLE\tSTART TIME\tEND TIME");
        int id = 1;
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            if (!t.archived) {
                System.out.println(id + "\t" + t.title + "\t" + t.startTime + "\t\t" + t.endTime);
                id++;
            }
        }
    }

    static Task getTaskByActiveId(ArrayList<Task> tasks, int index) {
        int count = 0;
        for (int i = 0; i < tasks.size(); i++) {
            if (!tasks.get(i).archived) {
                if (count == index) {
                    return tasks.get(i);
                }
                count++;
            }
        }
        return null;
    }

    static ArrayList<Task> loadTasks() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME));
            ArrayList<Task> tasks = (ArrayList<Task>) ois.readObject();
            ois.close();
            return tasks;
        } catch (Exception e) {
            return new ArrayList<Task>();
        }
    }

    static void saveTasks(ArrayList<Task> tasks) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            oos.writeObject(tasks);
            oos.close();
        } catch (Exception e) {
            System.out.println("Error saving file.");
        }
    }

    static int getValidMenu(int min, int max) {
        while (true) {
            try {
                System.out.print("Input a number: ");
                int num = sc.nextInt();
                if (num < min || num > max) {
                    throw new Exception();
                }
                return num;
            } catch (Exception e) {
                sc.nextLine();
                System.out.println("Invalid input, please try again. thxz!");
            }
        }
    }

    static int getValidTaskId(int min, int max) {
        while (true) {
            try {
                int num = sc.nextInt();
                if (num < min || num > max) {
                    throw new Exception();
                }
                return num;
            } catch (Exception e) {
                sc.nextLine();
                System.out.println("Invalid input, please try again. thxz!");
            }
        }
    }
}
