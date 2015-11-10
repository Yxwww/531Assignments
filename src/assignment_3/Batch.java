package assignment_3;


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
    public Batch(int ID, int startingID, int endingID, double a, double p, int numberOfCustomer){
        this.ID = ID;
        this.firstIDInBatch = startingID;
        this.lastIDInBatch = endingID;
        this.arrival = a;
        this.patience = p;
        this.numberOfCustomer = numberOfCustomer;
    }
    public void writeState(){
        System.out.println("BatchID: "+this.ID +"\t#ofCustomer: "+this.numberOfCustomer+
        "\t arrival: "+this.arrival+"\tPatience"+this.patience);
    }
}
