
package assignment_3;
/**
 * PolarBurger Class
 * 
 * @author 	Majid Ghaderi
 * @version	2.0, Oct 30, 2015
 *
 */
import java.util.*;


public class PolarBurger {
    public LinkedList<Batch> batchQueue;
    public LinkedList<Customer> customerQueue;
    public LinkedList<Event> events;
    int maxQueueLength;
    double meanBatchArrivalRate; // poisson
    double customerInBatchUniformParam;
    double impatienceTimeRate;  // exp
    int cashierServiceTime;
    double meanChefServiceTime;
    double meanChefServiceStd;


    RngStream randStream;
    Random randForNormal;
    boolean chefBusy;
    boolean cashierBusy;
    Customer customerServedByCashier;
    Customer customerServedByChef;
    int customerCounter;
    int batchCounter;

    double busyTimeCounter;
    double timeCounter;
    double runTime;
    int eventCounter;

    /**
     * Default constructor to initialize the web server
	 *
     * @param queue 	Max queue length
     * @param lambda 	Mean arrival rate
     * @param n 		For uniform distribution
     * @param c			Cashier service time
     * @param mu		Mean of chef service time
     * @param sigma		Std of chef service time
     * @param gamma		Mean impatience rate
	 *
     */
	public PolarBurger(int queue, double lambda, int n, int c, 
						double mu, double sigma, double gamma) {
		// To be completed
        this.maxQueueLength = queue;
        this.meanBatchArrivalRate = lambda;
        this.customerInBatchUniformParam = n;
        this.cashierServiceTime = c;
        this.meanChefServiceTime = mu;
        this.meanChefServiceStd = sigma;
        this.impatienceTimeRate = gamma;
        System.out.println("Initialize simulation with maxQueueLength: "+this.maxQueueLength+"\t mean inter-arrival rate: "+
            lambda+" uniform distribution param: "+this.customerInBatchUniformParam+"\tCashier service time:"+this.cashierServiceTime+
                        "\tmean of check service time: "+this.meanChefServiceTime+"\tSTDofChefService: "+this.meanChefServiceStd+"\t" +
                        "meanImpatienceRate: "+this.impatienceTimeRate
        );
        customerQueue = new LinkedList<Customer>();
        batchQueue = new LinkedList<Batch>();
        this.events = new LinkedList<Event>();
        this.randStream = new RngStream("Origin");
        this.randForNormal = new Random();
        customerCounter = 0;
        batchCounter    = 0;
        eventCounter    = 0;
        timeCounter     = 0;
	}
	
    /**
     * Starts the simulation. The simulation runs for 'time' minutes.
	 *
     * @param time 		Length of the simulation run in minutes
     * @param seed 		Random number generator seed
     * 
     * @return	 		Chef utilization
	 *
     */
	public double run(int time, int seed) {
		// Init:
        double utilization = 0;
        this.randStream.setSeed(new long[]{seed,seed,seed,seed,seed,seed}); // set seed for uniform random
        randForNormal.setSeed(seed);     //set seed for Gaussian
        timeCounter = 0;     // How long since the simulation started.
        customerCounter = 0;
        batchCounter    = 0;
        eventCounter    = 0;
        // END of Init


        //TODO: Simulation
        createBatch();
        while(Math.ceil(timeCounter)<time){ // while still have more time

            timeCounter++;
        }
        //events.remove().writeState();
        this.writeAllEventsState(this.events);
        this.writeAllCustomerState(this.customerQueue);
        this.writeAllBatchState(this.batchQueue);
		return utilization;
	}

    /*
        For patience time and for inter-arrival rate.
     */
    public double getArrivalExpoRandom (){
        return Math.log(1-this.randStream.randU01())/(-this.meanBatchArrivalRate);
    }
    public double getImpatienceExpoRandom (){
        return Math.log(1-this.randStream.randU01())/(-this.impatienceTimeRate);
    }

    public int getCustomerNumberUniformRandom(){
        return (int)Math.ceil(this.customerInBatchUniformParam*this.randStream.randU01());
    }
    public double getChefNormalRandom(){
        return this.randForNormal.nextGaussian()*this.meanChefServiceStd+this.meanChefServiceTime;
    }

    public void writeAllCustomerState(LinkedList<Customer> list){
        ListIterator<Customer> customerIterator = list.listIterator();
        while (customerIterator.hasNext()) {
            customerIterator.next().getSate();
        }
    }
    public void writeAllBatchState(LinkedList<Batch> list){
        ListIterator<Batch> batchIterator = list.listIterator();
        while (batchIterator.hasNext()) {
            batchIterator.next().writeState();
        }
    }
    public void writeAllEventsState(LinkedList<Event> list){
        ListIterator<Event> eventsIterator = list.listIterator();
        while (eventsIterator.hasNext()) {
            eventsIterator.next().writeState();
        }
    }


    public void createBatch(){
        int numberOfCustomerInBatch = this.getCustomerNumberUniformRandom();
        System.out.println("Creating: "+numberOfCustomerInBatch+" customers");
        //Create batch
        Batch batch = new Batch(batchCounter,this.customerCounter,(this.customerCounter+numberOfCustomerInBatch),(this.timeCounter+this.getArrivalExpoRandom()),this.getImpatienceExpoRandom(),numberOfCustomerInBatch);
        batchQueue.add(batch);
        //Create arrival event
        Event firstArrival = new Event(eventCounter,this.timeCounter+batch.arrival,EventType.Arrival);
        this.events.add(firstArrival);
        //Create Customer in Batch
        if (numberOfCustomerInBatch > 0) {
            for (int customerIndex = 0; customerIndex < numberOfCustomerInBatch; customerIndex++) {
                Customer aNewCustomer = new Customer(this.customerCounter, batch.patience, -1, batch.arrival);  // give service time after they become patient
                //aNewCustomer.getSate();
                customerQueue.add(aNewCustomer);
                System.out.println(numberOfCustomerInBatch);
            }
        }
        eventCounter++;
        this.batchCounter ++;
        this.customerCounter+=numberOfCustomerInBatch;
    }



}
