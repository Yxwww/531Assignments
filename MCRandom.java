
/**
 * MCRandom Class
 * 
 * @author 	Majid Ghaderi
 * @version	1.0, Sep 25, 2015
 *
 */

import java.util.Random;

public class MCRandom {
	
	// the seed value used for Java random number generator
	private final static int seed = 97153;
	
	// two Random objects, one for continuous, the other one for discrete case
	private Random random1;
	private Random random2;
	
	
    /**
     * MCRandom constructor to initialize random number generators.
	 *
     */
	public MCRandom() {
		random1 = new Random(seed);
		random2 = new Random(seed + 100000);
	}
	
    /**
     * Returns an integer array of uniform discrete random numbers between lower (inclusive) and upper (exclusive).
	 *
     * @param size 	length of the output array
     * @param lower	the lower limit of the random numbers (inclusive)
     * @param upper	the upper limit of the ransom numbers (exclusive)  
	 * @return an integer array of uniformly distributed random numbers
     */
	public int[] getUniRnd(int size, int lower, int upper) {
		return random1.ints(size, lower, upper).toArray();
	}
	
    /**
     * Returns a double array of uniformly distributed random numbers between 0 (inclusive) and 1 (exclusive).
	 *
     * @param size	length of the output array
	 * @return a double array of uniformly distributed random numbers
     */
	public double[] getUniRnd(int size) {
		return random2.doubles(size).toArray();
	}
	
	
    /**
     * A simple test driver showing how to use MCRandom class.
	 *
     */
	public static void main(String[] args) {
		int n = 10000;
		
		MCRandom random = new MCRandom();
		
		int[] items = random.getUniRnd(n, 0, 20);
		double[] u = random.getUniRnd(n);
		
		for (int i =0; i < n; i++)
			System.out.println(i + "\t" + items[i] + "\t" + u[i]);
	}
	
}
