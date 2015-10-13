

/**
 * PolarBurger Class
 * 
 * @author 	Majid Ghaderi
 * @version	1.0, Oct 6, 2015
 *
 */

public class PolarBurger {

    /**
     * Default constructor to initialize the web server
	 * Precondition: 
	 *
     * @param lambda 	Mean arrival rate
     * @param mu		Mean service rate
     * @param gamma		Mean impatience rate
     */
	public PolarBurger(double lambda, double mu, double gamma) {
		// To be completed
	}
	
    /**
     * Starts the simulation. The simulation runs for 'time' minutes.
	 *
     * @param time 		Length of the simulation run in minutes
     * @param seed 		Random number generator seed
     */
	public void run(int time, int seed) {
		// To be completed
	}
	
    /**
     * Generates a report on the performance measures of interest.
	 *
     */
	public Report getReport() {
		// To be completed
		return new Report();
	}
	
}
