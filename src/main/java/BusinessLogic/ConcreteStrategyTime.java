package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcreteStrategyTime implements Strategy {

    @Override
    public void addTask(List<Server> servers, Task t) {
        AtomicInteger min = new AtomicInteger(Integer.MAX_VALUE);
        Server need = new Server(2000);
        for (Server s : servers) {
            AtomicInteger length = s.getWaitingPeriod();
            if (length.get() < min.get()) {
                min = length;
                need = s;
            }

        }
        need.addTask(t);

    }
}

