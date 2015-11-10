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

    double arrival;
    CustomerStatus status;
    double waitTime;
    double startServiceTime;   // wait for how long
    double endServiceTime;
    public Customer(int id, double p, double s, double a){
        ID = id;
        patience = p;
        serviceTime = s;
        waitTime = 0;
        impatient = false;
        served = false;
        this.status = CustomerStatus.Waiting;
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
        this.status = c.status;
    }
    public void getSate(){
        System.out.println("CustomerID: "+ID + "\tPatience:" + this.patience+"\tStatus:"+this.status + "\tService: "
                + serviceTime + "\tImpatient: " + this.impatient +"\tServed: "+this.served +"\tArriveAt: "+this.arrival +" Waited: "+this.waitTime+
            " EndService: "+this.endServiceTime
        );

    }

}

