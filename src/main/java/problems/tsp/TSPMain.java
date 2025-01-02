package problems.tsp;
import algorithms.evolutionary.Evolutionary;
import algorithms.evolutionary.TSPEvolutionary;
import algorithms.evolutionary.crossover.Crossover;
import algorithms.evolutionary.crossover.PMX;
import instances.InstanceTSP;

import java.io.IOException;

public class TSPMain {
    public static void main(String[] args) throws IOException {
        String filepath = "src/main/resources/instances/tsp/instancia2.txt"; // ruta de la instancia que queremos usar
        InstanceTSP instance = new InstanceTSP(filepath);
        int populationSize = 20000;
        float crossoverRate= 0.6f;
        float mutationRate = (float) 1 / instance.getN();
        int generations = 1000;
        boolean debug = false;
        boolean minimize = true;
        Crossover<Integer> crossover = new PMX();
        Evolutionary<TSPSolution> algorithm = new TSPEvolutionary(instance, populationSize, crossoverRate, mutationRate, generations, debug, minimize, crossover);
        TSPSolution sol = algorithm.run();
        System.out.println("[VALOR SOLUCION]: " + sol.getValue());
        for (int i = 0; i < instance.getN(); i++) {
            System.out.print(sol.getRoute()[i]+" --> ");
        }
    }
}
