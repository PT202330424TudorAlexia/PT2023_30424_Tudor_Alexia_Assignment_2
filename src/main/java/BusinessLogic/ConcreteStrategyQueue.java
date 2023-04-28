package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.List;

public class ConcreteStrategyQueue implements Strategy{


    @Override
    public void addTask(List<Server> servers, Task t) {
        int min=Integer.MAX_VALUE;
        Server need= new Server(200);
        for (Server s:servers) {
            int length=s.getNumberTasks();
            if(s.getNumberTasks()==0)
                min=0;
            if(length<min)
            min=length;
            need=s;
        }
        need.addTask(t);
    }
}
