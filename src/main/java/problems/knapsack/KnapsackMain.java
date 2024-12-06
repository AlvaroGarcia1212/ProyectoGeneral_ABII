package problems.knapsack;

import instances.InstanceKnapsack;

import java.io.IOException;

public class KnapsackMain {
    public static void main(String[] args) throws IOException {
        String filepath = "src/main/resources/instances/knapsack/instancia1";
        InstanceKnapsack instance = new InstanceKnapsack(filepath);

    }
}
