package BusinessLogic;

import GUI.SimulationFrame;
import Model.Server;
import Model.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.*;

public class SimulationManager implements Runnable {
    public int timeLimit = 200;
    public int minArrivalTime = 10;
    public int maxArrivalTime = 100;
    public int maxProcessingTime = 9;
    public int minProcessingTime = 3;
    public int numberOfServers = 20;
    public int numberOfClients = 1000;
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;

    private Scheduler scheduler;
    private SimulationFrame frame ;
    private List<Task> generatedTasks; //waiting clients

    public SimulationManager(SimulationFrame frame, int timeLimit, int minArrivalTime, int maxArrivalTime, int maxProcessingTime, int minProcessingTime, int numberOfServers, int numberOfClients, SelectionPolicy selectionPolicy) {
        this.scheduler = new Scheduler(numberOfServers, numberOfClients, numberOfClients);
        this.frame = frame;
        this.timeLimit = timeLimit;
        this.minProcessingTime = minProcessingTime;
        this.maxProcessingTime = maxProcessingTime;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.numberOfServers = numberOfServers;
        this.numberOfClients = numberOfClients;

        createStrategy(selectionPolicy);
        generatedTasks = generateNRandomTasks(numberOfClients, minProcessingTime, maxProcessingTime, timeLimit);

    }

    private void createStrategy(SelectionPolicy selectionPolicy) {
        scheduler.changeStrategy(selectionPolicy);
    }

    private List<Task> generateNRandomTasks(int numberOfClients, int minProcessingTime, int maxProcessingTime, int timeLimit) {
        int nrprocessing, nrarrival;
        Random random_nr = new Random();
        generatedTasks = new ArrayList<>();
        int i = 0;
        while (i < numberOfClients) {
            nrprocessing = random_nr.nextInt(maxProcessingTime - minProcessingTime + 1) + minProcessingTime;

            nrarrival = random_nr.nextInt(maxArrivalTime - minArrivalTime + 1) + minArrivalTime;
            Task task = new Task(nrarrival, nrprocessing);
            generatedTasks.add(task);

            i++;
        }

        generatedTasks.sort(Comparator.comparing(Task::getArrivalTime));
        return generatedTasks;
    }


    @Override
    public void run() {
        int j;
        int avgservice = 0, peakhour = 0, hour = 0, finished = 0;

        List<Server> servers = scheduler.getServers();
        StringBuilder result = new StringBuilder();
        FileWriter writeinfile;
        try {
            writeinfile = new FileWriter("TRY.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int currentTime = 0;

        while (currentTime < timeLimit) {

            try {
                writeinfile.write("Time: " + currentTime + "\n");
                result.append("Time: " + currentTime + "\n");
                writeinfile.write("Waiting clients: ");
                result.append("Waiting clients: ");

                for (Task t : generatedTasks) {

                    writeinfile.write("(" + t.getId() + ", " + t.getArrivalTime() + ", " + t.getServiceTime() + "),");
                    result.append("(" + t.getId() + ", " + t.getArrivalTime() + ", " + t.getServiceTime() + "),");

                }
                writeinfile.write("\n");
                result.append("\n");

                j = 0;
                for (Server i : servers) {
                    writeinfile.write("Queue " + j + ": ");
                    result.append("Queue " + j + ": ");

                    if (i.getNumberTasks() == 0) {
                        writeinfile.write("closed");
                        result.append("closed");
                    } else {
                        for (Task t : i.getTasks()) {
                            writeinfile.write("(" + t.getId() + ", " + t.getArrivalTime() + ", " + t.getServiceTime() + "),");
                            result.append("(" + t.getId() + ", " + t.getArrivalTime() + ", " + t.getServiceTime() + "),");
                        }
                    }
                    j++;
                    writeinfile.write("\n");
                    result.append("\n");

                }
                writeinfile.write("\n");
                result.append("\n");

            } catch (IOException e) {
            }

            frame.updateTextArea(result.toString());

            if (generatedTasks.isEmpty()) {
                int c = 0;
                for (Server i : servers) {
                    if (i.getTasks().isEmpty())
                        c++;

                }
                if (c == scheduler.getServers().size())
                    currentTime += timeLimit + 1;
            }

            j = 0;
            for (Server i : servers) {
                j++;
                i.setFinish(true);
                Task nT;
                if (!i.getTasks().isEmpty()) {
                    if (i.getNumberTasks() > peakhour) {
                        peakhour = i.getNumberTasks();
                        hour = currentTime;
                    }
                    nT = i.getNextTask();
                    if (nT.getServiceTime() == 1) {
                        avgservice = avgservice + currentTime - nT.getArrivalTime();
                        finished++;
                        i.deleteTask();
                    } else {

                        nT.setServiceTime(nT.getServiceTime() - 1);
                    }
                }


            }
            Iterator<Task> iterator = generatedTasks.iterator();

            while (iterator.hasNext()) {
                Task next = iterator.next();

                if (next.getArrivalTime() == currentTime) {
                    scheduler.dispatchTask(next);
                    iterator.remove();
                }
            }
            currentTime++;
            synchronized (this) {
                try {
                    wait(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }

        float avg = 0;
        avg = (float)avgservice / finished;
        try {
            writeinfile.write("Average service time: " + avg + "\n");

            writeinfile.write("Peak hour: " + hour + " with " + peakhour + " clients\n");
            result.append("Average service time: " + avg + "\n");
            result.append("Peak hour: " + hour + " with " + peakhour + " clients\n");

        } catch (IOException e) {
        }

        frame.updateTextArea(result.toString());

        try {
            writeinfile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getMaxProcessingTime() {
        return maxProcessingTime;
    }

    public void setMaxProcessingTime(int maxProcessingTime) {
        this.maxProcessingTime = maxProcessingTime;
    }

    public int getMinProcessingTime() {
        return minProcessingTime;
    }

    public void setMinProcessingTime(int minProcessingTime) {
        this.minProcessingTime = minProcessingTime;
    }

    public int getNumberOfServers() {
        return numberOfServers;
    }

    public void setNumberOfServers(int numberOfServers) {
        this.numberOfServers = numberOfServers;
    }

    public int getNumberOfClients() {
        return numberOfClients;
    }

    public void setNumberOfClients(int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    public SelectionPolicy getSelectionPolicy() {
        return selectionPolicy;
    }

    public void setSelectionPolicy(SelectionPolicy selectionPolicy) {
        this.selectionPolicy = selectionPolicy;
    }

    public void startSimulation(SimulationFrame frame, int timeLimit, int minArrivalTime, int maxArrivalTime, int maxProcessingTime, int minProcessingTime, int numberOfServers, int numberOfClients, SelectionPolicy selectionPolicy) {
        //int timeLimit, minArrivalTime, maxArrivalTime, maxProcessingTime, minProcessingTime, numberOfServers, numberOfClients;

        SimulationManager gen = new SimulationManager(frame,timeLimit, minArrivalTime, maxArrivalTime, maxProcessingTime, minProcessingTime, numberOfServers, numberOfClients, selectionPolicy);
        Thread t = new Thread(gen);
        t.start();

    }
/*
    public static void main(String[] args) {
        int timeLimit = 0, minArrivalTime= 0, maxArrivalTime= 0, maxProcessingTime= 0, minProcessingTime=0, numberOfServers= 0, numberOfClients= 0;
        SimulationFrame frame1 = new SimulationFrame();
        timeLimit=frame1.getTextField3();
        System.out.println(timeLimit);
        SelectionPolicy policy = SelectionPolicy.SHORTEST_TIME;
        SimulationManager gen = new SimulationManager(timeLimit, minArrivalTime, maxArrivalTime, maxProcessingTime, minProcessingTime, numberOfServers, numberOfClients, policy);
        Thread t = new Thread(gen);
        t.start();

    }*/
}
