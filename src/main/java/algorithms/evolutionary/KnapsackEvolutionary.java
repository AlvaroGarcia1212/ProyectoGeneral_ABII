package algorithms.evolutionary;

import instances.InstanceKnapsack;
import problems.knapsack.KnapsackSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class KnapsackEvolutionary implements Evolutionary<KnapsackSolution> {

    final InstanceKnapsack instance;
    final int populationSize;
    final float crossoverRate;
    final float mutationRate;
    final int generations;
    final boolean debug;
    final boolean minimize;

    public KnapsackEvolutionary(InstanceKnapsack instance, int populationSize,
                                float crossoverRate, float mutationRate,
                                int generations, boolean debug, boolean minimize) {

        this.instance = instance;
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.generations = generations;
        this.debug = debug;
        this.minimize = minimize;
    }

    public KnapsackSolution run(){
        if (minimize){
            return runMaximize();
        }else{
            return runMaximize();
        }
    }

    private KnapsackSolution runMaximize() {
        List<KnapsackSolution> population = generateInitialPopulation();
        KnapsackSolution best = evaluatePopulation(population);
        for (int i = 0; i < generations; i++) {
            List<KnapsackSolution> primaPopulation = calityChoice(population);
            List<KnapsackSolution> O = crossover(primaPopulation);
            mutate(O);
            KnapsackSolution bestGeneration = evaluatePopulation(O);
            if (bestGeneration.isBeter(best, minimize)){
                best = bestGeneration;
            }
            population = replacement(population, primaPopulation, O, best);
        }
        return best;
    }

    private List<KnapsackSolution> replacement(List<KnapsackSolution> population, List<KnapsackSolution> primaPopulation, List<KnapsackSolution> o, KnapsackSolution best) {
        List<KnapsackSolution> newPopulation = new ArrayList<>();
        newPopulation.addAll(population);
        newPopulation.addAll(primaPopulation);
        newPopulation.addAll(o);
        if (!newPopulation.contains(best)) {
            newPopulation.add(best);
        }
        newPopulation.sort((s1, s2) -> Double.compare(s2.getValue(), s1.getValue()));

        return new ArrayList<>(newPopulation.subList(0, populationSize));


    }


    private List<KnapsackSolution> calityChoice(List<KnapsackSolution> population) {
        int sumFitnes = getSumFitnes(population);
        double[] probs = new double[population.size()];
        List<KnapsackSolution> newPopulation = new ArrayList<KnapsackSolution>();
        for (int i = 0; i < population.size(); i++) {
            probs[i] = (double) population.get(i).getValue() / sumFitnes;
        }
        for (int i = 0; i < population.size(); i++) {
            double u = Math.random();
            double sumProbs = 0;
            for (int j = 0; j < population.size(); j++) {
                sumProbs += probs[j];
                if (u <= sumProbs) {
                    newPopulation.add(population.get(j));
                    break;
                }
            }
        }
        return newPopulation;
    }

    private int getSumFitnes(List<KnapsackSolution> population) {
        int sum = 0;
        for (KnapsackSolution solution : population) {
            sum += solution.getValue();
        }
        return sum;
    }

    private List<KnapsackSolution> crossover(List<KnapsackSolution> population) {
        List<KnapsackSolution> newPopulation = new ArrayList<>();
        for (int i = 0; i < population.size() - 1; i+=2) {
            boolean[][] descendants = cross(population.get(i).getSolution(), population.get(i+1).getSolution());
            newPopulation.add(new KnapsackSolution(descendants[0], instance));
            newPopulation.add(new KnapsackSolution(descendants[1], instance));
        }
        return newPopulation;
    }

    private boolean[][] cross(boolean[] padre1, boolean[] padre2){
        int length = padre1.length;
        Random random = new Random();
        int crossoverPoint = random.nextInt(length - 1) + 1;
        boolean[] newPopulation1 = new boolean[length];
        boolean[] newPopulation2 = new boolean[length];

        for (int i = 0; i < length; i++) {
            if (i < crossoverPoint) {
                newPopulation1[i] = padre1[i];
                newPopulation2[i] = padre2[i];
            } else {
                newPopulation1[i] = padre2[i];
                newPopulation2[i] = padre1[i];
            }
        }
        return new boolean[][]{newPopulation1, newPopulation2};
    }

    private void mutate(List<KnapsackSolution> population) {
        for (int i = 0; i < population.size(); i++) {
            boolean[] solution = population.get(i).getSolution();
            for (int j = 0; j < solution.length; j++) {
                double u = Math.random();
                if (u < mutationRate) {
                    solution[j] = !solution[j];
                }
            }
        }
    }
    private List<KnapsackSolution> generateInitialPopulation() {
        List<KnapsackSolution> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            population.add(instance.generateRandomSolution());
        }
        return population;
    }

    KnapsackSolution evaluatePopulation(List<KnapsackSolution> population) {
        Integer bestValue = Integer.MIN_VALUE;
        KnapsackSolution best = null;
        for (KnapsackSolution solution : population) {

            int fitnes = instance.evaluate(solution);
            int weight = instance.evaluateWeight(solution);
            solution.setValue(fitnes);
            solution.setWeight(weight);
            if (solution.isFeasible()) {
                if (solution.getValue() > bestValue) {
                    best = solution;
                    bestValue = solution.getValue();
                }
            } else {
                solution.setValue(Integer.MIN_VALUE);
            }
        }
        return best;
    }

}
