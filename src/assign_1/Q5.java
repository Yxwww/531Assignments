/**
 *  Arthur: Yuxi Wang
 *  Course: CPSC 531
 *  Assignment: 1
 *  Question:   5
 *  Date: Oct.6th/2015
 *  Description: Using Monte Carlo Simulation Solve Coin Toss Problem
 */



public class Q5{
    public static void main(String[] args){
        System.out.println("Hello world");
        int numberOfExperiments = 100000000;

        MCDice diceInstance = new MCDice();
        System.out.println("Result of "+numberOfExperiments+" experiments yields result of "+
                diceInstance.solve(numberOfExperiments) + " rate of the second rolled dice vaule is larger or equal to " +
                "the first one");

    }
}

class MCDice{
    // MARK:    Properties
    int numberOfExperiments;
    double[] diceCDF;
    int[] diceRolledResult;
    //int diceValue;
    MCRandom randomEngine;
    public MCDice(){
        // random number generator
        randomEngine = new MCRandom();

        //init for rolling dice
        this.numberOfExperiments = -1;
        this.diceCDF = new double[]{0,0.1,0.1,0.2,0.4,0.8,1.0};


    }

    /**
     *  Get probability of X1<=X2 from a collection of rolled result,
     *  My approach is to iterate through the dice value array and compare the current value
     *      to the next value. Record the result. Then finally compute the probability front
     *      the recorded result
     * @param ExperimentsNumber - number of experiments
     * @return
     */
    public double solve(int ExperimentsNumber){
        double result = 0;
        /**
         * Roll the dice 2*Experiment times
         */
        this.numberOfExperiments = ExperimentsNumber;
        int numberOfRoll = this.numberOfExperiments*2;                  // need twice number as throws for the experiment
        System.out.println("#Experiment: "+this.numberOfExperiments+" #toss: "+ numberOfRoll);
        double[] randomedValue = randomEngine.getUniRnd(numberOfRoll);  // Generate normal distributed double value
        this.diceRolledResult = genDiceValueFromDoubleArr(randomedValue);
        /*for(int i=0;i<diceRolledResult.length;i++){
            System.out.print(diceRolledResult[i]+" ,");
        }
        System.out.println();
        */
        // Calculate the "success" rate
        int numberOfSuccessfulCase = 0;

        for(int i = 0; i < this.diceRolledResult.length; i+=2){
            if(this.diceRolledResult[i]<=this.diceRolledResult[i+1]){
                numberOfSuccessfulCase +=1;
            }
            if(i==diceRolledResult.length-2){
                System.out.println(" ... reached the end of sample array");
            }
        }
        return numberOfSuccessfulCase/(float)this.numberOfExperiments;
    }
    public int[] genDiceValueFromDoubleArr(double[] listOfRandomedValue){
        int[] diceValue = new int[listOfRandomedValue.length];
        for(int i=0;i<diceValue.length;i++){
            diceValue[i]=Roll(listOfRandomedValue[i]);
        }
        return diceValue;
    }

    /**
     *
     * @param randomedDouble - double value we need to convert it to dice based
     *                          on CDF
     * @return
     */
    public int Roll(double randomedDouble){
        //int tempRandomNumber =
        //double aRandomDouble = randomEngine.getUniRnd(1);
        if(randomedDouble<=diceCDF[1]&&randomedDouble>diceCDF[0]){
            return 1;
        }else if(randomedDouble>diceCDF[1]&&randomedDouble<=diceCDF[2]){
            return 2;
        }else if(randomedDouble>diceCDF[2]&&randomedDouble<=diceCDF[3]){
            return 3;
        }else if(randomedDouble>diceCDF[3]&&randomedDouble<=diceCDF[4]){
            return 4;
        }else if(randomedDouble>diceCDF[4]&&randomedDouble<=diceCDF[5]){
            return 5;
        }else {
            if(!(randomedDouble>diceCDF[5]&&randomedDouble<=diceCDF[6])){
                System.out.println("OOPS");
            }
            return 6;
        }
    }
}