package algorithms.evolutionary;
import algorithms.evolutionary.crossover.Crossover;
import algorithms.evolutionary.crossover.PMX;
import instances.InstanceTSP;
import problems.tsp.*;

import java.util.ArrayList;
import java.util.List;

public class TSPEvolutionary implements Evolutionary <TSPSolution> {

    final InstanceTSP instance;
    final int populationSize;
    final float crossoverRate;
    final float mutationRate;
    final int generations;
    final boolean debug;
    final boolean minimize;
    final Crossover<Integer> crossover;

    public TSPEvolutionary(InstanceTSP instance, int populationSize, float crossoverRate, float mutationRate, int generations, boolean debug, boolean minimize, Crossover<Integer> crossover) {
        this.instance = instance;
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.generations = generations;
        this.minimize = minimize;
        this.debug = debug;
        this.crossover = crossover;
    }

    public TSPSolution run(){
        List<TSPSolution> population = generateInitialPopulation();
        TSPSolution bestSolution = evaluatePopulation(population);
        for (int i = 0; i < generations; i++) {
            List<TSPSolution> primaPopulation = calityChoice(population);
            List<TSPSolution> O = crossover(primaPopulation);
            mutate(O);
            TSPSolution bestGeneration = evaluatePopulation(O);
            if (bestGeneration.isBeter(bestSolution, minimize)) {
                bestSolution = bestGeneration;
            }
            population = replacement(population, primaPopulation, primaPopulation, bestSolution);
        }
        return bestSolution;
    }

    private List<TSPSolution> crossover(List<TSPSolution> primaPopulation) {
        List<TSPSolution> newPopulation = new ArrayList<>();
        for (int i = 0; i < populationSize; i+=2) {
            Integer[] sol1 =  primaPopulation.get(i).getRoute();
            Integer[] sol2 = primaPopulation.get(i+1).getRoute();
            double u = Math.random();
            if (u < crossoverRate){
                Integer[][] descent = crossover.crossover(sol1, sol2);
                newPopulation.add(new TSPSolution(descent[0], instance));
                newPopulation.add(new TSPSolution(descent[1], instance));
            } else {
                newPopulation.add(primaPopulation.get(i));
                newPopulation.add(primaPopulation.get(i+1));
            }
        }
        return newPopulation;
    }


    private void mutate(List<TSPSolution> population) {
        for (TSPSolution solution : population) {
            double u = Math.random();
            if (u < mutationRate) {
                mutation(solution);
            }
        }
    }

    private void mutation(TSPSolution solution) { // Inversion de subcadena
        Integer[] route = solution.getRoute();
        int n = route.length;
        int p1 = (int) (Math.random() * n);
        int p2 = (int) (Math.random() * n);
        if (p1 > p2) {
            int aux = p2;
            p2 = p1;
            p1 = aux;
        }
        while (p1 < p2) {
            // Intercambiar los elementos
            int temp = route[p1];
            route[p1] = route[p2];
            route[p2] = temp;

            // Avanzar y retroceder en los índices
            p1++;
            p2--;
        }
        solution.setRoute(route);
    }

    private List<TSPSolution> calityChoice(List<TSPSolution> population) {
        double sumFitness = getSumFitness(population);
        double[] probs = new double[population.size()];
        List<TSPSolution> newPopulation = new ArrayList<>();
        for (int i = 0; i < population.size(); i++) {
            probs[i] = 1 - ((double) population.get(i).getValue() / sumFitness);
        }
        for (int i = 0; i < population.size(); i++) {
            double u = Math.random();
            double sumProb = 0;
            for (int j = 0; j < population.size(); j++) {
                sumProb += probs[j];
                if (u <= sumProb) {
                    newPopulation.add(population.get(j));
                    break;
                }
            }

        }
        return newPopulation;
    }

    private double getSumFitness(List<TSPSolution> population) {
        double sum = 0;
        for (TSPSolution solution : population) {
            sum += solution.getValue();
        }
        return sum;
    }


    private TSPSolution evaluatePopulation(List<TSPSolution> population) {
        double bestVal = Double.MAX_VALUE;
        TSPSolution bestSolution = null;
        for (TSPSolution solution : population) {
            double val = instance.evaluate(solution);
            solution.setValue(val);
            if (val < bestVal) {
                bestVal = val;
                bestSolution = solution;
            }
        }
        return bestSolution;
    }

    private List<TSPSolution> generateInitialPopulation(){
        List<TSPSolution> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++){
            population.add(instance.generateRandomSolution());
        }
        return population;
    }

    private List<TSPSolution> replacement(List<TSPSolution> population, List<TSPSolution> primaPopulation,
                                          List<TSPSolution> O, TSPSolution best) {
        List<TSPSolution> newPopulation = new ArrayList<>();
        newPopulation.addAll(population);
        newPopulation.addAll(primaPopulation);
        newPopulation.addAll(O);
        if (!newPopulation.contains(best)) {
            newPopulation.add(best);
        }
        newPopulation.sort((s1, s2) -> Double.compare(s1.getValue(), s2.getValue()));
        return new ArrayList<>(newPopulation.subList(0, populationSize));
    }
}
