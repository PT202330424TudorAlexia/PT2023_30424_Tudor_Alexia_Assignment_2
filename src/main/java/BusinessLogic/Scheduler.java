package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;


    public Scheduler(int maxNoServers, int maxTasksPerServer, int numberOfClients) {
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;
        this.servers = new ArrayList<Server>();
        for (int i = 0; i < maxNoServers; i++) {
            Server server = new Server(numberOfClients);
            Thread thread = new Thread(server);
            servers.add(server);
            thread.start();

        }

    }

    public void changeStrategy(SelectionPolicy selectionPolicy) {
        if (selectionPolicy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ConcreteStrategyQueue();
        }
        if (selectionPolicy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new ConcreteStrategyTime();
        }
    }

    public void dispatchTask(Task task) {
            strategy.addTask(servers, task);
    }

    public List<Server> getServers() {
        return servers;
    }

}
