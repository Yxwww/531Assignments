

package assignment_3;

import assignment_2.*;

/**
 * A simple test driver
 * 
 * @author 	Majid Ghaderi
 * @version	1.0, Oct 30, 2015
 *
 */
 
public class Tester {
	
	public static void main(String[] args) {
        double lambda = 1.0/5;
        int n = 4;
        int c = 1;
        double mu = 5;
        double sigma = 1;
        double gamma = 1.0/30;
        int time = 1000;

        // seeds for 4 runs
        int[] seed = {135, 351, 739, 937};
        int queue = 10;
        PolarBurger sim = new PolarBurger(queue, lambda, n, c, mu, sigma, gamma);
        sim.run(10,135);

        for(int i=0;i<100; i++){
            System.out.print(" "+sim.getImpatienceExpoRandom());
        }
        //sim.randStream.advanceState(5,3);

        //System.out.println(sim.randStream.randU01());

        // start and run the simulation
        // compute utilization for different queue lengths



		// initial values for various parameters
		/*
		double lambda = 1.0/5;

		int n = 4;
		int c = 1;
		double mu = 5;
		double sigma = 1;
		double gamma = 1.0/30;
		int time = 1000;
		
		// seeds for 4 runs
		int[] seed = {135, 351, 739, 937};
		
		// start and run the simulation
		// compute utilization for different queue lengths
		for (int queue = 1; queue < 100; queue++) {
			PolarBurger sim = new PolarBurger(queue, lambda, n, c, mu, sigma, gamma);
			
			// compute average over 4 runs
			double util = 0;
			for (int s : seed) 
				util += sim.run(time, s);
			
			// print the average utilization
			System.out.printf(" queue = %d \t utilization = %f \n", queue, util / seed.length);
		}
		*/
	}
	
}
