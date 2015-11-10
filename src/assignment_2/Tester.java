package assignment_2;

/**
 * A simple test driver
 * 
 * @author 	Majid Ghaderi
 * @version	1.0, Oct 6, 2015
 *
 */
//import org.uncommons.*;


public class Tester {
	
	public static void main(String[] args) {
        /*PolarBurger simTest = new PolarBurger(0.5,0.6,0.2);
        simTest.run(100,135);
        simTest.getReport().WriteState();
        */
        PolarBurger sim = new PolarBurger(0.5, 0.6, 0.2);
        RngStream random = new RngStream();
        for(int i=0;i<100;i++){
            System.out.print(sim.getPoissonRandom(0.5,random) +" ");
        }

		// Final tests
		/*
        int[] seed = {135, 351, 739, 937};
		//int[] time = {1000, 10000};
        int[] time = {0};
		PolarBurger sim = new PolarBurger(0.5, 0.6, 0.2);
		
		// start and run the simulation
		for (int t : time) {
			double numOfCustomers = 0;
			
			for (int s : seed) {
				sim.run(t, s);
				Report report = sim.getReport();
				report.WriteState();
				numOfCustomers += report.numOfCustomers;
			}
			
			System.out.printf("Sim Length = %d \t Avg No Customers = %5.2f \n", t, numOfCustomers / seed.length);
		}
		*/
	}
	
}
