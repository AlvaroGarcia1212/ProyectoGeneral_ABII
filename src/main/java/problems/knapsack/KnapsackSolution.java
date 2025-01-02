package problems.knapsack;
import instances.InstanceKnapsack;

public class KnapsackSolution {
    private InstanceKnapsack instanceKnapsack;
    private boolean[] solution;
    private int weight;
    private int value;

    public KnapsackSolution(boolean[] solution, InstanceKnapsack instance) {
        this.solution = solution;
        this.instanceKnapsack = instance;}

    public boolean[] getSolution() {return this.solution;}
    public int getWeight() {return this.weight;}
    public int getValue() {return this.value;}
    public void setSolution(boolean[] solution) {this.solution = solution;}
    public void setWeight(int weight) {this.weight = weight;}
    public void setValue(int value) {this.value = value;}
    public boolean isFeasible() {
        return weight <= instanceKnapsack.getC();
    }
    public boolean isBeter(KnapsackSolution other, boolean minimize) {
        if (minimize) {
            return value < other.getValue();
        }else{
            return value > other.getValue();
        }
    }
}
