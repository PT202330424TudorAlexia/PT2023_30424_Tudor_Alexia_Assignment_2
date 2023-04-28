package Model;

import java.util.concurrent.atomic.AtomicInteger;

public class Task {
    private static AtomicInteger idfinal=new AtomicInteger(0);
    private int id=0;
    private int arrivalTime;
    private int serviceTime;

    public Task(int arrivalTime,int serviceTime){
        this.arrivalTime=arrivalTime ;
        this.serviceTime=serviceTime ;
        this.id=idfinal.getAndIncrement();
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
