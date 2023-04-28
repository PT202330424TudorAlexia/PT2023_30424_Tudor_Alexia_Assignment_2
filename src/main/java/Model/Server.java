package Model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private AtomicInteger nrClients;
    private boolean finish=false;

    public Server(int numberOfClients) {
        this.tasks = new ArrayBlockingQueue<>(numberOfClients);
        this.waitingPeriod = new AtomicInteger(0);
        this.nrClients = new AtomicInteger();
    }


    public void addTask(Task newTask) {
            waitingPeriod.addAndGet(newTask.getServiceTime());
            nrClients.getAndIncrement();
            tasks.add(newTask);
    }


    @Override
    public void run() {
        while (true) {
            Task task = tasks.peek();

            if (task != null) {

                try {
                    Thread.sleep(task.getServiceTime());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(task.getServiceTime()==0){
                tasks.remove();
                waitingPeriod.getAndAdd(-task.getServiceTime());

                }
            }
        }
    }

    public Task getNextTask() {
        Task t = tasks.peek();
        return t;
    }

    public void deleteTask(){
        tasks.remove();
    }
    public List<Task> getTasks() {
        List<Task> taskList = new ArrayList<>();
        for (Task t: tasks) {
            taskList.add(t);
        }
        return taskList;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public void setWaitingPeriod(AtomicInteger waitingPeriod) {
        this.waitingPeriod = waitingPeriod;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public int getNumberTasks() {
        int result=0;
        List<Task> taskArray= getTasks();
        result=taskArray.size();
        return result;
    }

}
