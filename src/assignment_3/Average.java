package assignment_3;

/**
 * Created by Yuxibro on 15-11-13.
 */
import java.util.*;
public class Average {

    public static void main(String[] args) {
        //int maximumNumber = 10;
        int index = 0;
        double[] data = new double[]{0.9514245141826891,
                0.9649410907602856,
                0.9497273912626044,
                0.9756273127236023,
                0.9815379509182086  };
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
        System.out.println("average          = " + ave);
        System.out.println("sample variance  = " + variance);
        System.out.println("sample standard daviation    = " + standardDaviation);
        System.out.println("approximate confidence interval");
        System.out.println("[ " + lower + ", " + higher + " ]");
    }
}