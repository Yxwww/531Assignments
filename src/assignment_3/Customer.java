package assignment_3;

/**
 * Created by Yuxibro on 15-10-30.
 */
public class Customer {
    int ID;
    double patience;
    double serviceTime;

    boolean impatient;  // states
    boolean served;     // state

    int arrival;

    int waitTime;
    int startServiceTime;   // wait for how long
    int endServiceTime;
    public Customer(int id, double p, double s, int a){
        ID = id;
        patience = p;
        serviceTime = s;
        waitTime = 0;
        impatient = false;
        served = false;
        arrival = a;
        startServiceTime = -1;
        endServiceTime = -1;
    }

    /**
     *  Copy constructor
     */
    public Customer(Customer c){
        this.ID = c.ID;
        this.patience = c.patience;
        this.serviceTime = c.serviceTime;
        this.waitTime = c.waitTime;
        this.impatient = c.impatient;
        this.served = c.served;
        this.arrival = c.arrival;
        this.startServiceTime = c.startServiceTime;
    }
    public void getSate(){
        System.out.println("ID: "+ID + "\tPatience:" + this.patience + "\tService: "
                + serviceTime + "\tImpatient: " + this.impatient +"\tServed: "+this.served +"\tArriveAt: "+this.arrival +" Waited: "+this.waitTime+
            " EndService: "+this.endServiceTime
        );

    }

}

