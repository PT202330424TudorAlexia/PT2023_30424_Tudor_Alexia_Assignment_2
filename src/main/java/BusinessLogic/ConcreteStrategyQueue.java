package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.List;

public class ConcreteStrategyQueue implements Strategy{


    @Override
    public void addTask(List<Server> servers, Task t) {
        int min=10000;
        for (Server s:servers) {
            int length=s.getNumberTasks();
            if(length<min)
            min=length;
        }
    }
}
