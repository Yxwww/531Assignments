/**
 *  Arthur: Yuxi Wang
 *  Course: CPSC 531
 *  Assignment: 1
 *  Question:   6
 *  Date: Oct.6th/2015
 *  Description: Using monte carlo simulate Knapsack problem
 */



public class Q6{
    public static void main(String[] args){
        MCKnapsack knapsack = new MCKnapsack();
        knapsack.solve(10, 10, new int[]{1,2,3,4,5}, new int[]{5,4,3,2,1});
        //System.out.println(knapsack.getWeightFromState(new int[1]));
    }
}
class MCKnapsack{
    // MARK: Properties
    MCRandom randomEngine;
    int numberOfIterations;
    int capacity;
    int[] arrayOfItemWeights;
    int[] arrayOfItemValues;
    public MCKnapsack(){
        this.randomEngine = new MCRandom();
        this.numberOfIterations = -1;
        this.capacity = -1;
    }

    /**
     * Solving knapsack problem.
     *
     * @param T
     * @param w
     * @param weights
     * @param values
     * @return array of states
     */
    public int[] solve(int T, int w , int[] weights, int[] values){
        // MARK: Initialization
        this.arrayOfItemValues = values;
        this.arrayOfItemWeights = weights;
        this.numberOfIterations = T;
        this.capacity = w;
        int[] states = new int[this.numberOfIterations];
        System.out.println("Init States");
        for(int i=0; i<states.length;i++){
            states[i] = 0;
            System.out.print(states[i]+" ");
        }
        System.out.println("\n");
        // Get an array of random numbers for random selection.
        System.out.println("Init random pick index array");
        int[] arrayOfRandomNumber = randomEngine.getUniRnd(T,0,weights.length); // upper bound shouldn't exceed the length of weights
        for(int i = 0; i<arrayOfRandomNumber.length; i++) {
            System.out.print(arrayOfRandomNumber[i]+ " ");
        }
        System.out.println();

        // MARK: Operation
        int pickItem,pickedItemWeight,pickedItemValue,currentSateWeight,currentStateValue;
        for(int i = 0; i < arrayOfRandomNumber.length; i++){
            //pickItem = arrayOfRandomNumber[i];
            pickedItemWeight = arrayOfItemWeights[i];
            pickedItemValue  = arrayOfItemValues[i];
            currentSateWeight = getWeightFromState(states);
            currentStateValue = getValueFromState(states);
            if((currentSateWeight+pickedItemWeight)<=capacity){
                // if adding this item will be feasible.
                System.out.println("Alpha: "+ Math.exp(pickedItemValue));
            }
        }
        return states;
    }
    public int getWeightFromState(int[] states){
        int weight=0;
        for(int i=0;i<states.length;i++){
            if(states[i]==1){
                weight+=this.arrayOfItemWeights[i];
            }
        }
        return weight;
    }

    public int getValueFromState(int[] states){
        int value=0;
        for(int i=0;i<states.length;i++){
            if(states[i]==1){
                value+=this.arrayOfItemValues[i];
            }
        }
        return value;
    }

}
