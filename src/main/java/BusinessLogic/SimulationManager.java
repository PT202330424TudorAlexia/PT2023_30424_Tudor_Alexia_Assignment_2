package BusinessLogic;

import GUI.SimulationFrame;
import Model.Server;
import Model.Task;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class SimulationManager implements Runnable {
    public int timeLimit = 100;
    public int maxProcessingTime = 10;
    public int minProcessingTime = 2;
    public int numberOfServers = 3;
    public int numberOfClients = 100;
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;

    private Scheduler scheduler;
    private SimulationFrame frame;
    private List<Task> generatedTasks; //waiting clients

    public SimulationManager() {
        this.scheduler = new Scheduler(numberOfServers, numberOfClients);

        // create and start numberofServers threads
        for (int i=0;i<numberOfServers;i++){
            Server server=new Server();
            Thread servers=new Thread(server);
            servers.start();
        }


        //initialize selection strategy=>createStrategy
        createStrategy(selectionPolicy);

        // initialize frame to display simulation
        //SimulationFrame sim=new SimulationFrame();

        // generate numberOfClients clients using generateNRandomtasks()
        generateNRandomTasks(numberOfClients,minProcessingTime,maxProcessingTime,timeLimit);
        // and store them to generatedTasks
    }

    private void createStrategy(SelectionPolicy selectionPolicy) {
        scheduler.changeStrategy(selectionPolicy);
    }

    private void generateNRandomTasks(int numberOfClients, int minProcessingTime, int maxProcessingTime, int timeLimit) {
        int nrprocessing, nrarrival;
        Random random_nr = new Random();
        int i = 0;
        while (i < numberOfClients) {
            nrprocessing = random_nr.nextInt(maxProcessingTime - minProcessingTime + 1) + minProcessingTime;

            nrarrival = random_nr.nextInt(timeLimit - 1 + 1) + 1;
            Task task=new Task(nrarrival,nrprocessing);
            generatedTasks.add(task);

            i++;
        }

        generatedTasks.sort(Comparator.comparing(Task::getArrivalTime));

    }

    @Override
    public void run() {
        int currentTime = 0;
        while (currentTime < timeLimit) {
            Task picked;
            for (Task t: generatedTasks) {
                if(t.getArrivalTime()==currentTime)
                {
                    scheduler.dispatchTask(t);
                    generatedTasks.remove(t);
                }
                currentTime++;

                try {
                    wait(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

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

    public static void main() {
        SimulationManager gen = new SimulationManager();
        Thread t = new Thread(gen);
        t.start();

    }
}
