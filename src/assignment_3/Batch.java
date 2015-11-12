package assignment_3;


import assignment_2.*;

/**
 * Created by Yuxibro on 15-11-09.
 */
public class Batch {
    int ID;
    int numberOfCustomer;
    int firstIDInBatch;
    int lastIDInBatch;
    double arrival;
    double patience;
    public CustomerStatus status;
    public Batch(int ID, int startingID, int endingID, double a, double p, int numberOfCustomer){
        this.ID = ID;
        this.firstIDInBatch = startingID;
        this.lastIDInBatch = endingID;
        this.arrival = a;
        this.patience = p;
        this.numberOfCustomer = numberOfCustomer;
        this.status = CustomerStatus.Waiting;
        //System.out.println("made batch with arrival: "+this.arrival);
    }
    public void writeState(){
        System.out.println("BatchID: "+this.ID +"\tStatus: "+this.status+"\t#ofCustomer("+this.firstIDInBatch+","+this.lastIDInBatch+"): "+this.numberOfCustomer+
        "\tarrival: "+this.arrival+"\tPatience: "+this.patience );
    }

}
