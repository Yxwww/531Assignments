package assignment_2;

/**
 * assignment_2.PolarBurger Class
 * 
 * @author 	Majid Ghaderi
 * @version	1.0, Oct 6, 2015
 *
 */
import java.util.*;


public class PolarBurger {
    public LinkedList<Customer> customerQueue;
    double meanArrival ;
    double meanService ;
    double meanImpatience;
    RngStream randStream;
    public LinkedList<Customer> customerWaitingQueue;
    boolean busy;
    Customer customerBeingServed;

    int busyTimeCounter;
    int numberOfCustomerIterationSum;
    int runTime;
    /**
     * Default constructor to initialize the web server
	 * Precondition: 
	 *
     * @param lambda 	Mean arrival rate
     * @param mu		Mean serviceTime rate
     * @param gamma		Mean impatience rate
     */
	public PolarBurger(double lambda, double mu, double gamma) {
		// To be completed
        this.meanArrival = lambda;
        this.meanService = mu;
        this.meanImpatience = gamma;
        customerQueue = new LinkedList<Customer>();
        customerWaitingQueue = new LinkedList<Customer>();
        busy = false;
        randStream = new RngStream("Origin");       // according API default seed is [12345,12345,12345,12345,12345,12345]
        numberOfCustomerIterationSum = 0;
        runTime = 0;
        busyTimeCounter = 0;
    }
	
    /**
     * Starts the simulation. The simulation runs for 'time' minutes.
	 *
     * @param time 		Length of the simulation run in minutes
     * @param seed 		Random number generator seed
     */
	public void run(int time, int seed) {
		// To be completed
        // Initialize for run
        randStream.setSeed(new long[]{seed,seed,seed,seed,seed,seed}); // they use 6 dimensions seed for some reason
        int numberOfCustomersJoined = 0;
        int numberOfCustomerLeft = 0;
        this.busyTimeCounter = 0; // busy time counter should belong each run
        this.customerQueue = new LinkedList<Customer>();
        customerQueue = new LinkedList<Customer>();
        customerWaitingQueue = new LinkedList<Customer>();
        busy = false;
        randStream = new RngStream("Origin");       // according API default seed is [12345,12345,12345,12345,12345,12345]
        numberOfCustomerIterationSum = 0;
        this.runTime = time;

        for(int timeIteration=0;timeIteration<time;timeIteration++) {
            int numberOfCustomer = this.getPoissonRandom(this.meanArrival, this.randStream); // how many Customer comes in this minute
            // System.out.println("#Customer: "+numberOfCustomer);
            // Handling new customer
            if (numberOfCustomer > 0) {
                for (int customerIndex = 0; customerIndex < numberOfCustomer; customerIndex++) {
                    Customer aNewCustomer = new Customer(numberOfCustomersJoined, getExpoRandom(this.meanImpatience, randStream), getExpoRandom(this.meanService, randStream),timeIteration);
                    //aNewCustomer.getSate();
                    customerQueue.add(aNewCustomer);
                    numberOfCustomersJoined++;
                }
            }
            // TODO: check if any customer is leaving Service
            ListIterator<Customer> customerIterator = customerQueue.listIterator();
            while (customerIterator.hasNext()) {
                //customerIterator.next().getSate();
                Customer tempCustomer = customerIterator.next();
                if(tempCustomer.served==false && tempCustomer.impatient==false) {
                    // check if this customer is still waiting ie not served && not left
                    if ((timeIteration - tempCustomer.arrival) > tempCustomer.patience) {
                        System.out.println(tempCustomer.ID + " Impatient!! since she arrives at "+tempCustomer.arrival +" waited for "+(timeIteration - tempCustomer.arrival));
                        customerQueue.get(tempCustomer.ID).impatient = true;
                        customerQueue.get(tempCustomer.ID).waitTime = (int)customerQueue.get(tempCustomer.ID).patience;
                        numberOfCustomerLeft++;
                    }
                }
            }
            //TODO: Update Customer Waiting Queue
            //bussinessHandler();
            if(!busy){
                int firstUnServedPatientCustomerID = this.findFirstUnServedPatientCustomerID();

                if(firstUnServedPatientCustomerID != -1){
                    // found a unServed patient customer
                    //Customer firstUnServedPatientCustomer = new Customer(customerQueue.get(firstUnServedPatientCustomerID));
                    //firstUnServedPatientCustomer.served=true;
                    //firstUnServedPatientCustomer.getSate();
                    Customer firstUnServedPatientCustomer = customerQueue.get(firstUnServedPatientCustomerID); // pass by reference
                    firstUnServedPatientCustomer.served = true;
                    firstUnServedPatientCustomer.waitTime = timeIteration - firstUnServedPatientCustomer.arrival;
                    firstUnServedPatientCustomer.startServiceTime = timeIteration;
                    firstUnServedPatientCustomer.endServiceTime = timeIteration+(int)firstUnServedPatientCustomer.serviceTime;
                    //customerQueue.get();
                    customerBeingServed = firstUnServedPatientCustomer;
                    System.out.println("Serving customer:" + customerBeingServed.ID + " at time " + timeIteration);
                    this.busy = true;
                    busyTimeCounter++;
                }
            }else{
                //While server is busy
                if(customerBeingServed.endServiceTime==timeIteration){
                    this.busy = false;// CustomerLeft
                    customerBeingServed = null;
                    numberOfCustomerLeft++;
                    // find another customer to serve
                    int firstUnServedPatientCustomerID = this.findFirstUnServedPatientCustomerID();
                    if(firstUnServedPatientCustomerID != -1){
                        Customer firstUnServedPatientCustomer = customerQueue.get(firstUnServedPatientCustomerID); // pass by reference
                        firstUnServedPatientCustomer.served = true;
                        firstUnServedPatientCustomer.waitTime = timeIteration - firstUnServedPatientCustomer.arrival;
                        firstUnServedPatientCustomer.startServiceTime = timeIteration;
                        firstUnServedPatientCustomer.endServiceTime = timeIteration+(int)firstUnServedPatientCustomer.serviceTime;
                        //customerQueue.get();
                        customerBeingServed = firstUnServedPatientCustomer;
                        System.out.println("Serving customer:" + customerBeingServed.ID + " at time " + timeIteration);
                        this.busy = true;
                        busyTimeCounter++;
                    }
                }else{
                    // still serving this customer
                    busyTimeCounter++;
                }
            }
            System.out.println(numberOfCustomersJoined + " - " + numberOfCustomerLeft);
            this.numberOfCustomerIterationSum += (numberOfCustomersJoined-numberOfCustomerLeft);
        }// END of time interation
        //this.writeAllCustomerState(this.customerQueue);
        System.out.println();
    }

    /**
     * Generates a report on the performance measures of interest.
     *
     */
    public Report getReport() {
        // To be completed
        Report report = new Report();
        report.numOfCustomers = (double)this.numberOfCustomerIterationSum / this.runTime; // number of user in each minute divide by time length
        report.patientCustomersWait = getAVGPatientCustomersWait(this.customerQueue);
        report.impatientCustomersWait = getAVGImpatientCustomersWait(this.customerQueue);
        report.impatientCustomersRatio = (double)getNumberOfImpatientCustomers(this.customerQueue)/this.customerQueue.size();
        report.chefUtilization = (double)this.busyTimeCounter/this.runTime;
        return report;
    }

    //Reference: https://en.wikipedia.org/wiki/Poisson_distribution#Generating_Poisson-distributed_random_variables   by Dr. Knuth
    public int getPoissonRandom(double mean,RngStream randStream) {
        Random r = new Random();
        double L = Math.exp(-mean);
        int k = 0;
        double p = 1.0;
        do {
            p = p * randStream.randU01(); //r.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }
    public double getExpoRandom (double mean,RngStream randomStream){
        //Random r = new Random();
        return Math.ceil(Math.log(1-randomStream.randU01())/(-mean));
    }

    public void writeAllCustomerState(LinkedList<Customer> list){
        ListIterator<Customer> customerIterator = list.listIterator();
        while (customerIterator.hasNext()) {
            customerIterator.next().getSate();
        }
    }
    public int findFirstUnServedPatientCustomerID(){
        ListIterator<Customer> customerIterator = customerQueue.listIterator();
        while (customerIterator.hasNext()) {
            Customer tempCustomer = customerIterator.next();

            if(tempCustomer.impatient==false && tempCustomer.served==false){
                return tempCustomer.ID;
            }
        }// END of while
        return -1;
    }

    public int getNumberOfCustomerInSystem(){
        int numberOfCustomerInSystem = 0;
        ListIterator<Customer> customerIterator = customerQueue.listIterator();
        while (customerIterator.hasNext()) {
            Customer tempCustomer = customerIterator.next();
            if(tempCustomer.impatient==false && tempCustomer.served==false){
                numberOfCustomerInSystem++;
            }
        }// END of while
        //System.out.println(numberOfCustomerInSystem);
        return numberOfCustomerInSystem;
    }


    public double getAVGPatientCustomersWait(LinkedList<Customer> c){
        // get number Of Patient Customer
        int numberOfPatientCustomerInSystem = 0;
        int sumOfWaitTime = 0;
        ListIterator<Customer> customerIterator = c.listIterator();
        while (customerIterator.hasNext()) {
            Customer tempCustomer = customerIterator.next();
            if(tempCustomer.impatient==false && tempCustomer.served==true){
                numberOfPatientCustomerInSystem++;
                sumOfWaitTime+=tempCustomer.waitTime;
            }
        }// END of while
        //System.out.println(numberOfPatientCustomerInSystem +" "+sumOfWaitTime);
        return (double)sumOfWaitTime / numberOfPatientCustomerInSystem;
    }
    public double getAVGImpatientCustomersWait(LinkedList<Customer> c){
        int numberOfImpatientCustomerInSystem = 0;
        int sumOfWaitTime = 0;
        ListIterator<Customer> customerIterator = c.listIterator();
        while (customerIterator.hasNext()) {
            Customer tempCustomer = customerIterator.next();
            if(tempCustomer.impatient==true && tempCustomer.served==false){
                numberOfImpatientCustomerInSystem++;
                sumOfWaitTime+=tempCustomer.waitTime;
            }
        }// END of while
        //System.out.println(numberOfImpatientCustomerInSystem +" "+sumOfWaitTime);
        return (double)sumOfWaitTime / numberOfImpatientCustomerInSystem;
    }

    public int getNumberOfImpatientCustomers(LinkedList<Customer> c){
        int numberOfImpatientCustomerInSystem = 0;
        ListIterator<Customer> customerIterator = c.listIterator();
        while (customerIterator.hasNext()) {
            Customer tempCustomer = customerIterator.next();
            if(tempCustomer.impatient==true && tempCustomer.served==false){
                numberOfImpatientCustomerInSystem++;
            }
        }// END of while
        //System.out.println("Impatient customer in system: "+ numberOfImpatientCustomerInSystem);
        return numberOfImpatientCustomerInSystem;
    }
}
