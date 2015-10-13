import java.util.Arrays;

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
        int[] resultState = knapsack.solve(50, 15, new int[]{1,2,3,4,5}, new int[]{5,4,3,2,1});
        //System.out.println(knapsack.getWeightFromState(new int[1]))
        System.out.println("Current value of state: "+ knapsack.getValueFromState(resultState)+" - " +
                "Weight: "+knapsack.getWeightFromState(resultState) + "\n Result State: "+Arrays.toString(resultState));
    }
}
class MCKnapsack{
    // MARK: Properties
    MCRandom randomEngine;
    int numberOfIterations;
    int capacity;
    int[] randomNumberForSelection;
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
        // Error Handling
        if(weights.length!=values.length){
            System.out.println("Weights array is not equal to value array. Pce out");
            System.exit(-1);
        }

        // MARK: Initialization
        this.arrayOfItemValues = values;
        this.arrayOfItemWeights = weights;
        this.numberOfIterations = T;
        this.capacity = w;
        int[] states = new int[this.arrayOfItemValues.length];
        double[] probabilityForIteration = randomEngine.getUniRnd(this.numberOfIterations); // Used for checking whether we gonna keep the state changed or not
        this.randomNumberForSelection = new int[this.numberOfIterations];
        System.out.println("Init States");
        for(int i=0; i<states.length;i++){
            states[i] = 0;
        //    System.out.print(states[i]+" ");
        }
        System.out.println("\n");
        // Get an array of random numbers for random selection.
        System.out.println("Init random pick index array");
        int[] arrayOfRandomNumber = randomEngine.getUniRnd(T,0,weights.length); // upper bound shouldn't exceed the length of weights
        System.arraycopy(arrayOfRandomNumber,0,this.randomNumberForSelection,0,arrayOfRandomNumber.length);
        System.out.println("Init random probability for pick index array");

        System.out.println(Arrays.toString(this.randomNumberForSelection));
        //System.out.println(Arrays.toString(probabilityForIteration));
        System.out.println();

        // MARK: Operation
        int pickItem,newStateWeight,pickedItemWeight,pickedItemValue,currentSateWeight,currentStateValue;
        int[] newStates = new int[states.length];
        for(int i = 0; i < this.randomNumberForSelection.length; i++){
            //System.out.println( " Pick - "+ arrayOfRandomNumber[i]);
            // Pick an item from normal distributed random number
            pickItem = arrayOfRandomNumber[i];
            // Initialize newState
            System.arraycopy(states,0,newStates,0,states.length);
            newStates[pickItem] = 1 - newStates[pickItem]; // flip the state

            //System.out.println("Curre: \t"+ Arrays.toString(states));
            //System.out.println("Newer: \t"+Arrays.toString(newStates));
            //System.out.println(newState[pickItem]+" - "+states[pickItem]);
            newStateWeight = getWeightFromState(newStates); // get the weight of the new state
            //currentSateWeight = getWeightFromState(states);
            //currentStateValue = getValueFromState(states);

            if(newStateWeight<=capacity){
                // if feasible, check probability

                if(probabilityForIteration[i] <= Math.min(1,Math.exp(getValueFromState(newStates)- getValueFromState(states)))){
                    // if lambda is less or equal to the min of 1 or e^(Value(newState) - Value(currentState))
                    // If so , we go with newState
                    System.arraycopy(newStates,0,states,0,states.length);



                }/*else{
                    //System.out.println(probabilityForIteration[i] + " > " +(Math.min(1,Math.exp(getValueFromState(newStates)- getValueFromState(states)))) );
                    //System.out.println(getValueFromState(newStates) + " - "+ getValueFromState(states));
                    //System.out.println(getWeightFromState(newStates) + " - "+ getWeightFromState(states));
                }*/
            }/*else{
                //System.out.println("Not Feasible");
            }*/
            //System.out.println();
        } // END of FOR loop
        return states;
    }
    public int getWeightFromState(int[] states){
        int weight=0;
        for(int i=0;i<states.length;i++){
            if(states[i]==1){
                weight+=this.arrayOfItemWeights[this.randomNumberForSelection[i]];
            }
        }
        return weight;
    }

    public int getValueFromState(int[] states){
        int value=0;
        for(int i=0;i<states.length;i++){
            if(states[i]==1){
                value+=this.arrayOfItemValues[this.randomNumberForSelection[i]];
            }
        }
        return value;
    }

}
