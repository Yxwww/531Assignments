
package assignment_3;
/**
 * PolarBurger Class
 * 
 * @author 	Majid Ghaderi
 * @version	2.0, Oct 30, 2015
 *
 */

public class PolarBurger {
    int maxQueueLength;
    double meanBatchArrivalTime; // poisson
    double customerInBatchUniformParam;
    double meanImpatienceTime;  // exp
    int cashierServiceTime;
    double meanChefServiceTime;
    double meanChefServiceStd;


    RngStream randStream;
    boolean chefBusy;
    boolean cashierBusy;
    Customer customerServedByCashier;
    Customer customerServedByChef;

    int busyTimeCounter;
    int runTime;

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
        this.meanBatchArrivalTime = lambda;
        this.customerInBatchUniformParam = n;
        this.cashierServiceTime = c;
        this.meanChefServiceTime = mu;
        this.meanChefServiceStd = sigma;
        this.meanImpatienceTime = gamma;
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
		double utilization = 0;
		// To be completed
		return utilization;
	}
	
}
