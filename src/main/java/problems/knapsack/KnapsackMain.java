package problems.knapsack;

import algorithms.evolutionary.Evolutionary;
import algorithms.evolutionary.KnapsackEvolutionary;
import instances.InstanceKnapsack;

import java.io.IOException;

public class KnapsackMain {

    public static void main(String[] args) throws IOException {
        String filepath = "src/main/resources/instances/knapsack/instancia2"; // ruta de la instancia que queremos usar
        InstanceKnapsack instance = new InstanceKnapsack(filepath);
        int populationSize = 300;
        float crossoverRate= 0.6f;
        float mutationRate = (float) 1 / instance.getN();
        int generations = 500;
        boolean debug = false;
        boolean minimize = false;

        Evolutionary<KnapsackSolution> algoritm = new KnapsackEvolutionary(instance, populationSize, crossoverRate, mutationRate, generations, debug, minimize);
        KnapsackSolution sol = algoritm.run();
        System.out.println("[Valor solucion]: " + sol.getValue() + " [Peso de la solucion]: " + sol.getWeight()) ;
        System.out.print("Solucion: [");
        for (int i = 0; i < instance.getN(); i++) {
            if (sol.getSolution()[i]){
                System.out.print(i + ",");
            }
        }
        System.out.println("]");

    }
}
