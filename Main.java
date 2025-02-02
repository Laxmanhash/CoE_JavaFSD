import java.util.*;

class TaskItem implements Comparable<TaskItem> {
    private String taskId;
    private String taskDetails;
    private int taskPriority;

    public TaskItem(String taskId, String taskDetails, int taskPriority) {
        this.taskId = taskId;
        this.taskDetails = taskDetails;
        this.taskPriority = taskPriority;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getTaskDetails() {
        return taskDetails;
    }

    public int getTaskPriority() {
        return taskPriority;
    }

    @Override
    public int compareTo(TaskItem other) {
        return Integer.compare(other.taskPriority, this.taskPriority);
    }

    @Override
    public String toString() {
        return "TaskItem[ID=" + taskId + ", Details=" + taskDetails + ", Priority=" + taskPriority + "]";
    }
}

class TaskScheduler {
    private PriorityQueue<TaskItem> taskQueue;
    private Map<String, TaskItem> taskLookup;

    public TaskScheduler() {
        taskQueue = new PriorityQueue<>();
        taskLookup = new HashMap<>();
    }

    public void addNewTask(String taskId, String taskDetails, int taskPriority) {
        if (taskLookup.containsKey(taskId)) {
            System.out.println("Task with ID " + taskId + " already exists.");
            return;
        }
        TaskItem newTask = new TaskItem(taskId, taskDetails, taskPriority);
        taskQueue.add(newTask);
        taskLookup.put(taskId, newTask);
    }

    public void deleteTask(String taskId) {
        TaskItem task = taskLookup.remove(taskId);
        if (task != null) {
            taskQueue.remove(task);
        } else {
            System.out.println("No task found with ID " + taskId);
        }
    }

    public TaskItem peekHighestPriorityTask() {
        return taskQueue.peek();
    }

    public void displayAllTasks() {
        for (TaskItem task : taskQueue) {
            System.out.println(task);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        TaskScheduler scheduler = new TaskScheduler();

        scheduler.addNewTask("A1", "Resolve UI Bug", 3);
        scheduler.addNewTask("A2", "Develop API Endpoint", 5);
        scheduler.addNewTask("A3", "Optimize Database Queries", 4);

        System.out.println("Top Priority Task: " + scheduler.peekHighestPriorityTask());

        scheduler.deleteTask("A2");

        System.out.println("Tasks after removing A2:");
        scheduler.displayAllTasks();
    }
}
