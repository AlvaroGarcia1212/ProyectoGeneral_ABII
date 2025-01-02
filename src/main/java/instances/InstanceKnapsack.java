package instances;

import problems.knapsack.KnapsackMain;
import problems.knapsack.KnapsackSolution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class InstanceKnapsack implements Instance<Integer, KnapsackSolution>{

    private String name;
    private int n;
    private int c;
    private int[] weights;
    private int[] values;

    public InstanceKnapsack(String name) throws IOException {
        this.name = name;
        this.importInstance(new BufferedReader(new FileReader(name)));
    }

    public void importInstance(BufferedReader reader) throws IOException {
        Scanner sc = new Scanner(reader).useLocale(Locale.US);
        this.n = sc.nextInt();
        this.c = sc.nextInt();
        this.weights = new int[n];
        this.values = new int[n];
        int i = 0;
        while (sc.hasNextInt()){
            this.values[i] = sc.nextInt();
            this.weights[i] = sc.nextInt();
            i++;
        }
        sc.close();
    }

    public Integer evaluate(KnapsackSolution solution) {
        boolean[] objets = solution.getSolution();
        int evaluation = 0;
        for (int i = 0; i < n; i++) {
            if (objets[i]) {
                evaluation += values[i];
            }
        }
        return evaluation;
    }

    public Integer evaluateWeight(KnapsackSolution solution) {
        boolean[] objets = solution.getSolution();
        int evaluation = 0;
        for (int i = 0; i < n; i++) {
            if (objets[i]) {
                evaluation += weights[i];
            }
        }
        return evaluation;
    }

    public int getC() {return this.c;}

    public int getN() {return this.n;}

    public KnapsackSolution generateRandomSolution() {
        boolean[] sol = new boolean[n];
        List<Integer> objetsList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            objetsList.add(i);
        }
        Collections.shuffle(objetsList);
        int sumWeight = 0;
        int sumValue = 0;
        for (Integer idx: objetsList) {
            if (sumWeight + weights[idx] <= c) {
                sumWeight += weights[idx];
                sumValue += values[idx];
                sol[idx] = true;
            }
        }
        KnapsackSolution solution = new KnapsackSolution(sol, this);
        solution.setWeight(sumWeight);
        solution.setValue(sumValue);
        return solution;

    }

}
