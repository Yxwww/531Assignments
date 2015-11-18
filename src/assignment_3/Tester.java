

package assignment_3;

/**
 * A simple test driver
 * 
 * @author 	Majid Ghaderi
 * @version	1.0, Oct 30, 2015
 *
 */
 
public class Tester {
	
	public static void main(String[] args) {


    /*
        double lambda = 1.0/5;
        int n = 4;
        int c = 1;
        double mu = 5;
        double sigma = 1;
        double gamma = 1.0/30;
        int time = 10;

        // seeds for 4 runs
        //int[] seed = {135, 351, 739, 937};
        int seed = 135;
        int queue = 10;



        PolarBurger sim = new PolarBurger(queue, lambda, n, c, mu, sigma, gamma);
        for(int i=0;i<5;i++){
            System.out.print(sim.randStream.randU01()+" ");
        }
        sim.randStream.writeStateFull();
        sim.randStream.resetNextSubstream();
        sim.randStream.writeStateFull();

        System.out.println("Util: "+sim.run(10,135));
        */

        double lambda = 0.2;//1.0 / 5;
        int n = 4;
        int c = 1;
        double mu = 5;
        double sigma = 1;
        double gamma = 1.0 / 30;
        int time = 10000;

        // seeds for 4 runs
        int[] seed = {135, 351, 739, 937, 227 , 777 , 1361 };
        double[] results = new double[100];
        double[] resultsInRun = new double[5];
        // start and run the simulation
        // compute utilization for different queue lengths
        for (int queue = 1; queue < 100; queue++) {
            resultsInRun = new double[seed.length];
            PolarBurger sim = new PolarBurger(queue, lambda, n, c, mu, sigma, gamma);

            // compute average over 4 runs
            double util = 0;
            int resultCounter = 0;
            for (int s : seed){
                double result = sim.run(time, s);
                util += result;
                System.out.println(result+" ");
                resultsInRun[resultCounter]= result;
                resultCounter++;
            }
            results[queue] = util / seed.length;


            // print the average utilization
            System.out.printf(" queue = %d \t utilization = %f \n", queue, util / seed.length);

            /*if(Math.abs(results[queue]-results[queue-1])<0.005){
                break;
            }*/

            if(Math.abs(getWidthOfCI(resultsInRun))<0.01){
                break;
            }
            System.out.println();

        }

    }

    public static double getWidthOfCI(double[] results) {
        //int maximumNumber = 10;
        int index = 0;
        double[] data = results;
        // first pass: read in data, compute sample mean
        double dataSum = 0.0;
        while (index<data.length) {
            dataSum  += data[index];
            index++;
        }
        double ave = dataSum / data.length;

        double variance1 = 0.0;
        for (int i = 0; i < index; i++) {
            variance1 += (data[i] - ave) * (data[i] - ave);
        }
        double variance = variance1 / (index - 1);
        double standardDaviation= Math.sqrt(variance);
        double lower = ave - 1.96 * standardDaviation;
        double higher = ave + 1.96 * standardDaviation;

        // print results
        /*System.out.println("average          = " + ave);
        System.out.println("sample variance  = " + variance);
        System.out.println("sample standard daviation    = " + standardDaviation);
        System.out.println("approximate confidence interval");*/
        System.out.println("[ " + lower + ", " + higher + " ]");
        System.out.println("Width: "+(higher-lower));
        return higher-lower;
    }
	
}
