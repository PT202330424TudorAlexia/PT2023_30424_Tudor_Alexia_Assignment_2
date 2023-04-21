package Model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private AtomicInteger nrClients;

    public Server() {
        this.tasks = new SynchronousQueue<>();
        this.waitingPeriod = new AtomicInteger();
        this.nrClients = new AtomicInteger();
    }


    public void addTask(Task newTask) {
        //add task to queue
        if (tasks.offer(newTask)) {
            //increment the waiting period
            waitingPeriod.addAndGet(newTask.getServiceTime());
            nrClients.getAndIncrement();
        }
    }


    @Override
    public void run() {
        while (true) {
//take next task from queue

            //Task[] taskArray= getTasks();
            Task task = getNextTask(tasks);
            if (task == null) {
                continue;
            } else

                // stop the thread for a time equal with the task's processing time
                try {
                    Thread.sleep(task.getServiceTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            // decrement the waitingPeriod
            waitingPeriod.getAndAdd(-task.getServiceTime());

        }
    }

    public void writeInFile(int time,int ){
        String file="log.txt";

        try{
            FileWriter writeinfile=new FileWriter(new File(file));
            writeinfile.write("Time");
        }catch (IOException e){

        }

    }

    public Task getNextTask(BlockingQueue<Task> tasks) {
        Task t = tasks.poll();
        return t;
    }

    public Task[] getTasks() {
        Task[] taskArray = tasks.toArray(new Task[0]);
        return taskArray;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public void setWaitingPeriod(AtomicInteger waitingPeriod) {
        this.waitingPeriod = waitingPeriod;
    }

    public int getNumberTasks() {
        int result=0;
        Task[] taskArray= getTasks();
        result=taskArray.length;
        return result;
    }

}
