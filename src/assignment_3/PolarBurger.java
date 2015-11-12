
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
    int maxRunTime;
    double meanBatchArrivalRate; // poisson
    double customerInBatchUniformParam;
    double impatienceTimeRate;  // exp
    int cashierServiceTime;
    double meanChefServiceTime;
    double meanChefServiceStd;


    RngStream randStream;
    Random randForNormal;

    /* store state */
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
        maxRunTime      = 0;
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
        this.maxRunTime = time;
        // END of Init

        //TODO: Simulation
        createBatch();  // create first batch with arrival event
        createLeftToChefEvent();
        while(Math.ceil(timeCounter)<time){ // while still have more time
            Event nextEvent = this.events.remove();
            System.out.println("Time: "+this.timeCounter);
            /*
                Check if events type of the next event
                    if event is batch Arrival
             */
            if(nextEvent.eventType==EventType.Arrival) {
                System.out.println("<<<<<<<<  Arrival  >>>>>>>>");
                this.timeCounter = nextEvent.time;    // update time before creating event for the next batch

                System.out.println("Arrival of: " + nextEvent.subjectID);
                Batch arrivalBatch =this.batchQueue.get(nextEvent.subjectID);
                /*
                        Check queue length
                  */
                if((arrivalBatch.numberOfCustomer+getNumberOfCustomerWithStatus(CustomerStatus.Waiting))>this.maxQueueLength){
                    System.out.println("Batch: "+nextEvent.subjectID+" left since, queue is full");
                    updateCustomerStatusWithBachID(arrivalBatch.ID, CustomerStatus.LeftCuzQueueFull);
                    //arrivalBatch.changeAllCustomerStatus(CustomerStatus.Le);
                }
                createBatch();                      // schedule the next event
            }



            /*
                    Check Patience
             */
            ListIterator<Batch> batchListIterator = this.batchQueue.listIterator();
            while (batchListIterator.hasNext()) {
                //customerIterator.next().getSate();
                Batch batchElement = batchListIterator.next();
                // if the batch is waiting and the time has exceed the
                if(batchElement.status == CustomerStatus.Waiting){
                    if((batchElement.arrival+batchElement.patience)<this.timeCounter){
                        // when current time is exceeds patience time of the batch
                        updateCustomerStatusWithBachID(batchElement.ID, CustomerStatus.LeftCusImpatient);
                        System.out.println("Impatient! "+batchElement.ID +" Arrive: "+batchElement.arrival+" Patience: "+batchElement.patience);
                    }
                }
            }

            System.out.println();
        }
        //events.remove().writeState();

        System.out.println("\n\t----  RESULT ----");
        this.writeAllEventsState(this.events);
        this.writeAllCustomerState(this.customerQueue);
        this.writeAllBatchState(this.batchQueue);

		return utilization;
	}

    public void createLeftToChefEvent(){
        //Customer firstUnServedWaitingCustomer = this.customerQueue.get(getFirstCustomerIDWithStatus(CustomerStatus.Waiting));
        //System.out.pritnln();C
        Event e1 = new Event(1,0.1,EventType.Arrival,1,0);
        Event e2 = new Event(2,0.2,EventType.Arrival,1,0);
        Event e3 = new Event(3,0.3,EventType.Arrival,1,0);
        this.events.add(e1);

        Event e4 = new Event(4,0.3,EventType.Arrival,1,0);
        int t4  = getEventToChronologicalIndex(e4);
        System.out.println("(t4) get index: " + t4);
        this.events.add(t4,e4);

        int t2 = getEventToChronologicalIndex(e2);
        System.out.println("(t2) get index: " + t2);
        this.events.add(getEventToChronologicalIndex(e2),e2);

        int t3 = getEventToChronologicalIndex(e3);
        System.out.println("(t3) get index: " + t3);
        this.events.add(getEventToChronologicalIndex(e3),e3);

        writeAllEventsState(this.events);


    }

    public void arrangeServiceEventsForBatch(int batchID){

    }

    /*
        Update the customer status and batch status with ID
     */
    public void updateCustomerStatusWithBachID(int BachID, CustomerStatus s){
        Batch batchOfInterest = this.batchQueue.get(BachID);
        batchOfInterest.status = s;
        for(int i=batchOfInterest.firstIDInBatch;i<batchOfInterest.lastIDInBatch;i++){
            this.customerQueue.get(i).status=s;
        }
    }

    public int getNumberOfCustomerWithStatus(CustomerStatus s){
        int numberOfInterestedCustomer = 0;
        ListIterator<Customer> customerListIterator = this.customerQueue.listIterator();
        while (customerListIterator.hasNext()) {
            //customerIterator.next().getSate();
            Customer customerElement = customerListIterator.next();
            // if the batch is waiting and the time has exceed the
            if(customerElement.status == s){
                numberOfInterestedCustomer++;
            }
        }
        return numberOfInterestedCustomer;
    }

    public int getFirstCustomerIDWithStatus(CustomerStatus s){
        ListIterator<Customer> customerListIterator = this.customerQueue.listIterator();
        while (customerListIterator.hasNext()) {
            //customerIterator.next().getSate();
            Customer customerElement = customerListIterator.next();
            // if the batch is waiting and the time has exceed the
            if(customerElement.status == s){
                return customerElement.batchID;
            }
        }
        return -1;
    }
    public int getEventToChronologicalIndex(Event newEvent){
        int correctIndex = 0;
        ListIterator<Event> eventListIterator = this.events.listIterator();
        for(int i=0; i<this.events.size(); i++)
        {
            Event current = this.events.get(i);
            if(i==this.events.size()-1){
                if(newEvent.time>current.time){
                    return current.ID;
                }else{
                    return current.ID-1;
                }
            }else{
                Event next = this.events.get(i+1);
                if(newEvent.time>current.time&&newEvent.time<next.time){
                    return current.ID;
                }
            }

        }
        return 0;
    }


    /*
        For patience time and for inter-arrival rate.
     */
    public double getArrivalExpoRandom (){
        //System.out.println("**");
        return Math.log(1-this.randStream.randU01())/(-this.meanBatchArrivalRate);
    }
    public double getImpatienceExpoRandom (){
        //System.out.println("^^");
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


    /*
        Create a batch, and create the arrival event associate with it.
     */
    public Batch createBatch(){
        if(this.timeCounter>this.maxRunTime){
            return null;
        }
        int numberOfCustomerInBatch = this.getCustomerNumberUniformRandom();
        System.out.println("Creating: "+numberOfCustomerInBatch+" customers");
        //Create arrival event
        Event nextArrival = new Event(eventCounter,(this.timeCounter+this.getArrivalExpoRandom()),EventType.Arrival,1,batchCounter);
        this.events.add(nextArrival);

        //Create batch
        Batch batch = new Batch(batchCounter,this.customerCounter,(this.customerCounter+numberOfCustomerInBatch),nextArrival.time,this.getImpatienceExpoRandom(),numberOfCustomerInBatch);
        batchQueue.add(batch);

        //Create Customer in Batch
        if (numberOfCustomerInBatch > 0) {
            for (int customerIndex = 0; customerIndex < numberOfCustomerInBatch; customerIndex++) {
                Customer aNewCustomer = new Customer(this.customerCounter, batch.patience, -1, batch.arrival,batch.ID);  // give service time after they become patient
                //aNewCustomer.getSate();
                customerQueue.add(aNewCustomer);
                //System.out.println(numberOfCustomerInBatch);
                this.customerCounter ++;
            }
        }
        eventCounter++;
        this.batchCounter ++;
        return batch;
    }



}
