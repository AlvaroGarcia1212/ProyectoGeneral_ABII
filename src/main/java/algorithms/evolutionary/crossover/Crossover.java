package algorithms.evolutionary.crossover;

public interface Crossover <T>{
    T[][] crossover(T[] parent1, T[] parent2);
}
