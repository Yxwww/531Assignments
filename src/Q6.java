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
        System.out.println("Hello Q6.");
        MCRandom rand = new MCRandom();
        int size = 10, lower = 0,upper = 10;
        int[] randomIntArr = rand.getUniRnd(10,0,10);
        for(int i = 0;i<size;i++){
            System.out.println(randomIntArr[i]);
        }
    }
    /*public static void print(Object stuff){
        //System.out.pritnln();
        System.out.println(stuff);
    }*/
}
